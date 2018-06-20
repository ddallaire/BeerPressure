(ns beerpressure.db.review.brewery-review
  (:require [environ.core :refer [env]]
            [yesql.core :refer [defqueries]]
            [clojure.string :as str]
            [beerpressure.db.common :refer :all]
            [clojure.java.jdbc :as jdbc]
            [beerpressure.db.user :refer [get-logged-user-from-context]]))

(defqueries "sql/review/operations_brewery_review.sql"
            {:connection db-spec})

(defn fill-brewery-review-thumbsups
  [brewery-review]
  (assoc brewery-review :thumbsups
                        (map #(hash-map :user %)
                             (get-brewery-review-thumbsups {:idBreweryReview (get brewery-review :idBreweryReview)}))))

(defn resolve-brewery-review
  [context args _value]
  (let [brewery-review (first
                         (convert-naming-convention (check-error (get-brewery-review args))))]
    (fill-user-from-row (fill-brewery-review-thumbsups brewery-review))))

(defn generate-brewery-reviews-query-beginning
  [args]
  (case (get args :orderBy)
    :TIME "SELECT id_brewery_review, \"user\".cip, name, surname, id_brewery, title, content, image_path, rating, time FROM brewery_review
               INNER JOIN \"user\" ON \"user\".cip = brewery_review.cip"
    :THUMBSUP_COUNT "SELECT id_brewery_review, \"user\".cip, name, surname, id_brewery, title, content, image_path, rating, time FROM brewery_review_with_thumbsup_count
                         INNER JOIN \"user\" ON \"user\".cip = brewery_review_with_thumbsup_count.cip"))

(defn generate-brewery-reviews-query-in-clause-breweries
  [args]
  (let [breweries (get args :breweries)]
    (if (not= breweries [])
      (str "id_brewery IN " (integer-list-to-in-clause breweries))
      "")))

(defn generate-brewery-reviews-query-in-clause-cips
  [args]
  (let [cips (get args :cips)]
    (if (not= cips [])
      (str "\"user\".cip IN " (cip-list-to-in-clause cips))
      "")))

(defn generate-brewery-reviews-query-where
  [args]
  (let [in-clauses [(generate-brewery-reviews-query-in-clause-breweries args)
                    (generate-brewery-reviews-query-in-clause-cips args)]
        valid-in-clauses (filter #(not= "" %) in-clauses)]
    (if (not= valid-in-clauses [])
      (str "WHERE " (str/join " AND " valid-in-clauses))
      "")))

(defn generate-brewery-reviews-query-order-by
  [args]
  (case (get args :orderBy)
    :TIME (case (get args :orderType)
            :ASC "ORDER BY time ASC"
            :DESC "ORDER BY time DESC")
    :THUMBSUP_COUNT (case (get args :orderType)
                      :ASC "ORDER BY thumbsup_count ASC, time ASC"
                      :DESC "ORDER BY thumbsup_count DESC, time ASC")))

(defn generate-brewery-reviews-query
  [args]
  (str (generate-brewery-reviews-query-beginning args) " "
       (generate-brewery-reviews-query-where args) " "
       (generate-brewery-reviews-query-order-by args) " "
       (generate-query-limit-offset args)))

(defn resolve-brewery-reviews
  [context args _value]
  (let [brewery-reviews (convert-naming-convention
                          (check-error (jdbc/query db-spec (generate-brewery-reviews-query args))))]
    (map #(fill-user-from-row (fill-brewery-review-thumbsups %)) brewery-reviews)))

(defn resolve-insert-brewery-review-thumbsup
  [context args _value]
  (let [cip (get (get-logged-user-from-context context) :cip)]
    (check-error (insert-brewery-review-thumbsup! (assoc args :cip cip)))))

(defn resolve-delete-brewery-review-thumbsup
  [context args _value]
  (let [cip (get (get-logged-user-from-context context) :cip)]
    (check-error (delete-brewery-review-thumbsup! (assoc args :cip cip)))))
