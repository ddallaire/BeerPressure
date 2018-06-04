(ns beerpressure.db.tag-test
  (:require [clojure.test :refer :all]
            [beerpressure.handler :refer :all]
            [beerpressure.db.common-test :refer :all]))

(use-fixtures :once db-setup)

(deftest test-resolve-beer
  (testing "tagsOrderedByPopularity(skip: 2, first: 3) "
    (let [graphql-query (long-str "{"
                                  "  tagsOrderedByPopularity(skip: 2, first: 3) {"
                                  "    id"
                                  "    name"
                                  "  }"
                                  "}")
          expected-response (long-str "{"
                                      "  \"data\": {"
                                      "    \"tagsOrderedByPopularity\": ["
                                      "      {"
                                      "        \"id\": 1,"
                                      "        \"name\": \"Industrielle\""
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
