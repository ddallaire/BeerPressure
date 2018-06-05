(ns beerpressure.db.tag
  (:require [environ.core :refer [env]]
            [yesql.core :refer [defqueries]]
            [beerpressure.db.common :refer :all]))

(defqueries "sql/operations_tag.sql"
            {:connection db-spec})

(defn resolve-tags-ordered-by-name
  [context args _value]
  (case (get args :orderType)
    :ASC (check-error (get-tags-ordered-by-name-asc args))
    :DESC (check-error (get-tags-ordered-by-name-desc args))))

(defn resolve-tags-ordered-by-brewery-popularity
  [context args _value]
  (case (get args :orderType)
    :ASC (check-error (get-tags-ordered-by-brewery-popularity-asc args))
    :DESC (check-error (get-tags-ordered-by-brewery-popularity-desc args))))

(defn resolve-tags-ordered-by-beer-popularity
  [context args _value]
  (case (get args :orderType)
    :ASC (check-error (get-tags-ordered-by-beer-popularity-asc args))
    :DESC (check-error (get-tags-ordered-by-beer-popularity-desc args))))
