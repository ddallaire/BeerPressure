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
  (testing "breweryReviewComments(skip: 0, first: 2, orderBy: TIME, orderType: ASC)"
    (let [graphql-query (long-str "{"
                                  "  breweryReviewComments(skip: 0, first: 2, orderBy: TIME, orderType: ASC) {"
                                  "    idBreweryReviewComment"
                                  "    idBreweryReview"
                                  "    cip"
                                  "    content"
                                  "    time"
                                  " }"
                                  "}")
          expected-response (long-str "{"
                                      " \"data\": {"
                                      "   \"breweryReviewComments\": ["
                                      "     {"
                                      "       \"idBreweryReviewComment\": 1,"
                                      "       \"idBreweryReview\": 2,"
                                      "       \"cip\": \"alig2503\","
                                      "       \"content\": \"Comment oses-tu dire ceci?\","
                                      "       \"time\": \"2018-06-10 10:00:10.0\""
                                      "     },"
                                      "     {"
                                      "       \"idBreweryReviewComment\": 2,"
                                      "       \"idBreweryReview\": 3,"
                                      "       \"cip\": \"dald2202\","
                                      "       \"content\": \"Je suis bien d'accord!\","
                                      "       \"time\": \"2018-06-10 10:00:11.0\""
                                      "     }"
                                      "   ]"
                                      " }"
                                      "}")
          response (execute-graphql-query graphql-query)]
      (is (is-data-equal response expected-response))))
  (testing "breweryReviewComments(skip: 0, first: 1, orderBy: TIME, orderType: DESC)"
    (let [graphql-query (long-str "{"
                                  "  breweryReviewComments(skip: 0, first: 1, orderBy: TIME, orderType: DESC) {"
                                  "    idBreweryReviewComment"
                                  " }"
                                  "}")
          expected-response (long-str "{"
                                      " \"data\": {"
                                      "   \"breweryReviewComments\": ["
                                      "     {"
                                      "       \"idBreweryReviewComment\": 7"
                                      "     }"
                                      "   ]"
                                      " }"
                                      "}")
          response (execute-graphql-query graphql-query)]
      (is (is-data-equal response expected-response))))
  (testing "breweryReviewComments(skip: 0, first: 10, orderBy: TIME, orderType: ASC, breweryReviews: [3])"
    (let [graphql-query (long-str "{"
                                  "  breweryReviewComments(skip: 0, first: 10, orderBy: TIME, orderType: ASC, breweryReviews: [3]) {"
                                  "    idBreweryReviewComment"
                                  " }"
                                  "}")
          expected-response (long-str "{"
                                      " \"data\": {"
                                      "   \"breweryReviewComments\": ["
                                      "     {"
                                      "       \"idBreweryReviewComment\": 2"
                                      "     }"
                                      "   ]"
                                      " }"
                                      "}")
          response (execute-graphql-query graphql-query)]
      (is (is-data-equal response expected-response))))
  (testing "breweryReviewComments(skip: 0, first: 10, orderBy: TIME, orderType: ASC, cips: [\\\"pele1704\\\", \\\"lara2318\\\"])"
    (let [graphql-query (long-str "{"
                                  "  breweryReviewComments(skip: 0, first: 10, orderBy: TIME, orderType: ASC, cips: [\\\"pele1704\\\", \\\"lara2318\\\"]) {"
                                  "    idBreweryReviewComment"
                                  " }"
                                  "}")
          expected-response (long-str "{"
                                      " \"data\": {"
                                      "   \"breweryReviewComments\": ["
                                      "     {"
                                      "       \"idBreweryReviewComment\": 3"
                                      "     },"
                                      "     {"
                                      "       \"idBreweryReviewComment\": 6"
                                      "     }"
                                      "   ]"
                                      " }"
                                      "}")
          response (execute-graphql-query graphql-query)]
      (is (is-data-equal response expected-response))))
  (testing "breweryReviewComments(skip: 0, first: 10, orderBy: TIME, orderType: ASC, breweryReviews: [1], cips: [\\\"alig2503\\\", \\\"royj1933\\\"])"
    (let [graphql-query (long-str "{"
                                  "  breweryReviewComments(skip: 0, first: 10, orderBy: TIME, orderType: ASC, breweryReviews: [1], cips: [\\\"alig2503\\\", \\\"royj1933\\\"]) {"
                                  "    idBreweryReviewComment"
                                  " }"
                                  "}")
          expected-response (long-str "{"
                                      " \"data\": {"
                                      "   \"breweryReviewComments\": ["
                                      "     {"
                                      "       \"idBreweryReviewComment\": 7"
                                      "     }"
                                      "   ]"
                                      " }"
                                      "}")
          response (execute-graphql-query graphql-query)]
      (is (is-data-equal response expected-response)))))
