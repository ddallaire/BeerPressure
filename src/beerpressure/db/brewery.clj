(ns beerpressure.db.brewery
  (:require [environ.core :refer [env]]
            [yesql.core :refer [defqueries]]
            [beerpressure.db.common :refer :all]
            [clojure.java.jdbc :as jdbc]
            [beerpressure.db.tag :refer [get-tags-id-or-insert-tags]]))

(defqueries "sql/operations_brewery.sql"
            {:connection db-spec})

(defn fill-brewery-tags
  [brewery]
  (assoc brewery :tags (check-error (get-brewery-tags {:id (get brewery :id)}))))

(defn resolve-brewery
  [context args _value]
  (let [brewery (first (convert-naming-convention (check-error (get-brewery args))))]
    (fill-brewery-tags brewery)))

(defn generate-breweries-query-beginning
  [args]
  (case (get args :tags)
    [] "SELECT id_brewery AS id, name, description, image_path, rating FROM brewery_with_rating"
    "SELECT brewery.id_brewery AS id, name, description, image_path, rating FROM brewery_with_rating AS brewery
    INNER JOIN tag_brewery ON tag_brewery.id_brewery = brewery.id_brewery"))

(defn generate-breweries-query-where
  [args]
  (let [tags (get args :tags)]
    (case tags
      [] ""
      (str "WHERE id_tag IN " (integer-list-to-in-clause tags)))))

(defn generate-breweries-query-group-by
  [args]
  (case (get args :tags)
    [] ""
    "GROUP BY brewery.id_brewery, name, description, image_path, rating"))

(defn generate-breweries-query-order-by
  [args]
  (case (get args :orderBy)
    :NAME (case (get args :orderType)
            :ASC "ORDER BY name ASC"
            :DESC "ORDER BY name DESC")
    :RATING (case (get args :orderType)
              :ASC "ORDER BY rating ASC, name ASC"
              :DESC "ORDER BY rating DESC, name ASC")))

(defn generate-breweries-query
  [args]
  (str (generate-breweries-query-beginning args) " "
       (generate-breweries-query-where args) " "
       (generate-breweries-query-group-by args) " "
       (generate-breweries-query-order-by args) " "
       (generate-query-limit-offset args)))


(defn resolve-breweries
  [context args _value]
  (let [breweries (check-error
                    (jdbc/query db-spec (generate-breweries-query args)))]
    (convert-naming-convention (map #(fill-brewery-tags %) breweries))))

(defn unassociate-brewery-tags
  [brewery-id]
  (check-error (delete-brewery-tags! {:id brewery-id})))

(defn associate-brewery-tags
  [tag-ids brewery-id]
  (execute-association-query "tag_brewery" "id_tag" "id_brewery" tag-ids brewery-id))

(defn resolve-insert-brewery
  [context args _value]
  (let [tag-ids (get-tags-id-or-insert-tags (get args :tags))
        brewery-id (get (first (check-error (insert-brewery args))) :id_brewery)]
    (associate-brewery-tags tag-ids brewery-id)
    (let [brewery (first (convert-naming-convention (check-error (get-brewery {:id brewery-id}))))]
      (fill-brewery-tags brewery))))

(defn resolve-update-brewery
  [context args _value]
  (let [tag-ids (get-tags-id-or-insert-tags (get args :tags))
        brewery-id (get args :id)]
    (check-error (update-brewery! args))
    (unassociate-brewery-tags brewery-id)
    (associate-brewery-tags tag-ids brewery-id)
    (let [brewery (first (convert-naming-convention (check-error (get-brewery {:id brewery-id}))))]
      (fill-brewery-tags brewery))))

(defn resolve-delete-brewery
  [context args _value]
  (unassociate-brewery-tags (get args :id))
  (check-error (delete-brewery! args)))
