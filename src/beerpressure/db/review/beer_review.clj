(ns beerpressure.db.review.beer-review
  (:require [environ.core :refer [env]]
            [yesql.core :refer [defqueries]]
            [beerpressure.db.common :refer [db-spec, check-error, convert-naming-convention]]))

(defqueries "sql/review/operations_beer_review.sql"
            {:connection db-spec})

(defn resolve-beer-review
  [context args _value]
   (first
     (convert-naming-convention (check-error (get-beer-review args)))))

(defn resolve-beer-reviews
  [context args _value]
  (convert-naming-convention (check-error (get-beer-reviews args))))
