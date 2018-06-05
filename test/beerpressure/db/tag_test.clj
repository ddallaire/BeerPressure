(ns beerpressure.db.tag-test
  (:require [clojure.test :refer :all]
            [beerpressure.handler :refer :all]
            [beerpressure.db.common-test :refer :all]))

(use-fixtures :once db-setup)

(deftest test-resolve-tags-ordered-by-name
  (testing "tags(skip: 1, first: 2, orderType: ASC) "
    (let [graphql-query (long-str "{"
                                  "  tags(skip: 1, first: 2, orderType: ASC) {"
                                  "    id"
                                  "    name"
                                  "  }"
                                  "}")
          expected-response (long-str "{"
                                      "  \"data\": {"
                                      "    \"tags\": ["
                                      "      {"
                                      "        \"id\": 3,"
                                      "        \"name\": \"Bon choix\""
                                      "      }"
                                      "      {"
                                      "        \"id\": 1,"
                                      "        \"name\": \"Industrielle\""
                                      "      }"
                                      "    ]"
                                      "  }"
                                      "}")
          response (execute-graphql-query graphql-query)]
      (is (is-data-equal response expected-response))))
  (testing "tags(skip: 0, first: 2, orderType: DESC) "
    (let [graphql-query (long-str "{"
                                  "  tags(skip: 0, first: 2, orderType: DESC) {"
                                  "    id"
                                  "    name"
                                  "  }"
                                  "}")
          expected-response (long-str "{"
                                      "  \"data\": {"
                                      "    \"tags\": ["
                                      "      {"
                                      "        \"id\": 4"
                                      "        \"name\": \"Mauvais choix\""
                                      "      }"
                                      "      {"
                                      "        \"id\": 1,"
                                      "        \"name\": \"Industrielle\""
                                      "      }"
                                      "    ]"
                                      "  }"
                                      "}")
          response (execute-graphql-query graphql-query)]
      (is (is-data-equal response expected-response)))))

(deftest test-resolve-tags-ordered-by-brewery-popularity
  (testing "tagsOrderedByBreweryPopularity(skip: 1, first: 3, orderType: ASC) "
    (let [graphql-query (long-str "{"
                                  "  tagsOrderedByBreweryPopularity(skip: 1, first: 3, orderType: ASC) {"
                                  "    id"
                                  "    name"
                                  "  }"
                                  "}")
          expected-response (long-str "{"
                                      "  \"data\": {"
                                      "    \"tagsOrderedByBreweryPopularity\": ["
                                      "      {"
                                      "        \"id\": 3,"
                                      "        \"name\": \"Bon choix\""
                                      "      }"
                                      "      {"
                                      "        \"id\": 1,"
                                      "        \"name\": \"Industrielle\""
                                      "      }"
                                      "      {"
                                      "        \"id\": 2,"
                                      "        \"name\": \"Artisanale\""
                                      "      }"
                                      "    ]"
                                      "  }"
                                      "}")
          response (execute-graphql-query graphql-query)]
      (is (is-data-equal response expected-response))))
  (testing "tagsOrderedByBreweryPopularity(skip: 1, first: 3, orderType: DESC) "
    (let [graphql-query (long-str "{"
                                  "  tagsOrderedByBreweryPopularity(skip: 1, first: 3, orderType: DESC) {"
                                  "    id"
                                  "    name"
                                  "  }"
                                  "}")
          expected-response (long-str "{"
                                      "  \"data\": {"
                                      "    \"tagsOrderedByBreweryPopularity\": ["
                                      "      {"
                                      "        \"id\": 1,"
                                      "        \"name\": \"Industrielle\""
                                      "      }"
                                      "      {"
                                      "        \"id\": 4,"
                                      "        \"name\": \"Mauvais choix\""
                                      "      }"
                                      "      {"
                                      "        \"id\": 3,"
                                      "        \"name\": \"Bon choix\""
                                      "      }"
                                      "    ]"
                                      "  }"
                                      "}")
          response (execute-graphql-query graphql-query)]
      (is (is-data-equal response expected-response)))))

(deftest test-resolve-tags-ordered-by-beer-popularity
  (testing "tagsOrderedByBeerPopularity(skip: 0, first: 2, orderType: ASC) "
    (let [graphql-query (long-str "{"
                                  "  tagsOrderedByBeerPopularity(skip: 0, first: 2, orderType: ASC) {"
                                  "    id"
                                  "    name"
                                  "  }"
                                  "}")
          expected-response (long-str "{"
                                      "  \"data\": {"
                                      "    \"tagsOrderedByBeerPopularity\": ["
                                      "      {"
                                      "        \"id\": 3,"
                                      "        \"name\": \"Bon choix\""
                                      "      }"
                                      "      {"
                                      "        \"id\": 1,"
                                      "        \"name\": \"Industrielle\""
                                      "      }"
                                      "    ]"
                                      "  }"
                                      "}")
          response (execute-graphql-query graphql-query)]
      (is (is-data-equal response expected-response))))
  (testing "tagsOrderedByBeerPopularity(skip: 0, first: 2, orderType: DESC) "
    (let [graphql-query (long-str "{"
                                  "  tagsOrderedByBeerPopularity(skip: 0, first: 2, orderType: DESC) {"
                                  "    id"
                                  "    name"
                                  "  }"
                                  "}")
          expected-response (long-str "{"
                                      "  \"data\": {"
                                      "    \"tagsOrderedByBeerPopularity\": ["
                                      "      {"
                                      "        \"id\": 4,"
                                      "        \"name\": \"Mauvais choix\""
                                      "      }"
                                      "      {"
                                      "        \"id\": 2,"
                                      "        \"name\": \"Artisanale\""
                                      "      }"
                                      "    ]"
                                      "  }"
                                      "}")
          response (execute-graphql-query graphql-query)]
      (is (is-data-equal response expected-response)))))