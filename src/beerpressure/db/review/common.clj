(ns beerpressure.db.review.common
  (:require [clojure.string :as str]
            [beerpressure.db.common :refer :all]))

(defn is-valid-rating-range
  [args]
  (let [rating (get args :rating)]
    (and (>= rating 0) (<= rating 5))))


(defn is-valid-review
  [args]
  (and (is-valid-rating-range args)))
