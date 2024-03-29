(ns beerpressure.db.brewery-test
  (:require [clojure.test :refer :all]
            [beerpressure.handler :refer :all]
            [beerpressure.db.common-test :refer :all]))

(use-fixtures :once db-setup-with-logged-user-fixture)

(deftest test-resolve-brewery
  (testing "brewery(id: 2)"
    (let [graphql-query (long-str "{"
                                  "  brewery(id: 2) {"
                                  "    id"
                                  "    name"
                                  "    description"
                                  "    imagePath"
                                  "    tags {"
                                  "      id"
                                  "      name"
                                  "    }"
                                  "    rating"
                                  "  }"
                                  "}")
          expected-response (long-str "{"
                                      "  \"data\": {"
                                      "    \"brewery\": {"
                                      "      \"id\": 2,"
                                      "      \"name\": \"Labatt\","
                                      "      \"description\": \"Une copie de MolsonCoors en pire...\","
                                      "      \"imagePath\": \"https:\\/\\/upload.wikimedia.org\\/wikipedia\\/fr\\/4\\/40\\/Labatt.png\""
                                      "      \"tags\": ["
                                      "        {"
                                      "          \"id\": 1,"
                                      "          \"name\": \"Industrielle\""
                                      "        }"
                                      "      ],"
                                      "      \"rating\": 1.1666666666666667"
                                      "    }"
                                      "  }"
                                      "}")
          response (execute-graphql-query graphql-query)]
      (is (is-data-equal response expected-response)))))

(deftest test-resolve-breweries
  (testing "breweries(skip: 1, first: 2, orderBy: NAME, orderType: ASC, tags: [])"
    (let [graphql-query (long-str "{"
                                  "  breweries(skip: 1, first: 2, orderBy: NAME, orderType: ASC, tags: []) {"
                                  "    id"
                                  "    name"
                                  "    description"
                                  "    imagePath"
                                  "    tags {"
                                  "      id"
                                  "      name"
                                  "    }"
                                  "    rating"
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
                                      "        \"tags\": ["
                                      "          {"
                                      "            \"id\": 1,"
                                      "            \"name\": \"Industrielle\""
                                      "          }"
                                      "        ],"
                                      "        \"rating\": 1.1666666666666667"
                                      "      },"
                                      "      {"
                                      "        \"id\": 1,"
                                      "        \"name\": \"MolsonCoors\","
                                      "        \"description\": \"Ils ne font de la marde comme bière.\","
                                      "        \"imagePath\": \"https:\\/\\/images.radio-canada.ca\\/q_auto,w_1250\\/v1\\/ici-info\\/16x9\\/molson-montreal-notre-dame.jpg\","
                                      "        \"tags\": ["
                                      "          {"
                                      "            \"id\": 1,"
                                      "            \"name\": \"Industrielle\""
                                      "          }"
                                      "        ],"
                                      "        \"rating\": 1.5"
                                      "      }"
                                      "    ]"
                                      "  }"
                                      "}")
          response (execute-graphql-query graphql-query)]
      (is (is-data-equal response expected-response))))
  (testing "breweries(skip: 1, first: 2, orderBy: NAME, orderType: DESC)"
    (let [graphql-query (long-str "{"
                                  "  breweries(skip: 1, first: 2, orderBy: NAME, orderType: DESC) {"
                                  "    id"
                                  "    name"
                                  "    description"
                                  "    imagePath"
                                  "    tags {"
                                  "      id"
                                  "      name"
                                  "    }"
                                  "    rating"
                                  "  }"
                                  "}")
          expected-response (long-str "{"
                                      "  \"data\": {"
                                      "    \"breweries\": ["
                                      "      {"
                                      "        \"id\": 5,"
                                      "        \"name\": \"Siboire\","
                                      "        \"description\": \"Une brasserie de Sherbrooke.\","
                                      "        \"imagePath\": \"http:\\/\\/www.ambq.ca\\/mod\\/file\\/MembreFile\\/c45147dee729311ef5b5c3003946c48f.png\","
                                      "        \"tags\": ["
                                      "          {"
                                      "            \"id\": 2,"
                                      "            \"name\": \"Artisanale\""
                                      "          }"
                                      "        ],"
                                      "        \"rating\": 4.5"
                                      "      },"
                                      "      {"
                                      "        \"id\": 6,"
                                      "        \"name\": \"SherBroue\","
                                      "        \"description\": \"Génie Sherbrooke!!!\","
                                      "        \"imagePath\": \"http:\\/\\/sherbroue.ca\\/accueil\\/wp-content\\/uploads\\/2012\\/02\\/Couverture-Facebook-1.jpg\","
                                      "        \"tags\": ["
                                      "          {"
                                      "            \"id\": 2,"
                                      "            \"name\": \"Artisanale\""
                                      "          }"
                                      "        ],"
                                      "        \"rating\": 4.0"
                                      "      }"
                                      "    ]"
                                      "  }"
                                      "}")
          response (execute-graphql-query graphql-query)]
      (is (is-data-equal response expected-response))))
  (testing "breweries(skip: 0, first: 10, orderBy: NAME, orderType: ASC, tags: [1])"
    (let [graphql-query (long-str "{"
                                  "  breweries(skip: 0, first: 10, orderBy: NAME, orderType: ASC, tags: [1]) {"
                                  "    id"
                                  "    name"
                                  "    description"
                                  "    imagePath"
                                  "    tags {"
                                  "      id"
                                  "      name"
                                  "    }"
                                  "    rating"
                                  "  }"
                                  "}")
          expected-response (long-str "{"
                                      "  \"data\": {"
                                      "    \"breweries\": ["
                                      "      {"
                                      "        \"id\": 2,"
                                      "        \"name\": \"Labatt\","
                                      "        \"description\": \"Une copie de MolsonCoors en pire...\","
                                      "        \"imagePath\": \"https:\\/\\/upload.wikimedia.org\\/wikipedia\\/fr\\/4\\/40\\/Labatt.png\","
                                      "        \"tags\": ["
                                      "          {"
                                      "            \"id\": 1,"
                                      "            \"name\": \"Industrielle\""
                                      "          }"
                                      "        ],"
                                      "        \"rating\": 1.1666666666666667"
                                      "      },"
                                      "      {"
                                      "        \"id\": 1,"
                                      "        \"name\": \"MolsonCoors\","
                                      "        \"description\": \"Ils ne font de la marde comme bière.\","
                                      "        \"imagePath\": \"https:\\/\\/images.radio-canada.ca\\/q_auto,w_1250\\/v1\\/ici-info\\/16x9\\/molson-montreal-notre-dame.jpg\","
                                      "        \"tags\": ["
                                      "          {"
                                      "            \"id\": 1,"
                                      "            \"name\": \"Industrielle\""
                                      "          }"
                                      "        ],"
                                      "        \"rating\": 1.5"
                                      "      }"
                                      "    ]"
                                      "  }"
                                      "}")
          response (execute-graphql-query graphql-query)]
      (is (is-data-equal response expected-response))))
  (testing "breweries(skip: 0, first: 10, orderBy: NAME, orderType: DESC, tags: [1])"
    (let [graphql-query (long-str "{"
                                  "  breweries(skip: 0, first: 10, orderBy: NAME, orderType: DESC, tags: [1]) {"
                                  "    id"
                                  "    name"
                                  "    description"
                                  "    imagePath"
                                  "    tags {"
                                  "      id"
                                  "      name"
                                  "    }"
                                  "    rating"
                                  "  }"
                                  "}")
          expected-response (long-str "{"
                                      "  \"data\": {"
                                      "    \"breweries\": ["
                                      "      {"
                                      "        \"id\": 1,"
                                      "        \"name\": \"MolsonCoors\","
                                      "        \"description\": \"Ils ne font de la marde comme bière.\","
                                      "        \"imagePath\": \"https:\\/\\/images.radio-canada.ca\\/q_auto,w_1250\\/v1\\/ici-info\\/16x9\\/molson-montreal-notre-dame.jpg\","
                                      "        \"tags\": ["
                                      "          {"
                                      "            \"id\": 1,"
                                      "            \"name\": \"Industrielle\""
                                      "          }"
                                      "        ],"
                                      "        \"rating\": 1.5"
                                      "      },"
                                      "      {"
                                      "        \"id\": 2,"
                                      "        \"name\": \"Labatt\","
                                      "        \"description\": \"Une copie de MolsonCoors en pire...\","
                                      "        \"imagePath\": \"https:\\/\\/upload.wikimedia.org\\/wikipedia\\/fr\\/4\\/40\\/Labatt.png\","
                                      "        \"tags\": ["
                                      "          {"
                                      "            \"id\": 1,"
                                      "            \"name\": \"Industrielle\""
                                      "          }"
                                      "        ],"
                                      "        \"rating\": 1.1666666666666667"
                                      "      }"
                                      "    ]"
                                      "  }"
                                      "}")
          response (execute-graphql-query graphql-query)]
      (is (is-data-equal response expected-response))))
  (testing "breweries(skip: 0, first: 2, orderBy: RATING, orderType: ASC)"
    (let [graphql-query (long-str "{"
                                  "  breweries(skip: 0, first: 2, orderBy: RATING, orderType: ASC) {"
                                  "    id"
                                  "    name"
                                  "    description"
                                  "    imagePath"
                                  "    tags {"
                                  "      id"
                                  "      name"
                                  "    }"
                                  "    rating"
                                  "  }"
                                  "}")
          expected-response (long-str "{"
                                      "  \"data\": {"
                                      "    \"breweries\": ["
                                      "      {"
                                      "        \"id\": 2,"
                                      "        \"name\": \"Labatt\","
                                      "        \"description\": \"Une copie de MolsonCoors en pire...\","
                                      "        \"imagePath\": \"https:\\/\\/upload.wikimedia.org\\/wikipedia\\/fr\\/4\\/40\\/Labatt.png\","
                                      "        \"tags\": ["
                                      "          {"
                                      "            \"id\": 1,"
                                      "            \"name\": \"Industrielle\""
                                      "          }"
                                      "        ],"
                                      "        \"rating\": 1.1666666666666667"
                                      "      },"
                                      "      {"
                                      "        \"id\": 3,"
                                      "        \"name\": \"Unibroue\","
                                      "        \"description\": \"La bière préférée des gars en génie qui ne connaissent pas ce qui est de la vrai bière.\","
                                      "        \"imagePath\": \"https:\\/\\/encrypted-tbn0.gstatic.com\\/images?q=tbn:ANd9GcTeNRjlWTRpm31yyz_mHoGAE9UdjNaAnt3WVCd9E_FsBnlNnLcGcw\","
                                      "        \"tags\": ["
                                      "          {"
                                      "            \"id\": 2,"
                                      "            \"name\": \"Artisanale\""
                                      "          }"
                                      "        ],"
                                      "        \"rating\": 1.275"
                                      "      }"
                                      "    ]"
                                      "  }"
                                      "}")
          response (execute-graphql-query graphql-query)]
      (is (is-data-equal response expected-response))))
  (testing "breweries(skip: 0, first: 2, orderBy: RATING, orderType: DESC)"
    (let [graphql-query (long-str "{"
                                  "  breweries(skip: 0, first: 2, orderBy: RATING, orderType: DESC) {"
                                  "    id"
                                  "    name"
                                  "    description"
                                  "    imagePath"
                                  "    tags {"
                                  "      id"
                                  "      name"
                                  "    }"
                                  "    rating"
                                  "  }"
                                  "}")
          expected-response (long-str "{"
                                      "  \"data\": {"
                                      "    \"breweries\": ["
                                      "      {"
                                      "        \"id\": 4,"
                                      "        \"name\": \"Dieu du Ciel!\","
                                      "        \"description\": \"Enfin!! Une brasserie qui fait de la bonne bière.\","
                                      "        \"imagePath\": \"http:\\/\\/theatregillesvigneault.com\\/wp-content\\/uploads\\/2017\\/03\\/DIEU-DU-CIEL.jpg\","
                                      "        \"tags\": ["
                                      "          {"
                                      "            \"id\": 2,"
                                      "            \"name\": \"Artisanale\""
                                      "          },"
                                      "          {"
                                      "            \"id\": 3,"
                                      "            \"name\": \"Bon choix\""
                                      "          }"
                                      "        ],"
                                      "        \"rating\": 4.5"
                                      "      },"
                                      "      {"
                                      "        \"id\": 5,"
                                      "        \"name\": \"Siboire\","
                                      "        \"description\": \"Une brasserie de Sherbrooke.\","
                                      "        \"imagePath\": \"http:\\/\\/www.ambq.ca\\/mod\\/file\\/MembreFile\\/c45147dee729311ef5b5c3003946c48f.png\","
                                      "        \"tags\": ["
                                      "          {"
                                      "            \"id\": 2,"
                                      "            \"name\": \"Artisanale\""
                                      "          }"
                                      "        ],"
                                      "        \"rating\": 4.5"
                                      "      }"
                                      "    ]"
                                      "  }"
                                      "}")
          response (execute-graphql-query graphql-query)]
      (is (is-data-equal response expected-response))))
  (testing "breweries(skip: 1, first: 1, orderBy: RATING, orderType: ASC, tags: [1])"
    (let [graphql-query (long-str "{"
                                  "  breweries(skip: 1, first: 1, orderBy: RATING, orderType: ASC, tags: [1]) {"
                                  "    id"
                                  "    name"
                                  "    description"
                                  "    imagePath"
                                  "    tags {"
                                  "      id"
                                  "      name"
                                  "    }"
                                  "    rating"
                                  "  }"
                                  "}")
          expected-response (long-str "{"
                                      "  \"data\": {"
                                      "    \"breweries\": ["
                                      "      {"
                                      "        \"id\": 1,"
                                      "        \"name\": \"MolsonCoors\","
                                      "        \"description\": \"Ils ne font de la marde comme bière.\","
                                      "        \"imagePath\": \"https:\\/\\/images.radio-canada.ca\\/q_auto,w_1250\\/v1\\/ici-info\\/16x9\\/molson-montreal-notre-dame.jpg\","
                                      "        \"tags\": ["
                                      "          {"
                                      "            \"id\": 1,"
                                      "            \"name\": \"Industrielle\""
                                      "          }"
                                      "        ],"
                                      "        \"rating\": 1.5"
                                      "      }"
                                      "    ]"
                                      "  }"
                                      "}")
          response (execute-graphql-query graphql-query)]
      (is (is-data-equal response expected-response))))
  (testing "breweries(skip: 1, first: 1, orderBy: RATING, orderType: DESC, tags: [1])"
    (let [graphql-query (long-str "{"
                                  "  breweries(skip: 1, first: 1, orderBy: RATING, orderType: DESC, tags: [1]) {"
                                  "    id"
                                  "    name"
                                  "    description"
                                  "    imagePath"
                                  "    tags {"
                                  "      id"
                                  "      name"
                                  "    }"
                                  "    rating"
                                  "  }"
                                  "}")
          expected-response (long-str "{"
                                      "  \"data\": {"
                                      "    \"breweries\": ["
                                      "      {"
                                      "        \"id\": 2,"
                                      "        \"name\": \"Labatt\","
                                      "        \"description\": \"Une copie de MolsonCoors en pire...\","
                                      "        \"imagePath\": \"https:\\/\\/upload.wikimedia.org\\/wikipedia\\/fr\\/4\\/40\\/Labatt.png\","
                                      "        \"tags\": ["
                                      "          {"
                                      "            \"id\": 1,"
                                      "            \"name\": \"Industrielle\""
                                      "          }"
                                      "        ],"
                                      "        \"rating\": 1.1666666666666667"
                                      "      }"
                                      "    ]"
                                      "  }"
                                      "}")
          response (execute-graphql-query graphql-query)]
      (is (is-data-equal response expected-response)))))

(deftest test-brewery-mutations
  (testing "brewery mutations"
    (let [insert-graphql-query (long-str "mutation insertBrewery {"
                                         "  insertBrewery(name: \\\"A name\\\", description: \\\"A description\\\", imagePath: \\\"A path\\\", tags: [\\\"Industrielle\\\", \\\"A new one\\\"]) {"
                                         "    id"
                                         "    name"
                                         "    description"
                                         "    imagePath"
                                         "    tags {"
                                         "      id"
                                         "      name"
                                         "    }"
                                         "    rating"
                                         "  }"
                                         "}")
          update-graphql-query (long-str "mutation updateBrewery {"
                                         "  updateBrewery(id: 100, name: \\\"A new name\\\", description: \\\"A new description\\\", imagePath: \\\"A new path\\\", tags: [\\\"Industrielle\\\", \\\"A new one 2\\\"]) {"
                                         "    id"
                                         "    name"
                                         "    description"
                                         "    imagePath"
                                         "    tags {"
                                         "      id"
                                         "      name"
                                         "    }"
                                         "    rating"
                                         "  }"
                                         "}")
          delete-graphql-query (long-str "mutation deleteBrewery {"
                                         "  deleteBrewery(id: 100)"
                                         "}")
          expected-insert-response (long-str "{"
                                             " \"data\": {"
                                             "   \"insertBrewery\": {"
                                             "     \"id\": 100,"
                                             "     \"name\": \"A name\","
                                             "     \"description\": \"A description\","
                                             "     \"imagePath\": \"A path\","
                                             "     \"tags\": ["
                                             "       {"
                                             "         \"id\": 1,"
                                             "         \"name\": \"Industrielle\""
                                             "       },"
                                             "       {"
                                             "         \"id\": 100,"
                                             "         \"name\": \"A new one\""
                                             "       }"
                                             "     ],"
                                             "     \"rating\": 0.0"
                                             "   }"
                                             " }"
                                             "}")
          expected-update-response (long-str "{"
                                             " \"data\": {"
                                             "   \"updateBrewery\": {"
                                             "     \"id\": 100,"
                                             "     \"name\": \"A new name\","
                                             "     \"description\": \"A new description\","
                                             "     \"imagePath\": \"A new path\","
                                             "     \"tags\": ["
                                             "       {"
                                             "         \"id\": 1,"
                                             "         \"name\": \"Industrielle\""
                                             "       },"
                                             "       {"
                                             "         \"id\": 101,"
                                             "         \"name\": \"A new one 2\""
                                             "       }"
                                             "     ],"
                                             "     \"rating\": 0.0"
                                             "   }"
                                             " }"
                                             "}")
          verification-query "SELECT * FROM brewery WHERE id_brewery = 100"]
      (is (is-data-equal (execute-graphql-query insert-graphql-query) expected-insert-response))
      (is (not (empty? (query-sql-statement verification-query))))
      (is (is-data-equal (execute-graphql-query update-graphql-query) expected-update-response))
      (execute-graphql-query delete-graphql-query)
      (is (empty? (query-sql-statement verification-query))))))
