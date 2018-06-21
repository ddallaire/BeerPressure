(ns beerpressure.db.comment.beer-review-comment
  (:require [environ.core :refer [env]]
            [yesql.core :refer [defqueries]]
            [clojure.string :as str]
            [beerpressure.db.common :refer :all]
            [clojure.java.jdbc :as jdbc]
            [beerpressure.db.user :refer [get-logged-user-from-context]]))

(defqueries "sql/comment/operations_beer_comment.sql"
            {:connection db-spec})

(defn resolve-beer-review-comment
  [context args _value]
  (let [beer-review-comment (first
                              (convert-naming-convention (check-error (get-beer-review-comment args))))]
    (fill-user-from-row beer-review-comment)))

(defn generate-beer-review-comments-query-beginning
  [args]
  "SELECT id_beer_review_comment, id_beer_review, \"user\".cip, name, surname, content, time FROM beer_review_comment
      INNER JOIN \"user\" ON \"user\".cip = beer_review_comment.cip")

(defn generate-beer-review-comments-query-in-clause-beer-reviews
  [args]
  (let [beer-reviews (get args :beerReviews)]
    (if (not= beer-reviews [])
      (str "id_beer_review IN " (integer-list-to-in-clause beer-reviews))
      "")))

(defn generate-beer-review-comments-query-in-clause-cips
  [args]
  (let [cips (get args :cips)]
    (if (not= cips [])
      (str "\"user\".cip IN " (cip-list-to-in-clause cips))
      "")))

(defn generate-beer-review-comments-query-where
  [args]
  (let [in-clauses [(generate-beer-review-comments-query-in-clause-beer-reviews args)
                    (generate-beer-review-comments-query-in-clause-cips args)]
        valid-in-clauses (filter #(not= "" %) in-clauses)]
    (if (not= valid-in-clauses [])
      (str "WHERE " (str/join " AND " valid-in-clauses))
      "")))

(defn generate-beer-review-comments-query-order-by
  [args]
  (case (get args :orderBy)
    :TIME (case (get args :orderType)
            :ASC "ORDER BY time ASC"
            :DESC "ORDER BY time DESC")))

(defn generate-beer-review-comments-query
  [args]
  (str (generate-beer-review-comments-query-beginning args) " "
       (generate-beer-review-comments-query-where args) " "
       (generate-beer-review-comments-query-order-by args) " "
       (generate-query-limit-offset args)))

(defn resolve-beer-review-comments
  [context args _value]
  (let [beer-review-comments (convert-naming-convention
                               (check-error (jdbc/query db-spec (generate-beer-review-comments-query args))))]
    (map #(fill-user-from-row %) beer-review-comments)))

(defn resolve-insert-beer-review-comment
  [context args _value]
  (let [cip (get (get-logged-user-from-context context) :cip)
        idBeerReviewComment (get (first
                                      (check-error
                                        (insert-beer-review-comment (assoc args :cip cip))))
                                    :id_beer_review_comment)]
    (let [beer-review-comment (first (convert-naming-convention
                                          (check-error (get-beer-review-comment
                                                         (hash-map :idBeerReviewComment idBeerReviewComment)))))]
      (fill-user-from-row beer-review-comment))))

(defn resolve-update-beer-review-comment
  [context args _value]
  (let [cip (get (get-logged-user-from-context context) :cip)]
    (check-error (update-beer-review-comment! (assoc args :cip cip)))
    (let [beer-review-comment (first (convert-naming-convention
                                          (check-error (get-beer-review-comment args))))]
      (fill-user-from-row beer-review-comment))))

(defn resolve-delete-beer-review-comment
  [context args _value]
  (let [cip (get (get-logged-user-from-context context) :cip)]
    (check-error (delete-beer-review-comment! (assoc args :cip cip)))))
