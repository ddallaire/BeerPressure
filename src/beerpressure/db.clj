(ns beerpressure.db
  (:require [clojure.data.json :as json]
            [environ.core :refer [env]]
            [beerpressure.core :as core]
            [yesql.core :refer [defqueries]]
            [clojure.string :as s]))

(def db-spec {:classname "org.postgresql.Driver"
              :subprotocol "postgresql"
              :subname (str "//"
                            (or (env :db-host)
                                (-> (s/split (System/getenv "DATABASE_URL") #":") (nth 2) (s/split #"@") second))
                            ":"
                            (or (env :db-port)
                                (-> (s/split (System/getenv "DATABASE_URL") #":") (nth 3) (s/split #"/") first))
                            "/"
                            (or (env :db-name)
                                (-> (s/split (System/getenv "DATABASE_URL") #":") (nth 3) (s/split #"/") second)))
              :user (or (env :db-username)
                        (-> (s/split (System/getenv "DATABASE_URL") #":") second (s/split #"//") second))
              :password (or (env :db-password)
                            (-> (s/split (System/getenv "DATABASE_URL") #":") (nth 2) (s/split #"@") first))})

(defqueries "sql/operations.sql"
  {:connection db-spec})

(defmacro check-error
  "Usage: (check-error (create-developer! (core/new-developer \"foo@bar.com\")))"
  [body]
  `(try ~body (catch Exception e# (throw (Exception.(:cause (Throwable->map (.getNextException e#))))))))

(defn convertNamingConvention [queryResult]
  (map #(clojure.set/rename-keys % {:image_path :imagePath, :alcohol_percent, :alcoholPercent}) queryResult))

(defn resolve-brewery
  [context {:keys [id]} _value]
  (first
    (convertNamingConvention (check-error (get-brewery {:id id})))))

(defn resolve-breweries
  [context args _value]
  (convertNamingConvention (check-error (get-breweries))))

(defn resolve-beer
  [context {:keys [id]} _value]
  (first
    (convertNamingConvention (check-error (get-beer {:id id})))))

(defn resolve-beers
  [context args _value]
  (convertNamingConvention (check-error (get-beers))))
