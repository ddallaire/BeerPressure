(ns beerpressure.db.user
  (:require [environ.core :refer [env]]
            [yesql.core :refer [defqueries]]
            [beerpressure.db.common :refer :all]
            [clj-http.client :as http.client])
  (:use clj-xpath.core))

(defqueries "sql/operations_user.sql"
            {:connection db-spec})

(defn get-logged-user
  [token]
  (first (get-logged-user-db {:token token})))

(defn update-token-time
  [token]
  (update-token-time-db! {:token token}))

(defn extract-user-from-cas-xml-response
  [response]
  (with-namespace-context (xmlnsmap-from-root-node response)
                          {:cip     ($x:text "//cas:cip" response)
                           :name    ($x:text "//cas:prenom" response)
                           :surname ($x:text "//cas:nomFamille" response)}))

(defn get-user-from-cas
  [ticket]
  (let [response (http.client/get "https://cas.usherbrooke.ca//serviceValidate" {:query-params {:service (env :frontend-url)
                                                                                                :ticket  ticket}})]
    (extract-user-from-cas-xml-response (get response :body))))

(defn insert-user-if-not-exists
  [user]
  (if (empty? (get-user user)) (insert-user! user)))

(defn login
  [user]
  (let [token (str (java.util.UUID/randomUUID))
        user-and-token (assoc user :token token)]
    (insert-user-if-not-exists user)
    (logout-using-cip-db! user)
    (login-db! user-and-token)
    user-and-token))

(defn resolve-login
  [context args _value]
  (login (get-user-from-cas (get args :ticket))))

(defn logout
  [token]
  (logout-using-token-db! {:token token}))

(defn resolve-logout
  [context args _value]
  (logout (get-authentication-token-from-context context)))

