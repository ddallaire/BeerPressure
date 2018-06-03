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

(defn execute-statement
  [statement]
  (jdbc/execute! db-spec [statement]))

(defn execute-sql-file
  [filename]
  (let [file-content (slurp (io/resource filename))
        statements (str/split file-content #";")]
    (doseq [statement statements] (execute-statement (format-sql-statement statement)))))

(defn db-setup
  [f]
  (execute-sql-file "sql/tables.sql")
  (execute-sql-file "sql/test-data.sql")
  (f))


(defn long-str [& strings] (clojure.string/join "\n" strings))

(defn execute-graphql-query
  [graphql-query]
  (let [request (mock/body
                  (mock/content-type (mock/request :post "/graphql") "application/json")
                  (str "{\"query\":\"" graphql-query "\"}"))]
    (app request)))

(defn is-data-equal
  [response data-str]
  (= (json/read-str (get response :body)) (json/read-str data-str)))
