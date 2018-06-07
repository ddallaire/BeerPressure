(ns beerpressure.db.beer-style
  (:require [environ.core :refer [env]]
            [yesql.core :refer [defqueries]]
            [beerpressure.db.common :refer :all]))

(defqueries "sql/operations_beer_style.sql"
            {:connection db-spec})

(defn resolve-beer-styles-ordered-by-name
  [context args _value]
  (check-error (get-beer-styles-ordered-by-name)))
