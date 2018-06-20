(ns beerpressure.db.comment.beer-review-comment-test
  (:require [clojure.test :refer :all]
            [beerpressure.handler :refer :all]
            [beerpressure.db.common-test :refer :all]))

(use-fixtures :once db-setup-with-logged-user-fixture)

(deftest test-resolve-beer-review-comment
  (testing "beerReviewComment(idBeerReviewComment: 1"
    (let [graphql-query (long-str "{"
                                  "  beerReviewComment(idBeerReviewComment: 1) {"
                                  "    idBeerReviewComment"
                                  "    idBeerReview"
                                  "    user {"
                                  "      cip"
                                  "      name"
                                  "      surname"
                                  "    }"
                                  "    content"
                                  "    time"
                                  "  }"
                                  "}")
          expected-response (long-str "{"
                                      " \"data\": {"
                                      "   \"beerReviewComment\": {"
                                      "     \"idBeerReviewComment\": 1,"
                                      "     \"idBeerReview\": 1,"
                                      "     \"user\": {"
                                      "       \"cip\": \"pele1704\","
                                      "       \"name\": \"Étienne\""
                                      "       \"surname\": \"Pelletier\""
                                      "     }"
                                      "     \"content\": \"Je suis d'accord avec toi!\","
                                      "     \"time\": \"2018-06-10 10:00:10.0\""
                                      "   }"
                                      " }"
                                      "}")
          response (execute-graphql-query graphql-query)]
      (is (is-data-equal response expected-response)))))

(deftest test-resolve-beer-review-comments
  (testing "beerReviewComments(skip: 0, first: 2, orderBy: TIME, orderType: ASC)"
    (let [graphql-query (long-str "{"
                                  "  beerReviewComments(skip: 0, first: 2, orderBy: TIME, orderType: ASC) {"
                                  "    idBeerReviewComment"
                                  "    idBeerReview"
                                  "    user {"
                                  "      cip"
                                  "      name"
                                  "      surname"
                                  "    }"
                                  "    content"
                                  "    time"
                                  " }"
                                  "}")
          expected-response (long-str "{"
                                      " \"data\": {"
                                      "   \"beerReviewComments\": ["
                                      "     {"
                                      "       \"idBeerReviewComment\": 1,"
                                      "       \"idBeerReview\": 1,"
                                      "       \"user\": {"
                                      "         \"cip\": \"pele1704\","
                                      "         \"name\": \"Étienne\""
                                      "         \"surname\": \"Pelletier\""
                                      "       }"
                                      "       \"content\": \"Je suis d'accord avec toi!\","
                                      "       \"time\": \"2018-06-10 10:00:10.0\""
                                      "     },"
                                      "     {"
                                      "       \"idBeerReviewComment\": 2,"
                                      "       \"idBeerReview\": 2,"
                                      "       \"user\": {"
                                      "         \"cip\": \"parp2009\","
                                      "         \"name\": \"Pierre\""
                                      "         \"surname\": \"Parrat\""
                                      "       }"
                                      "       \"content\": \"J'ai tellement été saoul un soir en buvant ça!!!\","
                                      "       \"time\": \"2018-06-10 10:00:11.0\""
                                      "     }"
                                      "   ]"
                                      " }"
                                      "}")
          response (execute-graphql-query graphql-query)]
      (is (is-data-equal response expected-response))))
  (testing "beerReviewComments(skip: 0, first: 1, orderBy: TIME, orderType: DESC)"
    (let [graphql-query (long-str "{"
                                  "  beerReviewComments(skip: 0, first: 1, orderBy: TIME, orderType: DESC) {"
                                  "    idBeerReviewComment"
                                  " }"
                                  "}")
          expected-response (long-str "{"
                                      " \"data\": {"
                                      "   \"beerReviewComments\": ["
                                      "     {"
                                      "       \"idBeerReviewComment\": 7"
                                      "     }"
                                      "   ]"
                                      " }"
                                      "}")
          response (execute-graphql-query graphql-query)]
      (is (is-data-equal response expected-response))))
  (testing "beerReviewComments(skip: 0, first: 10, orderBy: TIME, orderType: ASC, beerReviews: [3])"
    (let [graphql-query (long-str "{"
                                  "  beerReviewComments(skip: 0, first: 10, orderBy: TIME, orderType: ASC, beerReviews: [3]) {"
                                  "    idBeerReviewComment"
                                  " }"
                                  "}")
          expected-response (long-str "{"
                                      " \"data\": {"
                                      "   \"beerReviewComments\": ["
                                      "     {"
                                      "       \"idBeerReviewComment\": 3"
                                      "     },"
                                      "     {"
                                      "       \"idBeerReviewComment\": 6"
                                      "     },"
                                      "     {"
                                      "       \"idBeerReviewComment\": 7"
                                      "     }"
                                      "   ]"
                                      " }"
                                      "}")
          response (execute-graphql-query graphql-query)]
      (is (is-data-equal response expected-response))))
  (testing "beerReviewComments(skip: 0, first: 10, orderBy: TIME, orderType: ASC, cips: [\\\"pele1704\\\", \\\"lara2318\\\"])"
    (let [graphql-query (long-str "{"
                                  "  beerReviewComments(skip: 0, first: 10, orderBy: TIME, orderType: ASC, cips: [\\\"pele1704\\\", \\\"lara2318\\\"]) {"
                                  "    idBeerReviewComment"
                                  " }"
                                  "}")
          expected-response (long-str "{"
                                      " \"data\": {"
                                      "   \"beerReviewComments\": ["
                                      "     {"
                                      "       \"idBeerReviewComment\": 1"
                                      "     },"
                                      "     {"
                                      "       \"idBeerReviewComment\": 4"
                                      "     }"
                                      "   ]"
                                      " }"
                                      "}")
          response (execute-graphql-query graphql-query)]
      (is (is-data-equal response expected-response))))
  (testing "beerReviewComments(skip: 0, first: 10, orderBy: TIME, orderType: ASC, beerReviews: [3], cips: [\\\"alig2503\\\", \\\"royj1933\\\"])"
    (let [graphql-query (long-str "{"
                                  "  beerReviewComments(skip: 0, first: 10, orderBy: TIME, orderType: ASC, beerReviews: [3], cips: [\\\"alig2503\\\", \\\"royj1933\\\"]) {"
                                  "    idBeerReviewComment"
                                  " }"
                                  "}")
          expected-response (long-str "{"
                                      " \"data\": {"
                                      "   \"beerReviewComments\": ["
                                      "     {"
                                      "       \"idBeerReviewComment\": 6"
                                      "     },"
                                      "     {"
                                      "       \"idBeerReviewComment\": 7"
                                      "     }"
                                      "   ]"
                                      " }"
                                      "}")
          response (execute-graphql-query graphql-query)]
      (is (is-data-equal response expected-response)))))
