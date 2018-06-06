(ns beerpressure.db.comment.beer-comment
  (:require [environ.core :refer [env]]
            [yesql.core :refer [defqueries]]
            [beerpressure.db.common :refer [db-spec, check-error, convert-naming-convention]]))

(defqueries "sql/comment/operations_beer_comment.sql"
            {:connection db-spec})

(defn resolve-beer-review-comment
  [context args _value]
  (first
    (convert-naming-convention (check-error (get-beer-review-comment args)))))

(defn resolve-beer-review-comments
  [context args _value]
  (convert-naming-convention (check-error (get-beer-review-comments args))))
