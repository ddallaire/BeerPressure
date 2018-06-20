(ns beerpressure.db.review.beer-review
  (:require [environ.core :refer [env]]
            [yesql.core :refer [defqueries]]
            [clojure.string :as str]
            [beerpressure.db.common :refer :all]
            [clojure.java.jdbc :as jdbc]
            [beerpressure.db.user :refer [get-logged-user-from-context]]))

(defqueries "sql/review/operations_beer_review.sql"
            {:connection db-spec})

(defn fill-beer-review-thumbsups
  [beer-review]
  (assoc beer-review :thumbsups
                     (map #(hash-map :user %)
                          (get-beer-review-thumbsups {:idBeerReview (get beer-review :idBeerReview)}))))

(defn resolve-beer-review
  [context args _value]
  (let [beer-review (first
                      (convert-naming-convention (check-error (get-beer-review args))))]
    (fill-user-from-row (fill-beer-review-thumbsups beer-review))))

(defn generate-beer-reviews-query-beginning
  [args]
  (case (get args :orderBy)
    :TIME "SELECT id_beer_review, \"user\".cip, name, surname, id_beer, title, content, image_path, rating, time FROM beer_review
               INNER JOIN \"user\" ON \"user\".cip = beer_review.cip"
    :THUMBSUP_COUNT "SELECT id_beer_review, \"user\".cip, name, surname, id_beer, title, content, image_path, rating, time FROM beer_review_with_thumbsup_count
                         INNER JOIN \"user\" ON \"user\".cip = beer_review_with_thumbsup_count.cip"))

(defn generate-beer-reviews-query-in-clause-beers
  [args]
  (let [beers (get args :beers)]
    (if (not= beers [])
      (str "id_beer IN " (integer-list-to-in-clause beers))
      "")))

(defn generate-beer-reviews-query-in-clause-cips
  [args]
  (let [cips (get args :cips)]
    (if (not= cips [])
      (str "\"user\".cip IN " (cip-list-to-in-clause cips))
      "")))

(defn generate-beer-reviews-query-where
  [args]
  (let [in-clauses [(generate-beer-reviews-query-in-clause-beers args)
                    (generate-beer-reviews-query-in-clause-cips args)]
        valid-in-clauses (filter #(not= "" %) in-clauses)]
    (if (not= valid-in-clauses [])
      (str "WHERE " (str/join " AND " valid-in-clauses))
      "")))

(defn generate-beer-reviews-query-order-by
  [args]
  (case (get args :orderBy)
    :TIME (case (get args :orderType)
            :ASC "ORDER BY time ASC"
            :DESC "ORDER BY time DESC")
    :THUMBSUP_COUNT (case (get args :orderType)
                      :ASC "ORDER BY thumbsup_count ASC, time ASC"
                      :DESC "ORDER BY thumbsup_count DESC, time ASC")))

(defn generate-beer-reviews-query
  [args]
  (str (generate-beer-reviews-query-beginning args) " "
       (generate-beer-reviews-query-where args) " "
       (generate-beer-reviews-query-order-by args) " "
       (generate-query-limit-offset args)))

(defn resolve-beer-reviews
  [context args _value]
  (let [beer-reviews (convert-naming-convention
                       (check-error (jdbc/query db-spec (generate-beer-reviews-query args))))]
    (map #(fill-user-from-row (fill-beer-review-thumbsups %)) beer-reviews)))

(defn resolve-insert-beer-review-thumbsup
  [context args _value]
  (let [cip (get (get-logged-user-from-context context) :cip)]
    (check-error (insert-beer-review-thumbsup! (assoc args :cip cip)))))

(defn resolve-delete-beer-review-thumbsup
  [context args _value]
  (let [cip (get (get-logged-user-from-context context) :cip)]
    (check-error (delete-beer-review-thumbsup! (assoc args :cip cip)))))
