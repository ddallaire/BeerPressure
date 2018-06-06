(ns beerpressure.db.review.beer-review-test
  (:require [clojure.test :refer :all]
            [beerpressure.handler :refer :all]
            [beerpressure.db.common-test :refer :all]))

(use-fixtures :once db-setup)

(deftest test-resolve-beer-review
  (testing "beerReview(idBeerReview: 6)"
    (let [graphql-query (long-str "{"
                                  "  beerReview(idBeerReview: 6) {"
                                  "    idBeerReview"
                                  "    cip"
                                  "    idBeer"
                                  "    title"
                                  "    content"
                                  "    imagePath"
                                  "    rating"
                                  "    time"
                                  "  }"
                                  "}")
          expected-response (long-str "{"
                                      " \"data\": {"
                                      "   \"beerReview\": {"
                                      "     \"idBeerReview\": 6,"
                                      "     \"cip\": \"pele1704\","
                                      "     \"idBeer\": 4,"
                                      "     \"title\": \"Tu as raison Andy!!\","
                                      "     \"content\": \"Damn... c'est un review, pas un commentaire...\","
                                      "     \"imagePath\": \"https:\\/\\/www.google.com\\/url?sa=i&source=images&cd=&cad=rja&uact=8&ved=2ahUKEwjn1Pr_wLPbAhXKtlkKHX7EBJAQjRx6BAgBEAU&url=http%3A%2F%2Fknowyourmeme.com%2Fphotos%2F56807-fail-epic-fail&psig=AOvVaw3HfgoRpGtV9HRR5-0cLFJ0&ust=1527978321220516\","
                                      "     \"rating\": 4.0,"
                                      "     \"time\": \"2018-06-10 10:00:05.0\""
                                      "   }"
                                      " }"
                                      "}")
          response (execute-graphql-query graphql-query)]
      (is (is-data-equal response expected-response)))))

(deftest test-resolve-beer-reviews
  (testing "beerReviews(first: 3, skip: 4)"
    (let [graphql-query (long-str "{"
                                  "  beerReviews(first: 3, skip: 4) {"
                                  "    idBeerReview"
                                  "    cip"
                                  "    idBeer"
                                  "    title"
                                  "    content"
                                  "    imagePath"
                                  "    rating"
                                  "    time"
                                  "  }"
                                  "}")
          expected-response (long-str "{"
                                      " \"data\": {"
                                      "   \"beerReviews\": ["
                                      "     {"
                                      "       \"idBeerReview\": 5,"
                                      "       \"cip\": \"parp2009\","
                                      "       \"idBeer\": 1,"
                                      "       \"title\": \"Un autre truc pas bon!\","
                                      "       \"content\": \"CE N'EST PAS BON...\","
                                      "       \"imagePath\": null,"
                                      "       \"rating\": 2.0,"
                                      "       \"time\": \"2018-06-10 10:00:04.0\""
                                      "     },"
                                      "     {"
                                      "       \"idBeerReview\": 6,"
                                      "       \"cip\": \"pele1704\","
                                      "       \"idBeer\": 4,"
                                      "       \"title\": \"Tu as raison Andy!!\","
                                      "       \"content\": \"Damn... c'est un review, pas un commentaire...\","
                                      "       \"imagePath\": \"https:\\/\\/www.google.com\\/url?sa=i&source=images&cd=&cad=rja&uact=8&ved=2ahUKEwjn1Pr_wLPbAhXKtlkKHX7EBJAQjRx6BAgBEAU&url=http%3A%2F%2Fknowyourmeme.com%2Fphotos%2F56807-fail-epic-fail&psig=AOvVaw3HfgoRpGtV9HRR5-0cLFJ0&ust=1527978321220516\","
                                      "       \"rating\": 4.0,"
                                      "       \"time\": \"2018-06-10 10:00:05.0\""
                                      "     },"
                                      "     {"
                                      "       \"idBeerReview\": 7,"
                                      "       \"cip\": \"royj1933\","
                                      "       \"idBeer\": 4,"
                                      "       \"title\": \"Époustouflant!!!\","
                                      "       \"content\": \"Je n'ai pas d'autre\","
                                      "       \"imagePath\": null,"
                                      "       \"rating\": 5.0,"
                                      "       \"time\": \"2018-06-10 10:00:06.0\""
                                      "     }"
                                      "   ]"
                                      " }"
                                      "}")
          response (execute-graphql-query graphql-query)]
      (is (is-data-equal response expected-response)))))
