(ns beerpressure.db.tag
  (:require [environ.core :refer [env]]
            [yesql.core :refer [defqueries]]
            [beerpressure.db.common :refer [db-spec, check-error, convert-naming-convention]]))

(defqueries "sql/operations_tag.sql"
            {:connection db-spec})

(defn resolve-tags-ordered-by-popularity
  [context args _value]
  (check-error (get-tags-ordered-by-popularity args)))
