(ns beerpressure.schema
  (:require
    [beerpressure.db.user :as db.user]
    [beerpressure.db.tag :as db.tag]
    [beerpressure.db.beer-style :as db.beer-style]
    [beerpressure.db.beer :as db.beer]
    [beerpressure.db.brewery :as db.brewery]
    [beerpressure.db.comment.beer-review-comment :as beer-review-comment]
    [beerpressure.db.review.beer-review :as beer-review]
    [beerpressure.db.comment.brewery-review-comment :as brewery-review-comment]
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
      (attach-resolvers {:resolve-login                          db.user/resolve-login
                         :resolve-logout                         db.user/resolve-logout

                         :resolve-tags                           db.tag/resolve-tags

                         :resolve-brewery                        db.brewery/resolve-brewery
                         :resolve-breweries                      db.brewery/resolve-breweries

                         :resolve-beer-styles                    db.beer-style/resolve-beer-styles

                         :resolve-beer                           db.beer/resolve-beer
                         :resolve-beers                          db.beer/resolve-beers

                         :resolve-brewery-review                 brewery-review/resolve-brewery-review
                         :resolve-brewery-reviews                brewery-review/resolve-brewery-reviews
                         :resolve-insert-brewery-review          brewery-review/resolve-insert-brewery-review
                         :resolve-update-brewery-review          brewery-review/resolve-update-brewery-review
                         :resolve-delete-brewery-review          brewery-review/resolve-delete-brewery-review
                         :resolve-insert-brewery-review-thumbsup brewery-review/resolve-insert-brewery-review-thumbsup
                         :resolve-delete-brewery-review-thumbsup brewery-review/resolve-delete-brewery-review-thumbsup

                         :resolve-brewery-review-comment         brewery-review-comment/resolve-brewery-review-comment
                         :resolve-brewery-review-comments        brewery-review-comment/resolve-brewery-review-comments

                         :resolve-beer-review                    beer-review/resolve-beer-review
                         :resolve-beer-reviews                   beer-review/resolve-beer-reviews
                         :resolve-insert-beer-review-thumbsup    beer-review/resolve-insert-beer-review-thumbsup
                         :resolve-delete-beer-review-thumbsup    beer-review/resolve-delete-beer-review-thumbsup

                         :resolve-beer-review-comment            beer-review-comment/resolve-beer-review-comment
                         :resolve-beer-review-comments           beer-review-comment/resolve-beer-review-comments})
      schema/compile))
