(ns beerpressure.schema
  (:require
   [beerpressure.db :as db]
   [clojure.java.io :as io]
   [clojure.edn :as edn]
   [com.walmartlabs.lacinia.schema :as schema]
   [com.walmartlabs.lacinia.util :refer [attach-resolvers]]))

(defn beerpressure-schema
  []
  (-> (io/resource "edn/beerpressure-schema.edn")
      slurp
      edn/read-string
      (attach-resolvers {:resolve-game db/resolve-game
                         :resolve-games db/resolve-games
                         :resolve-create-game! db/resolve-create-game!
                         :resolve-create-score! db/resolve-create-score!
                         :resolve-recent-scores db/resolve-recent-scores
                         :resolve-top-scores db/resolve-top-scores})
      schema/compile))
