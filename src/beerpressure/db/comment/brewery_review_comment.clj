(ns beerpressure.db.comment.brewery-review-comment
  (:require [environ.core :refer [env]]
            [yesql.core :refer [defqueries]]
            [clojure.string :as str]
            [beerpressure.db.common :refer :all]
            [clojure.java.jdbc :as jdbc]))

(defqueries "sql/comment/operations_brewery_comment.sql"
            {:connection db-spec})

(defn resolve-brewery-review-comment
  [context args _value]
  (first
    (convert-naming-convention (check-error (get-brewery-review-comment args)))))

(defn generate-brewery-review-comments-query-beginning
  [args]
  "SELECT id_brewery_review_comment, cip, id_brewery_review, content, time FROM brewery_review_comment")

(defn generate-brewery-review-comments-query-in-clause-brewery-reviews
  [args]
  (let [brewery-reviews (get args :breweryReviews)]
    (if (not= brewery-reviews [])
      (str "id_brewery_review IN " (integer-list-to-in-clause brewery-reviews))
      "")))

(defn generate-brewery-review-comments-query-in-clause-cips
  [args]
  (let [cips (get args :cips)]
    (if (not= cips [])
      (str "cip IN " (cip-list-to-in-clause cips))
      "")))

(defn generate-brewery-review-comments-query-where
  [args]
  (let [in-clauses [(generate-brewery-review-comments-query-in-clause-brewery-reviews args)
                    (generate-brewery-review-comments-query-in-clause-cips args)]
        valid-in-clauses (filter #(not= "" %) in-clauses)]
    (if (not= valid-in-clauses [])
      (str "WHERE " (str/join " AND " valid-in-clauses))
      "")))

(defn generate-brewery-review-comments-query-order-by
  [args]
  (case (get args :orderBy)
    :TIME (case (get args :orderType)
            :ASC "ORDER BY time ASC"
            :DESC "ORDER BY time DESC")))

(defn generate-brewery-review-comments-query
  [args]
  (str (generate-brewery-review-comments-query-beginning args) " "
       (generate-brewery-review-comments-query-where args) " "
       (generate-brewery-review-comments-query-order-by args) " "
       (generate-query-limit-offset args)))

(defn resolve-brewery-review-comments
  [context args _value]
  (convert-naming-convention
    (check-error
      (jdbc/query db-spec (generate-brewery-review-comments-query args)))))
