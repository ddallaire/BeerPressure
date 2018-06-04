(ns beerpressure.db.brewery-test
  (:require [clojure.test :refer :all]
            [beerpressure.handler :refer :all]
            [beerpressure.db.common-test :refer :all]))

(use-fixtures :once db-setup)

(deftest test-resolve-brewery
  (testing "brewery(id: 2)"
    (let [graphql-query (long-str "{"
                                  "  brewery(id: 2) {"
                                  "    id"
                                  "    name"
                                  "    description"
                                  "    imagePath"
                                  "  }"
                                  "}")
          expected-response (long-str "{"
                                      "  \"data\": {"
                                      "    \"brewery\": {"
                                      "      \"id\": 2,"
                                      "      \"name\": \"Labatt\","
                                      "      \"description\": \"Une copie de MolsonCoors en pire...\","
                                      "      \"imagePath\": \"https:\\/\\/upload.wikimedia.org\\/wikipedia\\/fr\\/4\\/40\\/Labatt.png\""
                                      "    }"
                                      "  }"
                                      "}")
          response (execute-graphql-query graphql-query)]
      (is (is-data-equal response expected-response)))))

(deftest test-resolve-breweries
  (testing "breweries(skip: 1, first: 2)"
    (let [graphql-query (long-str "{"
                                  "  breweries(skip: 1, first: 2) {"
                                  "    id"
                                  "    name"
                                  "    description"
                                  "    imagePath"
                                  "  }"
                                  "}")
          expected-response (long-str "{"
                                      "  \"data\": {"
                                      "    \"breweries\": ["
                                      "      {"
                                      "        \"id\": 2,"
                                      "        \"name\": \"Labatt\","
                                      "        \"description\": \"Une copie de MolsonCoors en pire...\","
                                      "        \"imagePath\": \"https:\\/\\/upload.wikimedia.org\\/wikipedia\\/fr\\/4\\/40\\/Labatt.png\""
                                      "      },"
                                      "      {"
                                      "        \"id\": 3,"
                                      "        \"name\": \"Unibroue\","
                                      "        \"description\": \"La bière préférée des gars en génie qui ne connaissent pas ce qui est de la vrai bière.\","
                                      "        \"imagePath\": \"https:\\/\\/encrypted-tbn0.gstatic.com\\/images?q=tbn:ANd9GcTeNRjlWTRpm31yyz_mHoGAE9UdjNaAnt3WVCd9E_FsBnlNnLcGcw\""
                                      "      },"
                                      "    ]"
                                      "  }"
                                      "}")
          response (execute-graphql-query graphql-query)]
      (is (is-data-equal response expected-response)))))
