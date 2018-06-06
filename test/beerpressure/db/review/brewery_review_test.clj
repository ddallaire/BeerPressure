(ns beerpressure.db.review.brewery-review-test
  (:require [clojure.test :refer :all]
            [beerpressure.handler :refer :all]
            [beerpressure.db.common-test :refer :all]))

(use-fixtures :once db-setup-with-logged-user-fixture)

(deftest test-resolve-brewery-review
  (testing "breweryReview(idBreweryReview: 2)"
    (let [graphql-query (long-str "{"
                                  " breweryReview(idBreweryReview: 2) {"
                                  "   idBreweryReview"
                                  "   cip"
                                  "   idBrewery"
                                  "   title"
                                  "   content"
                                  "   imagePath"
                                  "   rating"
                                  "   time"
                                  " }"
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
                                      "     \"time\": \"2018-06-10 10:00:01.0\""
                                      "   }"
                                      " }"
                                      "}")
          response (execute-graphql-query graphql-query)]
      (is (is-data-equal response expected-response)))))

(deftest test-resolve-brewery-reviews
  (testing "breweryReviews(first:3, skip: 0)"
    (let [graphql-query (long-str "{"
                                  " breweryReviews(first:3, skip: 0) {"
                                  "   idBreweryReview"
                                  "   cip"
                                  "   idBrewery"
                                  "   title"
                                  "   content"
                                  "   imagePath"
                                  "   rating"
                                  "   time"
                                  " }"
                                  "}")
          expected-response (long-str "{"
                                      " \"data\": {"
                                      "   \"breweryReviews\": ["
                                      "     {"
                                      "       \"idBreweryReview\": 1,"
                                      "       \"cip\": \"alig2503\","
                                      "       \"idBrewery\": 1,"
                                      "       \"title\": \"Une mauvaise brasserie montréalaise...\","
                                      "       \"content\": \"Sans autre commentaire...\","
                                      "       \"imagePath\": null,"
                                      "       \"rating\": 1.5"
                                      "       \"time\": \"2018-06-10 10:00:00.0\""
                                      "     },"
                                      "     {"
                                      "       \"idBreweryReview\": 2,"
                                      "       \"cip\": \"dald2202\","
                                      "       \"idBrewery\": 2,"
                                      "       \"title\": \"Le brasseur de la 50...\","
                                      "       \"content\": \"Ce n'est pas grand exploit...\","
                                      "       \"imagePath\": \"https:\\/\\/www.google.com\\/url?sa=i&source=images&cd=&cad=rja&uact=8&ved=2ahUKEwiylLDdxrPbAhXJjVkKHdQ0AfUQjRx6BAgBEAU&url=http%3A%2F%2Fwww.labatt.com%2Fbrands%2Fnationalbrands.php%3Flanguage%3Dfr&psig=AOvVaw3AY0gWiexTaG1xbjUkwtVH&ust=1527979870192306\","
                                      "       \"rating\": 1.5"
                                      "       \"time\": \"2018-06-10 10:00:01.0\""
                                      "     },"
                                      "     {"
                                      "       \"idBreweryReview\": 3,"
                                      "       \"cip\": \"lara2318\","
                                      "       \"idBrewery\": 3,"
                                      "       \"title\": \"Ordinaire...\","
                                      "       \"content\": \"Toutes leurs bières ont le même goût...\","
                                      "       \"imagePath\": null,"
                                      "       \"rating\": 0.1"
                                      "       \"time\": \"2018-06-10 10:00:02.0\""
                                      "     }"
                                      "   ]"
                                      " }"
                                      "}")
          response (execute-graphql-query graphql-query)]
      (is (is-data-equal response expected-response)))))
