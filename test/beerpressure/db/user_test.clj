(ns beerpressure.db.user-test
  (:require [clojure.test :refer :all]
            [beerpressure.handler :refer :all]
            [beerpressure.db.user :refer :all]
            [beerpressure.db.common-test :refer :all]
            [environ.core :refer [env]]))

(use-fixtures :once db-setup-fixture)

(deftest test-extract-user-from-cas-xml-response
  (testing "extract-user-from-cas-xml-response"
    (let [response-xml (long-str "<cas:serviceResponse xmlns:cas='http://www.yale.edu/tp/cas'>"
                                 "  <cas:authenticationSuccess>"
                                 "    <cas:user>dald2202</cas:user>"
                                 "    <cas:attributes>"
                                 "      <cas:idGIA>4F566EE0-1887-41A5-AF51-F3582C121079</cas:idGIA>"
                                 "      <cas:nomFamille>Dallaire</cas:nomFamille>"
                                 "      <cas:titre>M.</cas:titre>"
                                 "      <cas:genre>M</cas:genre>"
                                 "      <cas:cip>dald2202</cas:cip>"
                                 "      <cas:courriel>David.Dallaire@USherbrooke.ca</cas:courriel>"
                                 "      <cas:prenom>David</cas:prenom>"
                                 "      <cas:nomAffichage>David Dallaire</cas:nomAffichage>"
                                 "    </cas:attributes>"
                                 "  </cas:authenticationSuccess>"
                                 "</cas:serviceResponse>")
          user (extract-user-from-cas-xml-response response-xml)]
      (is (= user {:cip     "dald2202"
                   :name    "David"
                   :surname "Dallaire"})))))

(deftest test-login
  (testing "Existing user"
    (let [user {:cip     "dald2202"
                :name    "David"
                :surname "Dallaire"}
          logged-user (login user)
          db-logged-user (get-logged-user (get logged-user :token))]
      (is (= (get db-logged-user :cip) "dald2202"))
      (is (= (get db-logged-user :name) "David"))
      (is (= (get db-logged-user :surname) "Dallaire"))))
  (testing "Not existing user"
    (let [user {:cip     "test1234"
                :name    "TestName"
                :surname "TestSurname"}
          logged-user (login user)
          db-logged-user (get-logged-user (get logged-user :token))]
      (is (= (get db-logged-user :cip) "test1234"))
      (is (= (get db-logged-user :name) "TestName"))
      (is (= (get db-logged-user :surname) "TestSurname"))))
  (testing "Already logged user"
    (let [user {:cip     "test1234"
                :name    "TestName"
                :surname "TestSurname"}
          logged-user1 (login user)
          logged-user2 (login user)
          db-logged-user (get-logged-user (get logged-user2 :token))]
      (is (= (get db-logged-user :cip) "test1234"))
      (is (= (get db-logged-user :name) "TestName"))
      (is (= (get db-logged-user :surname) "TestSurname")))))

(deftest test-logout
  (testing "Logged user"
    (let [user {:cip     "dald2202"
                :name    "David"
                :surname "Dallaire"}
          logged-user (login user)
          token (get logged-user :token)]
      (logout token)
      (is (= (get-logged-user token) nil))))
  (testing "Not logged user"
    (let [token (str (java.util.UUID/randomUUID))]
      (logout token)
      (is (= (get-logged-user token) nil)))))

(deftest test-update-token-time
  (testing "Logged user"
    (let [user {:cip     "dald2202"
                :name    "David"
                :surname "Dallaire"}
          logged-user (login user)
          token (get logged-user :token)
          db-logged-user (get-logged-user token)]
      (Thread/sleep 1000)
      (update-token-time token)
      (is (not= (get (get-logged-user token) :time) (get db-logged-user :time))))))

(deftest test-is-token-time-valid
  (testing "test-is-token-time-valid"
    (let [time-now-ms (System/currentTimeMillis)
          session-duration-sec (Integer/parseInt (env :session-duration-sec))
          session-duration-ms (* session-duration-sec 1000)]
      (is (is-token-time-valid (java.sql.Timestamp. (- time-now-ms 41))))
      (is (not (is-token-time-valid (java.sql.Timestamp. (- time-now-ms (+ session-duration-ms 1)))))))))
