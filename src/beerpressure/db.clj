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

(defn convert-naming-convention
  [queryResult]
  (map #(clojure.set/rename-keys % {:image_path :imagePath, :alcohol_percent, :alcoholPercent}) queryResult))


(defn resolve-brewery
  [context args _value]
  (first
    (convert-naming-convention (check-error (get-brewery args)))))

(defn resolve-breweries
  [context args _value]
  (convert-naming-convention (check-error (get-breweries args))))


(defn fill-beer-breweries
  [beer]
  (assoc beer :breweries (convert-naming-convention (get-beer-breweries {:id (get beer :id)}))))

(defn resolve-beer
  [context args _value]
  (def beer (first (convert-naming-convention (check-error (get-beer args)))))
  (fill-beer-breweries beer))

(defn resolve-beers
  [context args _value]
  (def beers (convert-naming-convention (check-error (get-beers args))))
  (map #(fill-beer-breweries %) beers))
