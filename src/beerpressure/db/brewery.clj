(ns beerpressure.db.brewery
  (:require [environ.core :refer [env]]
            [yesql.core :refer [defqueries]]
            [beerpressure.db.common :refer [db-spec, check-error, convert-naming-convention]]))

(defqueries "sql/operations_brewery.sql"
            {:connection db-spec})

(defn fill-brewery-tags
  [brewery]
  (assoc brewery :tags (check-error (get-brewery-tags {:id (get brewery :id)}))))

(defn resolve-brewery
  [context args _value]
  (let [brewery (first (convert-naming-convention (check-error (get-brewery args))))]
    (fill-brewery-tags brewery)))

(defn resolve-breweries
  [context args _value]
  (let [breweries (check-error (case (get args :orderBy)
                                 :NAME (case (get args :tags)
                                         [] (case (get args :orderType)
                                              :ASC (get-breweries-ordered-by-name-asc args)
                                              :DESC (get-breweries-ordered-by-name-desc args))
                                         (case (get args :orderType)
                                           :ASC (get-breweries-filtered-by-tags-ordered-by-name-asc args)
                                           :DESC (get-breweries-filtered-by-tags-ordered-by-name-desc args)))
                                 :RATING (case (get args :tags)
                                           [] (case (get args :orderType)
                                                :ASC (get-breweries-ordered-by-rating-asc args)
                                                :DESC (get-breweries-ordered-by-rating-desc args))
                                           (case (get args :orderType)
                                             :ASC (get-breweries-filtered-by-tags-ordered-by-rating-asc args)
                                             :DESC (get-breweries-filtered-by-tags-ordered-by-rating-desc args)))))]
    (convert-naming-convention (map #(fill-brewery-tags %) breweries))))
