(ns beerpressure.db.brewery
  (:require [environ.core :refer [env]]
            [yesql.core :refer [defqueries]]
            [beerpressure.db.common :refer [db-spec, check-error, convert-naming-convention]]))

(defqueries "sql/operations_brewery.sql"
            {:connection db-spec})

(defn resolve-brewery
  [context args _value]
  (first
    (convert-naming-convention (check-error (get-brewery args)))))

(defn resolve-breweries
  [context args _value]
  (convert-naming-convention (check-error (get-breweries args))))

