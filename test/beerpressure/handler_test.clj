(ns beerpressure.handler-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [beerpressure.handler :refer :all]))

(deftest test-app
  (testing "main route"
    (let [response (app (mock/request :get "/"))]
      (is (= (:status response) 404))
      (is (= (:body response) "Only GraphQL JSON requests to /graphql are accepted on this server"))))

  (testing "not-found route"
    (let [response (app (mock/request :get "/invalid"))]
      (is (= (:status response) 404)))))
