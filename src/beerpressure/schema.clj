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
      (attach-resolvers {:resolve-brewery db/resolve-brewery
                         :resolve-breweries db/resolve-breweries
                         :resolve-beer db/resolve-beer
                         :resolve-beers db/resolve-beers})
      schema/compile))
