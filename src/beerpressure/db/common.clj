(ns beerpressure.db.common
  (:require [environ.core :refer [env]]
            [clojure.string :as s]))

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
  (map #(clojure.set/rename-keys % {:image_path :imagePath, :alcohol_percent :alcoholPercent}) queryResult))
