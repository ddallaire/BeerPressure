(ns beerpressure.db.beer
  (:require [environ.core :refer [env]]
            [clojure.string :as str]
            [yesql.core :refer [defqueries]]
            [beerpressure.db.common :refer :all]
            [beerpressure.db.brewery :refer [fill-brewery-tags]]
            [clojure.java.jdbc :as jdbc]
            [beerpressure.db.tag :refer [get-tags-id-or-insert-tags]]))

(defqueries "sql/operations_beer.sql"
            {:connection db-spec})

(defn fill-beer-breweries
  [beer]
  (assoc beer :breweries (convert-naming-convention
                           (map #(fill-brewery-tags %) (get-beer-breweries {:id (get beer :id)})))))

(defn fill-beer-tags
  [beer]
  (assoc beer :tags (get-beer-tags {:id (get beer :id)})))

(defn fill-beer-style
  [beer]
  (assoc beer :style {:id (get beer :id_style), :name (get beer :name_style)}))

(defn resolve-beer
  [context args _value]
  (let [beer (first (convert-naming-convention (check-error (get-beer args))))]
    (fill-beer-style (fill-beer-tags (fill-beer-breweries beer)))))

(defn generate-beers-query-beginning
  [args]
  (let [breweries (get args :breweries)
        tags (get args :tags)]
    (case breweries
      [] (case tags
           [] "SELECT id_beer AS id, name, description, ibu, alcohol_percent, image_path, rating, id_style, name_style FROM beer_with_rating"
           "SELECT beer.id_beer AS id, beer.name, description, ibu, alcohol_percent, image_path, rating, id_style, name_style FROM beer_with_rating AS beer
            INNER JOIN beer_tag ON beer_tag.id_beer = beer.id_beer")
      (case tags
        [] "SELECT beer.id_beer AS id, beer.name, description, ibu, alcohol_percent, image_path, rating, id_style, name_style FROM beer_with_rating AS beer
           INNER JOIN beer_brewery ON beer_brewery.id_beer = beer.id_beer"
        "SELECT beer.id_beer AS id, beer.name, description, ibu, alcohol_percent, image_path, rating, id_style, name_style FROM beer_with_rating AS beer
        INNER JOIN beer_brewery ON beer_brewery.id_beer = beer.id_beer
        INNER JOIN beer_tag ON beer_tag.id_beer = beer.id_beer"))))

(defn generate-beers-query-in-clause-breweries
  [args]
  (let [breweries (get args :breweries)]
    (if (not= breweries [])
      (str "id_brewery IN " (integer-list-to-in-clause breweries))
      "")))

(defn generate-beers-query-in-clause-tags
  [args]
  (let [tags (get args :tags)]
    (if (not= tags [])
      (str "id_tag IN " (integer-list-to-in-clause tags))
      "")))

(defn generate-beers-query-in-clause-styles
  [args]
  (let [styles (get args :styles)]
    (if (not= styles [])
      (str "id_style IN " (integer-list-to-in-clause styles))
      "")))

(defn generate-beers-query-where
  [args]
  (let [in-clauses [(generate-beers-query-in-clause-breweries args)
                    (generate-beers-query-in-clause-tags args)
                    (generate-beers-query-in-clause-styles args)]
        valid-in-clauses (filter #(not= "" %) in-clauses)]
    (if (not= valid-in-clauses [])
      (str "WHERE " (str/join " AND " valid-in-clauses))
      "")))

(defn generate-beers-query-group-by
  [args]
  (if (or (not= (get args :breweries) []) (not= (get args :tags) []))
    "GROUP BY beer.id_beer, beer.name, description, ibu, alcohol_percent, image_path, rating, id_style, name_style"
    ""))

(defn generate-beers-query-order-by
  [args]
  (case (get args :orderBy)
    :NAME (case (get args :orderType)
            :ASC "ORDER BY name ASC"
            :DESC "ORDER BY name DESC")
    :RATING (case (get args :orderType)
              :ASC "ORDER BY rating ASC, name ASC"
              :DESC "ORDER BY rating DESC, name ASC")))

(defn generate-beers-query
  [args]
  (str (generate-beers-query-beginning args) " "
       (generate-beers-query-where args) " "
       (generate-beers-query-group-by args) " "
       (generate-beers-query-order-by args) " "
       (generate-query-limit-offset args)))

(defn resolve-beers
  [context args _value]
  (let [beers (check-error
                (jdbc/query db-spec (generate-beers-query args)))]
    (convert-naming-convention (map #(fill-beer-style (fill-beer-tags (fill-beer-breweries %))) beers))))

(defn unassociate-beer-breweries
  [beer-id]
  (check-error (delete-beer-breweries! {:id beer-id})))

(defn associate-beer-breweries
  [brewery-ids beer-id]
  (execute-association-query "beer_brewery" "id_brewery" "id_beer" brewery-ids beer-id))

(defn unassociate-beer-tags
  [beer-id]
  (check-error (delete-beer-tags! {:id beer-id})))

(defn associate-beer-tags
  [tag-ids beer-id]
  (execute-association-query "beer_tag" "id_tag" "id_beer" tag-ids beer-id))

(defn resolve-insert-beer
  [context args _value]
  (let [tag-ids (get-tags-id-or-insert-tags (get args :tags))
        beer-id (get (first (check-error (insert-beer args))) :id_beer)]
    (associate-beer-breweries (get args :breweries) beer-id)
    (associate-beer-tags tag-ids beer-id)
    (let [beer (first (convert-naming-convention (check-error (get-beer {:id beer-id}))))]
      (fill-beer-style (fill-beer-tags (fill-beer-breweries beer))))))

(defn resolve-update-beer
  [context args _value]
  (let [tag-ids (get-tags-id-or-insert-tags (get args :tags))
        beer-id (get args :id)]
    (check-error (update-beer! args))
    (unassociate-beer-breweries beer-id)
    (associate-beer-breweries (get args :breweries) beer-id)
    (unassociate-beer-tags beer-id)
    (associate-beer-tags tag-ids beer-id)
    (let [beer (first (convert-naming-convention (check-error (get-beer {:id beer-id}))))]
      (fill-beer-style (fill-beer-tags (fill-beer-breweries beer))))))

(defn resolve-delete-beer
  [context args _value]
  (let [beer-id (get args :id)]
    (unassociate-beer-breweries beer-id)
    (unassociate-beer-tags beer-id)
    (check-error (delete-beer! args))))
