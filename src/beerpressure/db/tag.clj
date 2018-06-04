(ns beerpressure.db.tag
  (:require [environ.core :refer [env]]
            [yesql.core :refer [defqueries]]
            [beerpressure.db.common :refer [db-spec, check-error, convert-naming-convention]]))

(defqueries "sql/operations_tag.sql"
            {:connection db-spec})

(defn resolve-tags-ordered-by-name
  [context args _value]
  (check-error (get-tags-ordered-by-name args)))

(defn resolve-tags-ordered-by-brewery-popularity
  [context args _value]
  (check-error (get-tags-ordered-by-brewery-popularity args)))

(defn resolve-tags-ordered-by-beer-popularity
  [context args _value]
  (check-error (get-tags-ordered-by-beer-popularity args)))
