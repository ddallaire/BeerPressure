(ns beerpressure.handler
  (:require [clojure.data.json :as json]
            [clojure.string :as string]
            [com.walmartlabs.lacinia :refer [execute]]
            [beerpressure.schema :refer [beerpressure-schema]]
            [ring.middleware.cors :refer [wrap-cors]]
            [ring.middleware.params :refer [wrap-params]]
            [ring.util.request :refer [body-string]]
            [beerpressure.db.user :as db.user]
            [clojure.string :as str]))

(defn variable-map
  "Reads the `variables` query parameter, which contains a JSON string
  for any and all GraphQL variables to be associated with this request.
  Returns a map of the variables (using keyword keys)."
  [request]
  (let [variables (condp = (:request-method request)
                    :get (try (-> request
                                  (get-in [:query-params "variables"])
                                  (json/read-str :key-fn keyword))
                              (catch Exception e nil))
                    :post (try (-> request
                                   :body
                                   (json/read-str :key-fn keyword)
                                   :variables)
                               (catch Exception e nil)))]
    (if-not (empty? variables)
      variables
      {})))

(defn extract-query
  "Reads the `query` query parameters, which contains a JSON string
  for the GraphQL query associated with this request. Returns a
  string.  Note that this differs from the PersistentArrayMap returned
  by variable-map. e.g. The variable map is a hashmap whereas the
  query is still a plain string."
  [request]
  (case (:request-method request)
    :get (get-in request [:query-params "query"])
    :post (try (-> request
                   :body
                   (json/read-str :key-fn keyword)
                   :query)
               (catch Exception e ""))
    :else ""))

(defn extract-authorization-key
  "Extract the authorization key from the request header. The
  authorization header is of the form: Authorization: bearer <key>"
  [request]
  (if-let [auth-header (-> request
                           :headers
                           (get "authorization"))]
    (-> auth-header
        (string/split #"\s")
        last)
    nil))

(defn is-authorized
  [query token]
  (let [logged-user (db.user/get-logged-user token)]
    (or (and (not= logged-user nil) (db.user/is-token-time-valid (get logged-user :time)))
        (str/starts-with? (str/replace query #"[ \n]" "") "mutationlogin"))))

(defn ^:private graphql-handler
  "Accepts a GraphQL query via GET or POST, and executes the query.
  Returns the result as text/json."
  [compiled-schema]
  (let [context {:cache (atom {})}]
    (fn [request]
      (let [authorization (extract-authorization-key request)
            query (extract-query request)]
        (swap! (:cache context) assoc :authorization
               authorization)
        (if (is-authorized query authorization)
          (do (db.user/update-token-time authorization)
              (let [vars (variable-map request)
                    result (execute compiled-schema query vars context)
                    status (if (-> result :errors seq)
                             400
                             200)]
                {:status  status
                 :headers {"Content-Type" "application/json"}
                 :body    (json/write-str result)}))
          {:status 401})))))

(defn handler [request]
  (let [uri (:uri request)]
    (if (= uri "/graphql")
      ((graphql-handler (beerpressure-schema)) request)
      {:status  404
       :headers {"Content-Type" "text/html"}
       :body    (str "Only GraphQL JSON requests to /graphql are accepted on this server")})))

(defn wrap-body-string [handler]
  (fn [request]
    (let [body-str (body-string request)]
      (handler (assoc request :body body-str)))))

(def app
  (-> handler
      wrap-params
      (wrap-cors :access-control-allow-origin [#".*"]
                 :access-control-allow-methods [:get :post])
      wrap-body-string))
