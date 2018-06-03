(ns beerpressure.db.beer
  (:require [environ.core :refer [env]]
            [yesql.core :refer [defqueries]]
            [beerpressure.db.common :refer [db-spec, check-error, convert-naming-convention]]))

(defqueries "sql/operations_beer.sql"
            {:connection db-spec})

(defn fill-beer-breweries
  [beer]
  (assoc beer :breweries (convert-naming-convention (get-beer-breweries {:id (get beer :id)}))))

(defn resolve-beer
  [context args _value]
  (let [beer (first (convert-naming-convention (check-error (get-beer args))))]
    (fill-beer-breweries beer)))

(defn resolve-beers
  [context args _value]
  (let [beers (convert-naming-convention (check-error (get-beers args)))]
    (map #(fill-beer-breweries %) beers)))
