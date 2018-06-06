(ns beerpressure.db.comment.brewery-comment-test
  (:require [clojure.test :refer :all]
            [beerpressure.handler :refer :all]
            [beerpressure.db.common-test :refer :all]))

(use-fixtures :once db-setup-with-logged-user-fixture)

(deftest test-resolve-brewery-review-comment
  (testing "breweryReviewComment(idBreweryReviewComment: 1)"
    (let [graphql-query (long-str "{"
                                  " breweryReviewComment(idBreweryReviewComment: 1) {"
                                  "   idBreweryReviewComment"
                                  "   idBreweryReview"
                                  "   cip"
                                  "   content"
                                  "   time"
                                  " }"
                                  "}")
          expected-response (long-str "{"
                                      " \"data\": {"
                                      "   \"breweryReviewComment\": {"
                                      "     \"idBreweryReviewComment\": 1,"
                                      "     \"idBreweryReview\": 2,"
                                      "     \"cip\": \"alig2503\","
                                      "     \"content\": \"Comment oses-tu dire ceci?\","
                                      "     \"time\": \"2018-06-10 10:00:10.0\""
                                      "   }"
                                      " }"
                                      "}")
          response (execute-graphql-query graphql-query)]
      (is (is-data-equal response expected-response)))))

(deftest test-resolve-brewery-review-comments
  (testing "breweryReviewComments(idBreweryReview: 5, first:10, skip: 0)"
    (let [graphql-query (long-str "{"
                                  " breweryReviewComments(idBreweryReview: 5, first:10, skip: 0) {"
                                  "   idBreweryReviewComment"
                                  "   idBreweryReview"
                                  "   cip"
                                  "   content"
                                  "   time"
                                  " }"
                                  "}")
          expected-response (long-str "{"
                                      " \"data\": {"
                                      "   \"breweryReviewComments\": ["
                                      "     {"
                                      "       \"idBreweryReviewComment\": 5,"
                                      "       \"idBreweryReview\": 5,"
                                      "       \"cip\": \"parp2009\","
                                      "       \"content\": \"PARTY!!! PARTY!!! PARTY!!! PARTY!!!\","
                                      "       \"time\": \"2018-06-10 10:00:14.0\""
                                      "     },"
                                      "     {"
                                      "       \"idBreweryReviewComment\": 4,"
                                      "       \"idBreweryReview\": 5,"
                                      "       \"cip\": \"mahm1904\","
                                      "       \"content\": \"Party!!!\","
                                      "       \"time\": \"2018-06-10 10:00:13.0\""
                                      "     }"
                                      "   ]"
                                      " }"
                                      "}")
          response (execute-graphql-query graphql-query)]
      (is (is-data-equal response expected-response)))))
