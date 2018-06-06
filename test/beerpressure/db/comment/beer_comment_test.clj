(ns beerpressure.db.comment.beer-comment-test
  (:require [clojure.test :refer :all]
            [beerpressure.handler :refer :all]
            [beerpressure.db.common-test :refer :all]))

(use-fixtures :once db-setup)

(deftest test-resolve-beer-review-comment
  (testing "beerReviewComment(idBeerReviewComment: 1"
    (let [graphql-query (long-str "{"
                                  " beerReviewComment(idBeerReviewComment: 1) {"
                                  "   idBeerReviewComment"
                                  "   idBeerReview"
                                  "   cip"
                                  "   content"
                                  "   time"
                                  " }"
                                  "}")
          expected-response (long-str "{"
                                      " \"data\": {"
                                      "   \"beerReviewComment\": {"
                                      "     \"idBeerReviewComment\": 1,"
                                      "     \"idBeerReview\": 1,"
                                      "     \"cip\": \"pele1704\","
                                      "     \"content\": \"Je suis d'accord avec toi!\","
                                      "     \"time\": \"2018-06-10 10:00:10.0\""
                                      "   }"
                                      " }"
                                      "}")
          response (execute-graphql-query graphql-query)]
      (is (is-data-equal response expected-response)))))

(deftest test-resolve-beer-review-comments
  (testing "beerReviewComments(idBeerReview: 3, first: 10, skip: 0)"
    (let [graphql-query (long-str "{"
                                  " beerReviewComments(idBeerReview: 3, first: 10, skip: 0) {"
                                  "   idBeerReviewComment"
                                  "   idBeerReview"
                                  "   cip"
                                  "   content"
                                  "   time"
                                  " }"
                                  "}")
          expected-response (long-str "{"
                                      " \"data\": {"
                                      "   \"beerReviewComments\": ["
                                      "     {"
                                      "       \"idBeerReviewComment\": 7,"
                                      "       \"idBeerReview\": 3,"
                                      "       \"cip\": \"royj1933\","
                                      "       \"content\": \"C'est nice les soirée entre boux! Je fais de la crème brulée\","
                                      "       \"time\": \"2018-06-10 10:00:16.0\""
                                      "     },"
                                      "     {"
                                      "       \"idBeerReviewComment\": 6,"
                                      "       \"idBeerReview\": 3,"
                                      "       \"cip\": \"alig2503\","
                                      "       \"content\": \"C'est vraiment bon ça!!!\","
                                      "       \"time\": \"2018-06-10 10:00:15.0\""
                                      "     },"
                                      "     {"
                                      "       \"idBeerReviewComment\": 3,"
                                      "       \"idBeerReview\": 3,"
                                      "       \"cip\": \"mahm1904\","
                                      "       \"content\": \"Peut-être que j'aimerais ça!\","
                                      "       \"time\": \"2018-06-10 10:00:12.0\""
                                      "     }"
                                      "   ]"
                                      " }"
                                      "}")
          response (execute-graphql-query graphql-query)]
      (is (is-data-equal response expected-response)))))
