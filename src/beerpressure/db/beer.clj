(ns beerpressure.db.beer
  (:require [environ.core :refer [env]]
            [yesql.core :refer [defqueries]]
            [beerpressure.db.common :refer :all]
            [beerpressure.db.brewery :refer [fill-brewery-tags]]))

(defqueries "sql/operations_beer.sql"
            {:connection db-spec})

(defn fill-beer-breweries
  [beer]
  (assoc beer :breweries (convert-naming-convention
                           (map #(fill-brewery-tags %) (get-beer-breweries {:id (get beer :id)})))))

(defn fill-beer-tags
  [beer]
  (assoc beer :tags (get-beer-tags {:id (get beer :id)})))

(defn resolve-beer
  [context args _value]
  (let [beer (first (convert-naming-convention (check-error (get-beer args))))]
    (fill-beer-tags (fill-beer-breweries beer))))

(defn resolve-beers
  [context args _value]
  (let [beers (check-error (case (get args :orderBy)
                :NAME (case (get args :breweries)
                        [] (case (get args :tags)
                             [] (case (get args :orderType)
                                  :ASC (get-beers-ordered-by-name-asc args)
                                  :DESC (get-beers-ordered-by-name-desc args))
                             (case (get args :orderType)
                               :ASC (get-beers-filtered-by-tags-ordered-by-name-asc args)
                               :DESC (get-beers-filtered-by-tags-ordered-by-name-desc args)))
                        (case (get args :tags)
                          [] (case (get args :orderType)
                               :ASC (get-beers-filtered-by-breweries-ordered-by-name-asc args)
                               :DESC (get-beers-filtered-by-breweries-ordered-by-name-desc args))
                          (case (get args :orderType)
                            :ASC (get-beers-filtered-by-breweries-tags-ordered-by-name-asc args)
                            :DESC (get-beers-filtered-by-breweries-tags-ordered-by-name-desc args))))
                :RATING (case (get args :breweries)
                          [] (case (get args :tags)
                               [] (case (get args :orderType)
                                    :ASC (get-beers-ordered-by-rating-asc args)
                                    :DESC (get-beers-ordered-by-rating-desc args))
                               (case (get args :orderType)
                                 :ASC (get-beers-filtered-by-tags-ordered-by-rating-asc args)
                                 :DESC (get-beers-filtered-by-tags-ordered-by-rating-desc args)))
                          (case (get args :tags)
                            [] (case (get args :orderType)
                                 :ASC (get-beers-filtered-by-breweries-ordered-by-rating-asc args)
                                 :DESC (get-beers-filtered-by-breweries-ordered-by-rating-desc args))
                            (case (get args :orderType)
                              :ASC (get-beers-filtered-by-breweries-tags-ordered-by-rating-asc args)
                              :DESC (get-beers-filtered-by-breweries-tags-ordered-by-rating-desc args))))))]
    (convert-naming-convention (map #(fill-beer-tags (fill-beer-breweries %)) beers))))

(defn resolve-beer-review
  [context args _value]
   (first
     (convert-naming-convention (check-error (get-beer-review args)))))

(defn resolve-beer-reviews
  [context args _value]
  (convert-naming-convention (check-error (get-beer-reviews args))))

(defn resolve-beer-review-comment
  [context args _value]
  (first
    (check-error (get-beer-review-comment args))))

(defn resolve-beer-review-comments
  [context args _value]
  (check-error (get-beer-review-comments args)))
