(ns beerpressure.schema
  (:require
   [beerpressure.db.beer :as db.beer]
   [beerpressure.db.brewery :as db.brewery]
   [clojure.java.io :as io]
   [clojure.edn :as edn]
   [com.walmartlabs.lacinia.schema :as schema]
   [com.walmartlabs.lacinia.util :refer [attach-resolvers]]))

(defn beerpressure-schema
  []
  (-> (io/resource "edn/beerpressure-schema.edn")
      slurp
      edn/read-string
      (attach-resolvers {:resolve-brewery db.brewery/resolve-brewery
                         :resolve-breweries db.brewery/resolve-breweries
                         :resolve-beer db.beer/resolve-beer
                         :resolve-beers db.beer/resolve-beers})
      schema/compile))
