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
                                  "    cip"
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
                                      "     \"cip\": \"dald2202\","
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
                                  "    cip"
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
                                      "       \"cip\": \"dald2202\","
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
                                      "       \"cip\": \"lara2318\","
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
      (is (is-data-equal response expected-response)))))
