(ns beerpressure.db.common
  (:require [environ.core :refer [env]]
            [clojure.string :as s]
            [clojure.java.jdbc :as jdbc]))

(def db-spec {:classname   "org.postgresql.Driver"
              :subprotocol "postgresql"
              :subname     (str "//"
                                (or (env :db-host)
                                    (-> (s/split (System/getenv "DATABASE_URL") #":") (nth 2) (s/split #"@") second))
                                ":"
                                (or (env :db-port)
                                    (-> (s/split (System/getenv "DATABASE_URL") #":") (nth 3) (s/split #"/") first))
                                "/"
                                (or (env :db-name)
                                    (-> (s/split (System/getenv "DATABASE_URL") #":") (nth 3) (s/split #"/") second)))
              :user        (or (env :db-username)
                               (-> (s/split (System/getenv "DATABASE_URL") #":") second (s/split #"//") second))
              :password    (or (env :db-password)
                               (-> (s/split (System/getenv "DATABASE_URL") #":") (nth 2) (s/split #"@") first))})

(defmacro check-error
  "Usage: (check-error (create-developer! (core/new-developer \"foo@bar.com\")))"
  [body]
  `(try ~body (catch Exception e# (throw (Exception. (:cause (Throwable->map (.getNextException e#))))))))

(defn convert-naming-convention
  [queryResult]
  (map #(clojure.set/rename-keys % {:image_path :imagePath, :alcohol_percent :alcoholPercent, :id_beer :idBeer, :id_beer_review :idBeerReview, :id_beer_review_comment :idBeerReviewComment,
                                    :id_brewery :idBrewery, :id_brewery_review :idBreweryReview, :id_brewery_review_comment :idBreweryReviewComment}) queryResult))

(defn get-authentication-token-from-context
  [context]
  (get @(get context :cache) :authorization))

(defn fill-user-from-row
  [row]
  (assoc row :user row))

(defn integer-list-to-in-clause
  [list]
  (str "(" (s/join ", " list) ")"))

(defn cip-list-to-in-clause
  [list]
  (let [escaped-strings (map #(str "'" (s/replace % #"[^a-zA-Z0-9]" "") "'") list)]
    (str "(" (s/join ", " escaped-strings) ")")))

(defn generate-query-limit-offset
  [args]
  (str "LIMIT " (get args :first) " OFFSET " (get args :skip)))

(defn execute-association-query
  [table-name list-column-name fixed-column-name ids id]
  (if (empty? ids)
    0
    (let [query-beginning (str "INSERT INTO " table-name "(" list-column-name ", " fixed-column-name ") VALUES ")
          values (s/join ", " (map #(str "(" % "," id ")") ids))
          query (str query-beginning values)]
      (check-error (jdbc/execute! db-spec query)))))
