(ns beerpressure.db.tag-test
  (:require [clojure.test :refer :all]
            [beerpressure.handler :refer :all]
            [beerpressure.db.common-test :refer :all]))

(use-fixtures :once db-setup-with-logged-user-fixture)

(deftest test-resolve-tags
  (testing "tags(skip: 1, first: 2, orderBy: NAME, orderType: ASC)"
    (let [graphql-query (long-str "{"
                                  "  tags(skip: 1, first: 2, orderBy: NAME, orderType: ASC) {"
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
  (testing "tags(skip: 0, first: 2, orderBy: NAME, orderType: DESC)"
    (let [graphql-query (long-str "{"
                                  "  tags(skip: 0, first: 2, orderBy: NAME, orderType: DESC) {"
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
      (is (is-data-equal response expected-response))))
  (testing "tags(skip: 1, first: 3, orderBy: BREWERY_POPULARITY, orderType: ASC)"
    (let [graphql-query (long-str "{"
                                  "  tags(skip: 1, first: 3, orderBy: BREWERY_POPULARITY, orderType: ASC) {"
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
                                      "      {"
                                      "        \"id\": 2,"
                                      "        \"name\": \"Artisanale\""
                                      "      }"
                                      "    ]"
                                      "  }"
                                      "}")
          response (execute-graphql-query graphql-query)]
      (is (is-data-equal response expected-response))))
  (testing "tags(skip: 1, first: 3, orderBy: BREWERY_POPULARITY, orderType: DESC)"
    (let [graphql-query (long-str "{"
                                  "  tags(skip: 1, first: 3, orderBy: BREWERY_POPULARITY, orderType: DESC) {"
                                  "    id"
                                  "    name"
                                  "  }"
                                  "}")
          expected-response (long-str "{"
                                      "  \"data\": {"
                                      "    \"tags\": ["
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
      (is (is-data-equal response expected-response))))
  (testing "tags(skip: 0, first: 2, orderBy: BEER_POPULARITY, orderType: ASC)"
    (let [graphql-query (long-str "{"
                                  "  tags(skip: 0, first: 2, orderBy: BEER_POPULARITY, orderType: ASC) {"
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
  (testing "tags(skip: 0, first: 2, orderBy: BEER_POPULARITY, orderType: DESC)"
    (let [graphql-query (long-str "{"
                                  "  tags(skip: 0, first: 2, orderBy: BEER_POPULARITY, orderType: DESC) {"
                                  "    id"
                                  "    name"
                                  "  }"
                                  "}")
          expected-response (long-str "{"
                                      "  \"data\": {"
                                      "    \"tags\": ["
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
