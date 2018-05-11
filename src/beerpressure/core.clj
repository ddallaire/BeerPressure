(ns beerpressure.core
  (:require [clojure.string :as string]))

(defn uuid [] (string/replace (str (java.util.UUID/randomUUID)) #"-" ""))

(defn new-developer
  "Given email, create a developer map"
  [email]
  {:email email
   :key (uuid)})

(defn new-game
  "Given a name and developer key, create a game map"
  [name developer]
  {:name name
   :developer developer
   :key (uuid)})
