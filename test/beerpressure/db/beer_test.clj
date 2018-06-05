(ns beerpressure.db.beer-test
  (:require [clojure.test :refer :all]
            [beerpressure.handler :refer :all]
            [beerpressure.db.common-test :refer :all]))

(use-fixtures :once db-setup)

(deftest test-resolve-beer
  (testing "beer(id: 1) - 1 brewery"
    (let [graphql-query (long-str "{"
                                  "  beer(id: 1) {"
                                  "    id"
                                  "    name"
                                  "    description"
                                  "    ibu"
                                  "    alcoholPercent"
                                  "    imagePath"
                                  "    breweries {"
                                  "      id"
                                  "      name"
                                  "      description"
                                  "      imagePath"
                                  "      tags {"
                                  "        id"
                                  "        name"
                                  "      }"
                                  "    }"
                                  "    tags {"
                                  "      id"
                                  "      name"
                                  "    }"
                                  "  }"
                                  "}")
          expected-response (long-str "{"
                                      "  \"data\": {"
                                      "    \"beer\": {"
                                      "      \"id\": 1,"
                                      "      \"name\": \"Molson Canadian\","
                                      "      \"description\": \"Une bière avec une feuille d'érable dessus...\","
                                      "      \"ibu\": 15,"
                                      "      \"alcoholPercent\": 5.0,"
                                      "      \"imagePath\": \"https:\\/\\/decrescente.net\\/images\\/suppliers\\/millercoors\\/molson\\/molson-canadian\\/canadian-bottle-lg.png\","
                                      "      \"breweries\": ["
                                      "        {"
                                      "          \"id\": 1,"
                                      "          \"name\": \"MolsonCoors\","
                                      "          \"description\": \"Ils ne font de la marde comme bière.\","
                                      "          \"imagePath\": \"https:\\/\\/images.radio-canada.ca\\/q_auto,w_1250\\/v1\\/ici-info\\/16x9\\/molson-montreal-notre-dame.jpg\","
                                      "          \"tags\": ["
                                      "            {"
                                      "              \"id\": 1,"
                                      "              \"name\": \"Industrielle\""
                                      "            }"
                                      "          ]"
                                      "        }"
                                      "      ],"
                                      "      \"tags\": ["
                                      "        {"
                                      "          \"id\": 1,"
                                      "          \"name\": \"Industrielle\""
                                      "        },"
                                      "        {"
                                      "          \"id\": 4,"
                                      "          \"name\": \"Mauvais choix\""
                                      "        }"
                                      "      ]"
                                      "    }"
                                      "  }"
                                      "}")
          response (execute-graphql-query graphql-query)]
      (is (is-data-equal response expected-response))))
  (testing "beer(id: 5) - 2 breweries"
    (let [graphql-query (long-str "{"
                                  "  beer(id: 5) {"
                                  "    id"
                                  "    breweries {"
                                  "      id"
                                  "    }"
                                  "  }"
                                  "}")
          expected-response (long-str "{"
                                      "  \"data\": {"
                                      "    \"beer\": {"
                                      "      \"id\": 5,"
                                      "      \"breweries\": ["
                                      "        {"
                                      "          \"id\": 5"
                                      "        },"
                                      "        {"
                                      "          \"id\": 6"
                                      "        }"
                                      "      ]"
                                      "    }"
                                      "  }"
                                      "}")
          response (execute-graphql-query graphql-query)]
      (is (is-data-equal response expected-response)))))

(deftest test-resolve-beers
  (testing "beers(skip: 0, first: 1, orderBy: NAME, orderType: ASC, breweries: [], tags: [])"
    (let [graphql-query (long-str "{"
                                  "  beers(skip: 0, first: 1, orderBy: NAME, orderType: ASC, breweries: [], tags: []) {"
                                  "    id"
                                  "    name"
                                  "    description"
                                  "    ibu"
                                  "    alcoholPercent"
                                  "    imagePath"
                                  "    breweries {"
                                  "      id"
                                  "      name"
                                  "      description"
                                  "      imagePath"
                                  "      tags {"
                                  "        id"
                                  "        name"
                                  "      }"
                                  "    }"
                                  "    tags {"
                                  "      id"
                                  "      name"
                                  "    }"
                                  "  }"
                                  "}")
          expected-response (long-str "{"
                                      "  \"data\": {"
                                      "    \"beers\": ["
                                      "      {"
                                      "        \"id\": 2,"
                                      "        \"name\": \"Blue\","
                                      "        \"description\": \"Une bière bleu... Étrange\","
                                      "        \"ibu\": 12,"
                                      "        \"alcoholPercent\": 5.0,"
                                      "        \"imagePath\": \"https:\\/\\/www.google.com\\/url?sa=i&source=images&cd=&cad=rja&uact=8&ved=2ahUKEwir8pfW-7DbAhWr5oMKHX3oDtMQjRx6BAgBEAU&url=https%3A%2F%2Fwww.liquormarts.ca%2Fproduct%2Flabatt-blue%2F24-x-355-ml&psig=AOvVaw1n1N32iTLqUZu3QGlvQy7F&ust=1527891003844616\","
                                      "        \"breweries\": ["
                                      "          {"
                                      "            \"id\": 2,"
                                      "            \"name\": \"Labatt\","
                                      "            \"description\": \"Une copie de MolsonCoors en pire...\","
                                      "            \"imagePath\": \"https:\\/\\/upload.wikimedia.org\\/wikipedia\\/fr\\/4\\/40\\/Labatt.png\","
                                      "            \"tags\": ["
                                      "              {"
                                      "                \"id\": 1,"
                                      "                \"name\": \"Industrielle\""
                                      "              }"
                                      "            ]"
                                      "          }"
                                      "        ],"
                                      "        \"tags\": ["
                                      "          {"
                                      "            \"id\": 1,"
                                      "            \"name\": \"Industrielle\""
                                      "          },"
                                      "          {"
                                      "            \"id\": 4,"
                                      "            \"name\": \"Mauvais choix\""
                                      "          }"
                                      "        ]"
                                      "      }"
                                      "    ]"
                                      "  }"
                                      "}")
          response (execute-graphql-query graphql-query)]
      (is (is-data-equal response expected-response))))
  (testing "beers(skip: 0, first: 1, orderBy: NAME, orderType: DESC, breweries: [], tags: [])"
    (let [graphql-query (long-str "{"
                                  "  beers(skip: 0, first: 1, orderBy: NAME, orderType: DESC, breweries: [], tags: []) {"
                                  "    id"
                                  "  }"
                                  "}")
          expected-response (long-str "{"
                                      "  \"data\": {"
                                      "    \"beers\": ["
                                      "      {"
                                      "        \"id\": 4"
                                      "      }"
                                      "    ]"
                                      "  }"
                                      "}")
          response (execute-graphql-query graphql-query)]
      (is (is-data-equal response expected-response))))
  (testing "beers(skip: 0, first: 1, orderBy: NAME, orderType: ASC, breweries: [1], tags: [])"
    (let [graphql-query (long-str "{"
                                  "  beers(skip: 0, first: 1, orderBy: NAME, orderType: ASC, breweries: [1], tags: []) {"
                                  "    id"
                                  "  }"
                                  "}")
          expected-response (long-str "{"
                                      "  \"data\": {"
                                      "    \"beers\": ["
                                      "      {"
                                      "        \"id\": 1"
                                      "      }"
                                      "    ]"
                                      "  }"
                                      "}")
          response (execute-graphql-query graphql-query)]
      (is (is-data-equal response expected-response))))
  (testing "beers(skip: 0, first: 1, orderBy: NAME, orderType: DESC, breweries: [2], tags: [])"
    (let [graphql-query (long-str "{"
                                  "  beers(skip: 0, first: 1, orderBy: NAME, orderType: DESC, breweries: [2], tags: []) {"
                                  "    id"
                                  "  }"
                                  "}")
          expected-response (long-str "{"
                                      "  \"data\": {"
                                      "    \"beers\": ["
                                      "      {"
                                      "        \"id\": 2"
                                      "      }"
                                      "    ]"
                                      "  }"
                                      "}")
          response (execute-graphql-query graphql-query)]
      (is (is-data-equal response expected-response))))
  (testing "beers(skip: 0, first: 2, orderBy: NAME, orderType: ASC, breweries: [], tags: [1])"
    (let [graphql-query (long-str "{"
                                  "  beers(skip: 0, first: 2, orderBy: NAME, orderType: ASC, breweries: [], tags: [1]) {"
                                  "    id"
                                  "  }"
                                  "}")
          expected-response (long-str "{"
                                      "  \"data\": {"
                                      "    \"beers\": ["
                                      "      {"
                                      "        \"id\": 2"
                                      "      },"
                                      "      {"
                                      "        \"id\": 1"
                                      "      }"
                                      "    ]"
                                      "  }"
                                      "}")
          response (execute-graphql-query graphql-query)]
      (is (is-data-equal response expected-response))))
  (testing "beers(skip: 0, first: 2, orderBy: NAME, orderType: DESC, breweries: [], tags: [2])"
    (let [graphql-query (long-str "{"
                                  "  beers(skip: 0, first: 2, orderBy: NAME, orderType: DESC, breweries: [], tags: [2]) {"
                                  "    id"
                                  "  }"
                                  "}")
          expected-response (long-str "{"
                                      "  \"data\": {"
                                      "    \"beers\": ["
                                      "      {"
                                      "        \"id\": 4"
                                      "      },"
                                      "      {"
                                      "        \"id\": 5"
                                      "      }"
                                      "    ]"
                                      "  }"
                                      "}")
          response (execute-graphql-query graphql-query)]
      (is (is-data-equal response expected-response))))
  (testing "beers(skip: 0, first: 2, orderBy: NAME, orderType: ASC, breweries: [1,3], tags: [2])"
    (let [graphql-query (long-str "{"
                                  "  beers(skip: 0, first: 2, orderBy: NAME, orderType: ASC, breweries: [1,3], tags: [2]) {"
                                  "    id"
                                  "  }"
                                  "}")
          expected-response (long-str "{"
                                      "  \"data\": {"
                                      "    \"beers\": ["
                                      "      {"
                                      "        \"id\": 3"
                                      "      }"
                                      "    ]"
                                      "  }"
                                      "}")
          response (execute-graphql-query graphql-query)]
      (is (is-data-equal response expected-response))))
  (testing "beers(skip: 0, first: 2, orderBy: NAME, orderType: DESC, breweries: [1,3,4], tags: [2])"
    (let [graphql-query (long-str "{"
                                  "  beers(skip: 0, first: 2, orderBy: NAME, orderType: DESC, breweries: [1,3,4], tags: [2]) {"
                                  "    id"
                                  "  }"
                                  "}")
          expected-response (long-str "{"
                                      "  \"data\": {"
                                      "    \"beers\": ["
                                      "      {"
                                      "        \"id\": 4"
                                      "      },"
                                      "      {"
                                      "        \"id\": 3"
                                      "      }"
                                      "    ]"
                                      "  }"
                                      "}")
          response (execute-graphql-query graphql-query)]
      (is (is-data-equal response expected-response))))
  (testing "beers(skip: 0, first: 2, orderBy: RATING, orderType: ASC, breweries: [], tags: [])"
    (let [graphql-query (long-str "{"
                                  "  beers(skip: 0, first: 2, orderBy: RATING, orderType: ASC, breweries: [], tags: []) {"
                                  "    id"
                                  "  }"
                                  "}")
          expected-response (long-str "{"
                                      "  \"data\": {"
                                      "    \"beers\": ["
                                      "      {"
                                      "        \"id\": 2"
                                      "      },"
                                      "      {"
                                      "        \"id\": 1"
                                      "      }"
                                      "    ]"
                                      "  }"
                                      "}")
          response (execute-graphql-query graphql-query)]
      (is (is-data-equal response expected-response))))
  (testing "beers(skip: 0, first: 2, orderBy: RATING, orderType: DESC, breweries: [], tags: [])"
    (let [graphql-query (long-str "{"
                                  "  beers(skip: 0, first: 2, orderBy: RATING, orderType: DESC, breweries: [], tags: []) {"
                                  "    id"
                                  "  }"
                                  "}")
          expected-response (long-str "{"
                                      "  \"data\": {"
                                      "    \"beers\": ["
                                      "      {"
                                      "        \"id\": 4"
                                      "      },"
                                      "      {"
                                      "        \"id\": 5"
                                      "      }"
                                      "    ]"
                                      "  }"
                                      "}")
          response (execute-graphql-query graphql-query)]
      (is (is-data-equal response expected-response))))
  (testing "beers(skip: 0, first: 2, orderBy: RATING, orderType: ASC, breweries: [1,2], tags: [])"
    (let [graphql-query (long-str "{"
                                  "  beers(skip: 0, first: 2, orderBy: RATING, orderType: ASC, breweries: [1,2], tags: []) {"
                                  "    id"
                                  "  }"
                                  "}")
          expected-response (long-str "{"
                                      "  \"data\": {"
                                      "    \"beers\": ["
                                      "      {"
                                      "        \"id\": 2"
                                      "      },"
                                      "      {"
                                      "        \"id\": 1"
                                      "      }"
                                      "    ]"
                                      "  }"
                                      "}")
          response (execute-graphql-query graphql-query)]
      (is (is-data-equal response expected-response))))
  (testing "beers(skip: 0, first: 2, orderBy: RATING, orderType: DESC, breweries: [2,3], tags: [])"
    (let [graphql-query (long-str "{"
                                  "  beers(skip: 0, first: 2, orderBy: RATING, orderType: DESC, breweries: [2,3], tags: []) {"
                                  "    id"
                                  "  }"
                                  "}")
          expected-response (long-str "{"
                                      "  \"data\": {"
                                      "    \"beers\": ["
                                      "      {"
                                      "        \"id\": 3"
                                      "      },"
                                      "      {"
                                      "        \"id\": 2"
                                      "      }"
                                      "    ]"
                                      "  }"
                                      "}")
          response (execute-graphql-query graphql-query)]
      (is (is-data-equal response expected-response))))
  (testing "beers(skip: 0, first: 2, orderBy: RATING, orderType: ASC, breweries: [], tags: [1])"
    (let [graphql-query (long-str "{"
                                  "  beers(skip: 0, first: 2, orderBy: RATING, orderType: ASC, breweries: [], tags: [1]) {"
                                  "    id"
                                  "  }"
                                  "}")
          expected-response (long-str "{"
                                      "  \"data\": {"
                                      "    \"beers\": ["
                                      "      {"
                                      "        \"id\": 2"
                                      "      },"
                                      "      {"
                                      "        \"id\": 1"
                                      "      }"
                                      "    ]"
                                      "  }"
                                      "}")
          response (execute-graphql-query graphql-query)]
      (is (is-data-equal response expected-response))))
  (testing "beers(skip: 0, first: 2, orderBy: RATING, orderType: DESC, breweries: [], tags: [2])"
    (let [graphql-query (long-str "{"
                                  "  beers(skip: 0, first: 2, orderBy: RATING, orderType: DESC, breweries: [], tags: [2]) {"
                                  "    id"
                                  "  }"
                                  "}")
          expected-response (long-str "{"
                                      "  \"data\": {"
                                      "    \"beers\": ["
                                      "      {"
                                      "        \"id\": 4"
                                      "      },"
                                      "      {"
                                      "        \"id\": 5"
                                      "      }"
                                      "    ]"
                                      "  }"
                                      "}")
          response (execute-graphql-query graphql-query)]
      (is (is-data-equal response expected-response))))
  (testing "beers(skip: 0, first: 2, orderBy: RATING, orderType: ASC, breweries: [1,3,5], tags: [2,3])"
    (let [graphql-query (long-str "{"
                                  "  beers(skip: 0, first: 2, orderBy: RATING, orderType: ASC, breweries: [1,3,5], tags: [2,3]) {"
                                  "    id"
                                  "  }"
                                  "}")
          expected-response (long-str "{"
                                      "  \"data\": {"
                                      "    \"beers\": ["
                                      "      {"
                                      "        \"id\": 3"
                                      "      },"
                                      "      {"
                                      "        \"id\": 5"
                                      "      }"
                                      "    ]"
                                      "  }"
                                      "}")
          response (execute-graphql-query graphql-query)]
      (is (is-data-equal response expected-response))))
  (testing "beers(skip: 0, first: 2, orderBy: RATING, orderType: DESC, breweries: [1,3,4], tags: [1,2])"
    (let [graphql-query (long-str "{"
                                  "  beers(skip: 0, first: 2, orderBy: RATING, orderType: DESC, breweries: [1,3,4], tags: [1,2]) {"
                                  "    id"
                                  "  }"
                                  "}")
          expected-response (long-str "{"
                                      "  \"data\": {"
                                      "    \"beers\": ["
                                      "      {"
                                      "        \"id\": 4"
                                      "      },"
                                      "      {"
                                      "        \"id\": 3"
                                      "      }"
                                      "    ]"
                                      "  }"
                                      "}")
          response (execute-graphql-query graphql-query)]
      (is (is-data-equal response expected-response)))))
