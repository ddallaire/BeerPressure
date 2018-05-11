(ns beerpressure.db
  (:require [clojure.data.json :as json]
            [environ.core :refer [env]]
            [beerpressure.core :as core]
            [yesql.core :refer [defqueries]]
            [clojure.string :as s]))

(def db-spec {:classname "org.postgresql.Driver"
              :subprotocol "postgresql"
              :subname (str "//"
                            (or (:db-host env)
                                (-> (s/split (System/getenv "DATABASE_URL") #":") (nth 2) (s/split #"@") second))
                            ":"
                            (or (:db-port env)
                                (-> (s/split (System/getenv "DATABASE_URL") #":") (nth 3) (s/split #"/") first))
                            "/"
                            (or (:db-name env)
                                (-> (s/split (System/getenv "DATABASE_URL") #":") (nth 3) (s/split #"/") second)))
              :user (or (:db-username env)
                        (-> (s/split (System/getenv "DATABASE_URL") #":") second (s/split #"//") second))
              :password (or (:db-password env)
                            (-> (s/split (System/getenv "DATABASE_URL") #":") (nth 2) (s/split #"@") first))})

(defqueries "sql/operations.sql"
  {:connection db-spec})

(defmacro check-error
  "Usage: (check-error (create-developer! (core/new-developer \"foo@bar.com\")))"
  [body]
  `(try ~body (catch Exception e# (throw (Exception.(:cause (Throwable->map (.getNextException e#))))))))

(defn resolve-game
  [context args _value]
  (let [developer (:authorization @(:cache context))]
    (first
     (check-error (get-game (assoc args :developer developer))))))

(defn resolve-games
  [context args _value]
  (let [developer (:authorization @(:cache context))]
    (check-error (get-games {:developer developer}))))

(defn resolve-create-game!
  [context args _value]
  (let [developer (:authorization @(:cache context))]
    (dissoc (create-game<! (core/new-game (:name args) developer)) :developer)))

(defn resolve-create-score!
  [context args _value]
  (create-score<! args))

(defn resolve-recent-scores
  [context {:keys [game_key last]} _value]
  (let [last (if (> last 100)
               100
               last)]
    (get-recent-scores {:game_key game_key
                        :last last})))

(defn resolve-top-scores
  [context {:keys [game_key last keyword]} _value]
  (let [last (if (> last 100)
               100
               last)]
    (get-top-scores {:game_key game_key
                     :last last
                     :keyword keyword})))
