(ns beerpressure.db.review.brewery-review
  (:require [environ.core :refer [env]]
            [yesql.core :refer [defqueries]]
            [beerpressure.db.common :refer [db-spec, check-error, convert-naming-convention]]))

(defqueries "sql/review/operations_brewery_review.sql"
            {:connection db-spec})

(defn resolve-brewery-review
  [context args _value]
  (first
    (convert-naming-convention (check-error (get-brewery-review args)))))

(defn resolve-brewery-reviews
  [context args _value]
  (convert-naming-convention (check-error (get-brewery-reviews args))))
