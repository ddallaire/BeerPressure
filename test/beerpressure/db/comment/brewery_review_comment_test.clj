(ns beerpressure.db.comment.brewery-review-comment-test
  (:require [clojure.test :refer :all]
            [beerpressure.handler :refer :all]
            [beerpressure.db.common-test :refer :all]))

(use-fixtures :once db-setup-with-logged-user-fixture)

(deftest test-resolve-brewery-review-comment
  (testing "breweryReviewComment(idBreweryReviewComment: 1)"
    (let [graphql-query (long-str "{"
                                  "  breweryReviewComment(idBreweryReviewComment: 1) {"
                                  "    idBreweryReviewComment"
                                  "    idBreweryReview"
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
                                      "   \"breweryReviewComment\": {"
                                      "     \"idBreweryReviewComment\": 1,"
                                      "     \"idBreweryReview\": 2,"
                                      "     \"user\": {"
                                      "       \"cip\": \"alig2503\","
                                      "       \"name\": \"Gaétan\""
                                      "       \"surname\": \"Allano\""
                                      "     }"
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
                                      "   \"breweryReviewComments\": ["
                                      "     {"
                                      "       \"idBreweryReviewComment\": 1,"
                                      "       \"idBreweryReview\": 2,"
                                      "       \"user\": {"
                                      "         \"cip\": \"alig2503\","
                                      "         \"name\": \"Gaétan\""
                                      "         \"surname\": \"Allano\""
                                      "       }"
                                      "       \"content\": \"Comment oses-tu dire ceci?\","
                                      "       \"time\": \"2018-06-10 10:00:10.0\""
                                      "     },"
                                      "     {"
                                      "       \"idBreweryReviewComment\": 2,"
                                      "       \"idBreweryReview\": 3,"
                                      "       \"user\": {"
                                      "         \"cip\": \"dald2202\","
                                      "         \"name\": \"David\""
                                      "         \"surname\": \"Dallaire\""
                                      "       }"
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

(deftest test-brewery-review-mutations
  (testing "brewery review comment mutations"
    (let [insert-graphql-query (long-str "mutation insertBreweryReviewComment {"
                                         "  insertBreweryReviewComment(idBreweryReview: 1, content: \\\"A content\\\") {"
                                         "    idBreweryReviewComment"
                                         "    idBreweryReview"
                                         "    user {"
                                         "      cip"
                                         "      name"
                                         "      surname"
                                         "    }"
                                         "    content"
                                         "  }"
                                         "}")
          update-graphql-query (long-str "mutation updateBreweryReviewComment {"
                                         "  updateBreweryReviewComment(idBreweryReviewComment: 100, content: \\\"A new content\\\") {"
                                         "    idBreweryReviewComment"
                                         "    idBreweryReview"
                                         "    user {"
                                         "      cip"
                                         "      name"
                                         "      surname"
                                         "    }"
                                         "    content"
                                         "  }"
                                         "}")
          delete-graphql-query (long-str "mutation deleteBreweryReviewComment {"
                                         "  deleteBreweryReviewComment(id: 100)"
                                         "}")
          expected-insert-response (long-str "{"
                                             " \"data\": {"
                                             "   \"insertBreweryReviewComment\": {"
                                             "     \"idBreweryReviewComment\": 100,"
                                             "     \"user\": {"
                                             "       \"cip\": \"test1234\","
                                             "       \"name\": \"testName\""
                                             "       \"surname\": \"testSurname\""
                                             "     }"
                                             "     \"idBreweryReview\": 1,"
                                             "     \"content\": \"A content\""
                                             "   }"
                                             " }"
                                             "}")
          expected-update-response (long-str "{"
                                             " \"data\": {"
                                             "   \"updateBreweryReviewComment\": {"
                                             "     \"idBreweryReviewComment\": 100,"
                                             "     \"user\": {"
                                             "       \"cip\": \"test1234\","
                                             "       \"name\": \"testName\""
                                             "       \"surname\": \"testSurname\""
                                             "     }"
                                             "     \"idBreweryReview\": 1,"
                                             "     \"content\": \"A new content\""
                                             "   }"
                                             " }"
                                             "}")]
      (is (is-data-equal (execute-graphql-query insert-graphql-query) expected-insert-response))
      (is (not (empty? (query-sql-statement "SELECT * FROM brewery_review_comment WHERE id_brewery_review_comment = 100 AND cip = 'test1234' AND time <= now()"))))
      (is (is-data-equal (execute-graphql-query update-graphql-query) expected-update-response))
      (execute-graphql-query delete-graphql-query)
      (is (empty? (query-sql-statement "SELECT * FROM brewery_review_comment WHERE id_brewery_review_comment = 100 AND cip = 'test1234'"))))))
