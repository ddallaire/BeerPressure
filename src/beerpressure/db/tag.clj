(ns beerpressure.db.tag
  (:require [environ.core :refer [env]]
            [yesql.core :refer [defqueries]]
            [beerpressure.db.common :refer :all]))

(defqueries "sql/operations_tag.sql"
            {:connection db-spec})

(defn resolve-tags
  [context args _value]
  (check-error (case (get args :orderBy)
                 :NAME (case (get args :orderType)
                         :ASC (get-tags-ordered-by-name-asc args)
                         :DESC (get-tags-ordered-by-name-desc args))
                 :BREWERY_POPULARITY (case (get args :orderType)
                                       :ASC (get-tags-ordered-by-brewery-popularity-asc args)
                                       :DESC (get-tags-ordered-by-brewery-popularity-desc args))
                 :BEER_POPULARITY (case (get args :orderType)
                                    :ASC (get-tags-ordered-by-beer-popularity-asc args)
                                    :DESC (get-tags-ordered-by-beer-popularity-desc args)))))
