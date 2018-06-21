(ns beerpressure.db.common-test
  (:require [ring.mock.request :as mock]
            [beerpressure.handler :refer :all]
            [beerpressure.db.common :refer :all]
            [clojure.data.json :as json]
            [clojure.string :as str]
            [clojure.java.io :as io]
            [clojure.java.jdbc :as jdbc]))

(defn remove-sql-comments
  [statement]
  (str/replace statement #"\/\*[a-zA-Z0-9:=_ \.x-]*\*\/" ""))

(defn format-sql-statement
  [statement]
  (str/trim (str/replace (str/replace
                           (remove-sql-comments statement) #"\n" " ")
                         #"\x{FEFF}" "")))

(defn query-sql-statement
  [statement]
  (jdbc/query db-spec statement))

(defn execute-sql-statement
  [statement]
  (jdbc/execute! db-spec [statement]))

(defn execute-sql-file
  [filename]
  (let [file-content (slurp (io/resource filename))
        statements (str/split file-content #";")]
    (doseq [statement statements] (execute-sql-statement (format-sql-statement statement)))))

(defn db-setup-fixture
  [f]
  (execute-sql-file "sql/tables.sql")
  (execute-sql-file "sql/test-data.sql")
  (f))

(defn db-setup-with-logged-user-fixture
  [f]
  (execute-sql-file "sql/tables.sql")
  (execute-sql-file "sql/test-data.sql")
  (execute-sql-statement "INSERT INTO \"user\"(cip, name, surname) VALUES('test1234', 'testName', 'testSurname')")
  (execute-sql-statement "INSERT INTO authentication_token(cip, token, time) VALUES('test1234', 'authentication_token', now());")
  (f)
  (execute-sql-statement "DELETE FROM authentication_token WHERE cip = 'test1234'")
  (execute-sql-statement "DELETE FROM \"user\" WHERE cip = 'test1234'"))


(defn long-str [& strings] (clojure.string/join "\n" strings))

(defn execute-graphql-query
  [graphql-query]
  (let [request (mock/body
                  (mock/header (mock/content-type (mock/request :post "/graphql") "application/json")
                               "authorization" "authentication_token")
                  (str "{\"query\":\"" graphql-query "\"}"))]
    (app request)))

(defn is-data-equal
  [response data-str]
  (= (json/read-str (get response :body)) (json/read-str data-str)))
