(ns beerpressure.db.comment.brewery-comment
  (:require [environ.core :refer [env]]
            [yesql.core :refer [defqueries]]
            [beerpressure.db.common :refer [db-spec, check-error, convert-naming-convention]]))

(defqueries "sql/comment/operations_brewery_comment.sql"
            {:connection db-spec})

(defn resolve-brewery-review-comment
  [context args _value]
  (first
    (convert-naming-convention (check-error (get-brewery-review-comment args)))))

(defn resolve-brewery-review-comments
  [context args _value]
  (convert-naming-convention (check-error (get-brewery-review-comments args))))

