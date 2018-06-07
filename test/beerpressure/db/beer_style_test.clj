(ns beerpressure.db.beer-style-test
  (:require [clojure.test :refer :all]
            [beerpressure.handler :refer :all]
            [beerpressure.db.common-test :refer :all]))

(use-fixtures :once db-setup-with-logged-user-fixture)

(deftest test-resolve-beer-styles-orderedby-name
  (testing "beerStylesOrderedByName"
    (let [graphql-query (long-str "{"
                                  "  beerStylesOrderedByName {"
                                  "    id"
                                  "    name"
                                  "  }"
                                  "}")
          expected-response (long-str "{"
                                      "  \"data\": {"
                                      "    \"beerStylesOrderedByName\" : ["
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
      (is (is-data-equal response expected-response)))))
