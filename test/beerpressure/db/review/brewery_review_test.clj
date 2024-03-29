(ns beerpressure.db.review.brewery-review-test
  (:require [clojure.test :refer :all]
            [beerpressure.handler :refer :all]
            [beerpressure.db.common-test :refer :all]))

(use-fixtures :once db-setup-with-logged-user-fixture)

(deftest test-resolve-brewery-review
  (testing "breweryReview(idBreweryReview: 2)"
    (let [graphql-query (long-str "{"
                                  "  breweryReview(idBreweryReview: 2) {"
                                  "    idBreweryReview"
                                  "    user {"
                                  "      cip"
                                  "      name"
                                  "      surname"
                                  "    }"
                                  "    idBrewery"
                                  "    title"
                                  "    content"
                                  "    imagePath"
                                  "    rating"
                                  "    time"
                                  "    thumbsups {"
                                  "      user {"
                                  "        cip"
                                  "        name"
                                  "        surname"
                                  "      }"
                                  "    }"
                                  "  }"
                                  "}")
          expected-response (long-str "{"
                                      " \"data\": {"
                                      "   \"breweryReview\": {"
                                      "     \"idBreweryReview\": 2,"
                                      "     \"user\": {"
                                      "       \"cip\": \"dald2202\","
                                      "       \"name\": \"David\""
                                      "       \"surname\": \"Dallaire\""
                                      "     }"
                                      "     \"idBrewery\": 2,"
                                      "     \"title\": \"Le brasseur de la 50...\","
                                      "     \"content\": \"Ce n'est pas grand exploit...\","
                                      "     \"imagePath\": \"https:\\/\\/www.google.com\\/url?sa=i&source=images&cd=&cad=rja&uact=8&ved=2ahUKEwiylLDdxrPbAhXJjVkKHdQ0AfUQjRx6BAgBEAU&url=http%3A%2F%2Fwww.labatt.com%2Fbrands%2Fnationalbrands.php%3Flanguage%3Dfr&psig=AOvVaw3AY0gWiexTaG1xbjUkwtVH&ust=1527979870192306\","
                                      "     \"rating\": 1.5,"
                                      "     \"time\": \"2018-06-10 10:00:01.0\","
                                      "     \"thumbsups\": ["
                                      "       {"
                                      "         \"user\": {"
                                      "           \"cip\": \"royj1933\"",
                                      "           \"name\": \"Jérémie\","
                                      "           \"surname\": \"Roy\""
                                      "         }"
                                      "       }"
                                      "     ]"
                                      "   }"
                                      " }"
                                      "}")
          response (execute-graphql-query graphql-query)]
      (is (is-data-equal response expected-response)))))

(deftest test-resolve-brewery-reviews
  (testing "breweryReviews(skip: 1, first: 2, orderBy: TIME, orderType: ASC)"
    (let [graphql-query (long-str "{"
                                  "  breweryReviews(skip: 1, first: 2, orderBy: TIME, orderType: ASC) {"
                                  "    idBreweryReview"
                                  "    user {"
                                  "      cip"
                                  "      name"
                                  "      surname"
                                  "    }"
                                  "    idBrewery"
                                  "    title"
                                  "    content"
                                  "    imagePath"
                                  "    rating"
                                  "    time"
                                  "    thumbsups {"
                                  "      user {"
                                  "        cip"
                                  "        name"
                                  "        surname"
                                  "      }"
                                  "    }"
                                  "  }"
                                  "}")
          expected-response (long-str "{"
                                      " \"data\": {"
                                      "   \"breweryReviews\": ["
                                      "     {"
                                      "       \"idBreweryReview\": 2,"
                                      "       \"user\": {"
                                      "         \"cip\": \"dald2202\","
                                      "         \"name\": \"David\""
                                      "         \"surname\": \"Dallaire\""
                                      "       }"
                                      "       \"idBrewery\": 2,"
                                      "       \"title\": \"Le brasseur de la 50...\","
                                      "       \"content\": \"Ce n'est pas grand exploit...\","
                                      "       \"imagePath\": \"https:\\/\\/www.google.com\\/url?sa=i&source=images&cd=&cad=rja&uact=8&ved=2ahUKEwiylLDdxrPbAhXJjVkKHdQ0AfUQjRx6BAgBEAU&url=http%3A%2F%2Fwww.labatt.com%2Fbrands%2Fnationalbrands.php%3Flanguage%3Dfr&psig=AOvVaw3AY0gWiexTaG1xbjUkwtVH&ust=1527979870192306\","
                                      "       \"rating\": 1.5"
                                      "       \"time\": \"2018-06-10 10:00:01.0\","
                                      "       \"thumbsups\": ["
                                      "         {"
                                      "           \"user\": {"
                                      "             \"cip\": \"royj1933\"",
                                      "             \"name\": \"Jérémie\","
                                      "             \"surname\": \"Roy\""
                                      "           }"
                                      "         }"
                                      "       ]"
                                      "     },"
                                      "     {"
                                      "       \"idBreweryReview\": 3,"
                                      "       \"user\": {"
                                      "         \"cip\": \"lara2318\","
                                      "         \"name\": \"Andy\""
                                      "         \"surname\": \"Larochelle\""
                                      "       }"
                                      "       \"idBrewery\": 3,"
                                      "       \"title\": \"Ordinaire...\","
                                      "       \"content\": \"Toutes leurs bières ont le même goût...\","
                                      "       \"imagePath\": null,"
                                      "       \"rating\": 0.1"
                                      "       \"time\": \"2018-06-10 10:00:02.0\","
                                      "       \"thumbsups\": ["
                                      "         {"
                                      "           \"user\": {"
                                      "             \"cip\": \"dald2202\"",
                                      "             \"name\": \"David\","
                                      "             \"surname\": \"Dallaire\""
                                      "           }"
                                      "         }"
                                      "       ]"
                                      "     }"
                                      "   ]"
                                      " }"
                                      "}")
          response (execute-graphql-query graphql-query)]
      (is (is-data-equal response expected-response))))
  (testing "breweryReviews(skip: 0, first: 1, orderBy: THUMBSUP_COUNT, orderType: ASC)"
    (let [graphql-query (long-str "{"
                                  "  breweryReviews(skip: 0, first: 1, orderBy: THUMBSUP_COUNT, orderType: ASC) {"
                                  "    idBreweryReview"
                                  "    user {"
                                  "      cip"
                                  "      name"
                                  "      surname"
                                  "    }"
                                  "    idBrewery"
                                  "    title"
                                  "    content"
                                  "    imagePath"
                                  "    rating"
                                  "    time"
                                  "    thumbsups {"
                                  "      user {"
                                  "        cip"
                                  "        name"
                                  "        surname"
                                  "      }"
                                  "    }"
                                  "  }"
                                  "}")
          expected-response (long-str "{"
                                      " \"data\": {"
                                      "   \"breweryReviews\": ["
                                      "     {"
                                      "       \"idBreweryReview\": 2,"
                                      "       \"user\": {"
                                      "         \"cip\": \"dald2202\","
                                      "         \"name\": \"David\""
                                      "         \"surname\": \"Dallaire\""
                                      "       }"
                                      "       \"idBrewery\": 2,"
                                      "       \"title\": \"Le brasseur de la 50...\","
                                      "       \"content\": \"Ce n'est pas grand exploit...\","
                                      "       \"imagePath\": \"https:\\/\\/www.google.com\\/url?sa=i&source=images&cd=&cad=rja&uact=8&ved=2ahUKEwiylLDdxrPbAhXJjVkKHdQ0AfUQjRx6BAgBEAU&url=http%3A%2F%2Fwww.labatt.com%2Fbrands%2Fnationalbrands.php%3Flanguage%3Dfr&psig=AOvVaw3AY0gWiexTaG1xbjUkwtVH&ust=1527979870192306\","
                                      "       \"rating\": 1.5"
                                      "       \"time\": \"2018-06-10 10:00:01.0\","
                                      "       \"thumbsups\": ["
                                      "         {"
                                      "           \"user\": {"
                                      "             \"cip\": \"royj1933\"",
                                      "             \"name\": \"Jérémie\","
                                      "             \"surname\": \"Roy\""
                                      "           }"
                                      "         }"
                                      "       ]"
                                      "     }"
                                      "   ]"
                                      " }"
                                      "}")
          response (execute-graphql-query graphql-query)]
      (is (is-data-equal response expected-response))))
  (testing "breweryReviews(skip: 0, first: 1, orderBy: TIME, orderType: ASC)"
    (let [graphql-query (long-str "{"
                                  "  breweryReviews(skip: 0, first: 1, orderBy: TIME, orderType: ASC) {"
                                  "    idBreweryReview"
                                  "  }"
                                  "}")
          expected-response (long-str "{"
                                      " \"data\": {"
                                      "   \"breweryReviews\": ["
                                      "     {"
                                      "       \"idBreweryReview\": 1"
                                      "     }"
                                      "   ]"
                                      " }"
                                      "}")
          response (execute-graphql-query graphql-query)]
      (is (is-data-equal response expected-response))))
  (testing "breweryReviews(skip: 0, first: 1, orderBy: TIME, orderType: DESC)"
    (let [graphql-query (long-str "{"
                                  "  breweryReviews(skip: 0, first: 1, orderBy: TIME, orderType: DESC) {"
                                  "    idBreweryReview"
                                  "  }"
                                  "}")
          expected-response (long-str "{"
                                      " \"data\": {"
                                      "   \"breweryReviews\": ["
                                      "     {"
                                      "       \"idBreweryReview\": 7"
                                      "     }"
                                      "   ]"
                                      " }"
                                      "}")
          response (execute-graphql-query graphql-query)]
      (is (is-data-equal response expected-response))))
  (testing "breweryReviews(skip: 0, first: 10, orderBy: TIME, orderType: ASC, breweries: [1,3])"
    (let [graphql-query (long-str "{"
                                  "  breweryReviews(skip: 0, first: 10, orderBy: TIME, orderType: ASC, breweries: [1,3]) {"
                                  "    idBreweryReview"
                                  "  }"
                                  "}")
          expected-response (long-str "{"
                                      " \"data\": {"
                                      "   \"breweryReviews\": ["
                                      "     {"
                                      "       \"idBreweryReview\": 1"
                                      "     },"
                                      "     {"
                                      "       \"idBreweryReview\": 3"
                                      "     }"
                                      "   ]"
                                      " }"
                                      "}")
          response (execute-graphql-query graphql-query)]
      (is (is-data-equal response expected-response))))
  (testing "breweryReviews(skip: 0, first: 10, orderBy: TIME, orderType: ASC, cips: [\\\"mahm1904\\\",\\\"pele1704\\\"])"
    (let [graphql-query (long-str "{"
                                  "  breweryReviews(skip: 0, first: 10, orderBy: TIME, orderType: ASC, cips: [\\\"mahm1904\\\", \\\"pele1704\\\"]) {"
                                  "    idBreweryReview"
                                  "  }"
                                  "}")
          expected-response (long-str "{"
                                      " \"data\": {"
                                      "   \"breweryReviews\": ["
                                      "     {"
                                      "       \"idBreweryReview\": 4"
                                      "     },"
                                      "     {"
                                      "       \"idBreweryReview\": 6"
                                      "     }"
                                      "   ]"
                                      " }"
                                      "}")
          response (execute-graphql-query graphql-query)]
      (is (is-data-equal response expected-response))))
  (testing "breweryReviews(skip: 0, first: 10, orderBy: TIME, orderType: ASC, breweries: [3,4], cips: [\\\"mahm1904\\\",\\\"pele1704\\\"])"
    (let [graphql-query (long-str "{"
                                  "  breweryReviews(skip: 0, first: 10, orderBy: TIME, orderType: ASC, breweries: [3,4], cips: [\\\"mahm1904\\\", \\\"pele1704\\\"]) {"
                                  "    idBreweryReview"
                                  "  }"
                                  "}")
          expected-response (long-str "{"
                                      " \"data\": {"
                                      "   \"breweryReviews\": ["
                                      "     {"
                                      "       \"idBreweryReview\": 6"
                                      "     }"
                                      "   ]"
                                      " }"
                                      "}")
          response (execute-graphql-query graphql-query)]
      (is (is-data-equal response expected-response))))
  (testing "breweryReviews(skip: 0, first: 1, orderBy: THUMBSUP_COUNT, orderType: ASC)"
    (let [graphql-query (long-str "{"
                                  "  breweryReviews(skip: 0, first: 1, orderBy: THUMBSUP_COUNT, orderType: ASC) {"
                                  "    idBreweryReview"
                                  "  }"
                                  "}")
          expected-response (long-str "{"
                                      " \"data\": {"
                                      "   \"breweryReviews\": ["
                                      "     {"
                                      "       \"idBreweryReview\": 2"
                                      "     }"
                                      "   ]"
                                      " }"
                                      "}")
          response (execute-graphql-query graphql-query)]
      (is (is-data-equal response expected-response))))
  (testing "breweryReviews(skip: 0, first: 1, orderBy: THUMBSUP_COUNT, orderType: DESC)"
    (let [graphql-query (long-str "{"
                                  "  breweryReviews(skip: 0, first: 1, orderBy: THUMBSUP_COUNT, orderType: DESC) {"
                                  "    idBreweryReview"
                                  "  }"
                                  "}")
          expected-response (long-str "{"
                                      " \"data\": {"
                                      "   \"breweryReviews\": ["
                                      "     {"
                                      "       \"idBreweryReview\": 1"
                                      "     }"
                                      "   ]"
                                      " }"
                                      "}")
          response (execute-graphql-query graphql-query)]
      (is (is-data-equal response expected-response)))))

(deftest test-brewery-review-mutations
  (testing "brewery review mutations"
    (let [insert-graphql-query (long-str "mutation insertBreweryReview {"
                                         "  insertBreweryReview(idBrewery: 1, title: \\\"A title\\\", content: \\\"A content\\\", imagePath: null, rating: 2.5) {"
                                         "    idBreweryReview"
                                         "    user {"
                                         "      cip"
                                         "      name"
                                         "      surname"
                                         "    }"
                                         "    idBrewery"
                                         "    title"
                                         "    content"
                                         "    imagePath"
                                         "    rating"
                                         "    thumbsups {"
                                         "      user {"
                                         "        cip"
                                         "        name"
                                         "        surname"
                                         "      }"
                                         "    }"
                                         "  }"
                                         "}")
          update-graphql-query (long-str "mutation updateBreweryReview {"
                                         "  updateBreweryReview(idBreweryReview: 100, title: \\\"A new title\\\", content: \\\"A new content\\\", imagePath: \\\"bob\\\", rating: 3.5) {"
                                         "    idBreweryReview"
                                         "    user {"
                                         "      cip"
                                         "      name"
                                         "      surname"
                                         "    }"
                                         "    idBrewery"
                                         "    title"
                                         "    content"
                                         "    imagePath"
                                         "    rating"
                                         "    thumbsups {"
                                         "      user {"
                                         "        cip"
                                         "        name"
                                         "        surname"
                                         "      }"
                                         "    }"
                                         "  }"
                                         "}")
          delete-graphql-query (long-str "mutation deleteBreweryReview {"
                                         "  deleteBreweryReview(id: 100)"
                                         "}")
          expected-insert-response (long-str "{"
                                             " \"data\": {"
                                             "   \"insertBreweryReview\": {"
                                             "     \"idBreweryReview\": 100,"
                                             "     \"user\": {"
                                             "       \"cip\": \"test1234\","
                                             "       \"name\": \"testName\""
                                             "       \"surname\": \"testSurname\""
                                             "     }"
                                             "     \"idBrewery\": 1,"
                                             "     \"title\": \"A title\","
                                             "     \"content\": \"A content\","
                                             "     \"imagePath\": null,"
                                             "     \"rating\": 2.5,"
                                             "     \"thumbsups\": []"
                                             "   }"
                                             " }"
                                             "}")
          expected-update-response (long-str "{"
                                             " \"data\": {"
                                             "   \"updateBreweryReview\": {"
                                             "     \"idBreweryReview\": 100,"
                                             "     \"user\": {"
                                             "       \"cip\": \"test1234\","
                                             "       \"name\": \"testName\""
                                             "       \"surname\": \"testSurname\""
                                             "     }"
                                             "     \"idBrewery\": 1,"
                                             "     \"title\": \"A new title\","
                                             "     \"content\": \"A new content\","
                                             "     \"imagePath\": \"bob\","
                                             "     \"rating\": 3.5,"
                                             "     \"thumbsups\": []"
                                             "   }"
                                             " }"
                                             "}")
          verification-query (long-str "SELECT * FROM brewery_review"
                                       "WHERE id_brewery_review = 100 AND cip = 'test1234' AND time <= now()")]
      (is (is-data-equal (execute-graphql-query insert-graphql-query) expected-insert-response))
      (is (not (empty? (query-sql-statement verification-query))))
      (is (is-data-equal (execute-graphql-query update-graphql-query) expected-update-response))
      (execute-graphql-query delete-graphql-query)
      (is (empty? (query-sql-statement verification-query)))))
  (testing "invalid rating brewery review mutations"
    (let [insert-graphql-query (long-str "mutation insertBreweryReview {"
                                         "  insertBreweryReview(idBrewery: 1, title: \\\"A title\\\", content: \\\"A content\\\", imagePath: null, rating: 6) {"
                                         "    idBreweryReview"
                                         "  }"
                                         "}")
          update-graphql-query (long-str "mutation updateBreweryReview {"
                                         "  updateBreweryReview(idBreweryReview: 100, title: \\\"A new title\\\", content: \\\"A new content\\\", imagePath: \\\"bob\\\", rating: 6) {"
                                         "    idBreweryReview"
                                         "  }"
                                         "}")
          expected-insert-response (long-str "{"
                                             " \"data\": {"
                                             "   \"insertBreweryReview\": null"
                                             " }"
                                             "}")
          expected-update-response (long-str "{"
                                             " \"data\": {"
                                             "   \"updateBreweryReview\": null"
                                             " }"
                                             "}")]
      (is (is-data-equal (execute-graphql-query insert-graphql-query) expected-insert-response))
      (is (is-data-equal (execute-graphql-query update-graphql-query) expected-update-response)))))

(deftest test-brewery-review-thumbsup-mutations
  (testing "brewery review thumbs-up mutations"
    (let [insert-graphql-query (long-str "mutation insertBreweryReviewThumbsup {"
                                         "  insertBreweryReviewThumbsup(id: 1)"
                                         "}")
          delete-graphql-query (long-str "mutation deleteBreweryReviewThumbsup {"
                                         "  deleteBreweryReviewThumbsup(id: 1)"
                                         "}")
          verification-query (long-str "SELECT * FROM brewery_review_user_thumbsup"
                                       "WHERE id_brewery_review = 1 AND cip = 'test1234'")]
      (execute-graphql-query insert-graphql-query)
      (is (not (empty? (query-sql-statement verification-query))))
      (execute-graphql-query delete-graphql-query)
      (is (empty? (query-sql-statement verification-query))))))
