(ns beerpressure.schema
  (:require
   [beerpressure.db.user :as db.user]
   [beerpressure.db.tag :as db.tag]
   [beerpressure.db.beer :as db.beer]
   [beerpressure.db.brewery :as db.brewery]
   [beerpressure.db.comment.beer-comment :as beer-comment]
   [beerpressure.db.review.beer-review :as beer-review]
   [beerpressure.db.comment.brewery-comment :as brewery-comment]
   [beerpressure.db.review.brewery-review :as brewery-review]
   [clojure.java.io :as io]
   [clojure.edn :as edn]
   [com.walmartlabs.lacinia.schema :as schema]
   [com.walmartlabs.lacinia.util :refer [attach-resolvers]]))

(defn beerpressure-schema
  []
  (-> (io/resource "edn/beerpressure-schema.edn")
      slurp
      edn/read-string
      (attach-resolvers {:resolve-login db.user/resolve-login
                         :resolve-logout db.user/resolve-logout
                         :resolve-tags-ordered-by-name db.tag/resolve-tags-ordered-by-name
                         :resolve-tags-ordered-by-brewery-popularity db.tag/resolve-tags-ordered-by-brewery-popularity
                         :resolve-tags-ordered-by-beer-popularity db.tag/resolve-tags-ordered-by-beer-popularity
                         :resolve-brewery db.brewery/resolve-brewery
                         :resolve-breweries db.brewery/resolve-breweries
                         :resolve-beer db.beer/resolve-beer
                         :resolve-beers db.beer/resolve-beers})
                         :resolve-brewery-review          brewery-review/resolve-brewery-review
                         :resolve-brewery-reviews         brewery-review/resolve-brewery-reviews
                         :resolve-brewery-review-comment  brewery-comment/resolve-brewery-review-comment
                         :resolve-brewery-review-comments brewery-comment/resolve-brewery-review-comments
                         :resolve-beer-review             beer-review/resolve-beer-review
                         :resolve-beer-reviews            beer-review/resolve-beer-reviews
                         :resolve-beer-review-comment     beer-comment/resolve-beer-review-comment
                         :resolve-beer-review-comments    beer-comment/resolve-beer-review-comments})
      schema/compile))
