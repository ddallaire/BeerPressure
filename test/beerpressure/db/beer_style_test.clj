(ns beerpressure.db.beer-style-test
  (:require [clojure.test :refer :all]
            [beerpressure.handler :refer :all]
            [beerpressure.db.common-test :refer :all]))

(use-fixtures :once db-setup-with-logged-user-fixture)

(deftest test-resolve-beer-styles
  (testing "beerStyles(skip: 0, first: 4, orderBy: NAME, orderType: ASC)"
    (let [graphql-query (long-str "{"
                                  "  beerStyles(skip: 0, first: 4, orderBy: NAME, orderType: ASC) {"
                                  "    id"
                                  "    name"
                                  "  }"
                                  "}")
          expected-response (long-str "{"
                                      "  \"data\": {"
                                      "    \"beerStyles\" : ["
                                      "      {"
                                      "        \"id\": 2,"
                                      "        \"name\": \"Belgian ale\""
                                      "      },"
                                      "      {"
                                      "        \"id\": 3,"
                                      "        \"name\": \"Imperial stout\""
                                      "      },"
                                      "      {"
                                      "        \"id\": 4,"
                                      "        \"name\": \"Irish ale\""
                                      "      },"
                                      "      {"
                                      "        \"id\": 1,"
                                      "        \"name\": \"Light lager\""
                                      "      }"
                                      "    ]"
                                      "  }"
                                      "}")
          response (execute-graphql-query graphql-query)]
      (is (is-data-equal response expected-response))))
  (testing "beerStyles(skip: 0, first: 4, orderBy: NAME, orderType: DESC)"
    (let [graphql-query (long-str "{"
                                  "  beerStyles(skip: 0, first: 4, orderBy: NAME, orderType: DESC) {"
                                  "    id"
                                  "    name"
                                  "  }"
                                  "}")
          expected-response (long-str "{"
                                      "  \"data\": {"
                                      "    \"beerStyles\" : ["
                                      "      {"
                                      "        \"id\": 1,"
                                      "        \"name\": \"Light lager\""
                                      "      },"
                                      "      {"
                                      "        \"id\": 4,"
                                      "        \"name\": \"Irish ale\""
                                      "      },"
                                      "      {"
                                      "        \"id\": 3,"
                                      "        \"name\": \"Imperial stout\""
                                      "      },"
                                      "      {"
                                      "        \"id\": 2,"
                                      "        \"name\": \"Belgian ale\""
                                      "      }"
                                      "    ]"
                                      "  }"
                                      "}")
          response (execute-graphql-query graphql-query)]
      (is (is-data-equal response expected-response))))
  (testing "beerStyles(skip: 1, first: 1, orderBy: NAME, orderType: ASC)"
    (let [graphql-query (long-str "{"
                                  "  beerStyles(skip: 1, first: 1, orderBy: NAME, orderType: ASC) {"
                                  "    id"
                                  "    name"
                                  "  }"
                                  "}")
          expected-response (long-str "{"
                                      "  \"data\": {"
                                      "    \"beerStyles\" : ["
                                      "      {"
                                      "        \"id\": 3,"
                                      "        \"name\": \"Imperial stout\""
                                      "      }"
                                      "    ]"
                                      "  }"
                                      "}")
          response (execute-graphql-query graphql-query)]
      (is (is-data-equal response expected-response))))
  (testing "beerStyles(skip: 1, first: 1, orderBy: NAME, orderType: DESC)"
    (let [graphql-query (long-str "{"
                                  "  beerStyles(skip: 1, first: 1, orderBy: NAME, orderType: DESC) {"
                                  "    id"
                                  "    name"
                                  "  }"
                                  "}")
          expected-response (long-str "{"
                                      "  \"data\": {"
                                      "    \"beerStyles\" : ["
                                      "      {"
                                      "        \"id\": 4,"
                                      "        \"name\": \"Irish ale\""
                                      "      }"
                                      "    ]"
                                      "  }"
                                      "}")
          response (execute-graphql-query graphql-query)]
      (is (is-data-equal response expected-response)))))
