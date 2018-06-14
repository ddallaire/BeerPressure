(ns beerpressure.db.beer-style
  (:require [environ.core :refer [env]]
            [yesql.core :refer [defqueries]]
            [beerpressure.db.common :refer :all]))

(defqueries "sql/operations_beer_style.sql"
            {:connection db-spec})

(defn resolve-beer-styles
  [context args _value]
  (check-error (case (get args :orderBy)
                 :NAME (case (get args :orderType)
                         :ASC (get-beer-styles-ordered-by-name-asc args)
                         :DESC (get-beer-styles-ordered-by-name-desc args)))))
