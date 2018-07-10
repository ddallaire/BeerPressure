(ns beerpressure.db.profile
  (:require [environ.core :refer [env]]
            [yesql.core :refer [defqueries]]
            [beerpressure.db.common :refer :all]
            [beerpressure.db.user :as user]
            [beerpressure.db.brewery :as brewery]
            [beerpressure.db.beer :as beer]
            [beerpressure.db.review.brewery-review :as brewery-review]
            [beerpressure.db.review.beer-review :as beer-review]))

(defqueries "sql/operations_profile.sql"
            {:connection db-spec})

(defn get-user
  [args]
  (first (check-error (user/get-user args))))

(defn get-brewery-reviews-written-by
  [args]
  (let [brewery-reviews (convert-naming-convention
                          (check-error (get-brewery-reviews-written-by-query args)))]
    (map #(fill-user-from-row (brewery-review/fill-brewery-review-thumbsups %)) brewery-reviews)))

(defn get-brewery-and-review-container
  [review]
  {:brewery (brewery/resolve-brewery {} {:id (get review :idBrewery)} {})
   :review  review})

(defn get-brewery-reviewed-by
  [args]
  (let [brewery-reviews (get-brewery-reviews-written-by args)]
    (map #(get-brewery-and-review-container %) brewery-reviews)))

(defn get-beer-reviews-written-by
  [args]
  (let [beer-reviews (convert-naming-convention
                       (check-error (get-beer-reviews-written-by-query args)))]
    (map #(fill-user-from-row (beer-review/fill-beer-review-thumbsups %)) beer-reviews)))

(defn get-beer-and-review-container
  [review]
  {:beer   (beer/resolve-beer {} {:id (get review :idBeer)} {})
   :review review})

(defn get-beer-reviewed-by
  [args]
  (let [beer-reviews (get-beer-reviews-written-by args)]
    (map #(get-beer-and-review-container %) beer-reviews)))

(defn get-brewery-review-and-comment-container
  [comment]
  {:breweryReview (brewery-review/resolve-brewery-review {} {:idBreweryReview (get comment :idBreweryReview)} {})
   :comment       (fill-user-from-row comment)})

(defn get-brewery-reviews-commented-by
  [args]
  (let [brewery-review-comments (convert-naming-convention
                                  (check-error (get-brewery-review-comments-query args)))]
    (map #(get-brewery-review-and-comment-container %) brewery-review-comments)))

(defn get-beer-review-and-comment-container
  [comment]
  {:beerReview (beer-review/resolve-beer-review {} {:idBeerReview (get comment :idBeerReview)} {})
   :comment    (fill-user-from-row comment)})

(defn get-beer-reviews-commented-by
  [args]
  (let [beer-reviews-comments (convert-naming-convention
                                (check-error (get-beer-review-comments-query args)))]
    (map #(get-beer-review-and-comment-container %) beer-reviews-comments)))

(defn get-brewery-reviews-thumbsup-by
  [args]
  (let [brewery-reviews (convert-naming-convention
                          (check-error (get-brewery-reviews-thumbsup-by-query args)))]
    (map #(fill-user-from-row (brewery-review/fill-brewery-review-thumbsups %)) brewery-reviews)))

(defn get-beer-reviews-thumbsup-by
  [args]
  (let [beer-reviews (convert-naming-convention
                       (check-error (get-beer-reviews-thumbsup-by-query args)))]
    (map #(fill-user-from-row (beer-review/fill-beer-review-thumbsups %)) beer-reviews)))

(defn resolve-profile
  [context args value]
  {:user                      (get-user args)
   :breweryReviewedBy         (get-brewery-reviewed-by args)
   :beerReviewedBy            (get-beer-reviewed-by args)
   :breweryReviewsCommentedBy (get-brewery-reviews-commented-by args)
   :beerReviewsCommentedBy    (get-beer-reviews-commented-by args)
   :breweryReviewsThumbsupBy  (get-brewery-reviews-thumbsup-by args)
   :beerReviewsThumbsupBy     (get-beer-reviews-thumbsup-by args)})
