(defproject beerpressure "0.1.0-SNAPSHOT"
  :description "Graphql API for S6 semester project"
  :url "http://github.com/ddallaire/BeerPressure"
  :min-lein-version "2.0.0"
  :dependencies [[environ "1.1.0"]
                 [org.clojure/clojure "1.8.0"]
                 [org.clojure/data.json "0.2.6"]
                 [org.postgresql/postgresql "9.4-1201-jdbc41"]
                 [yesql "0.5.3"]
                 [com.walmartlabs/lacinia "0.25.0"]
                 [ring/ring-core "1.6.0"]
                 [ring-cors "0.1.10"]]
  :plugins [[lein-ring "0.11.0"]
            [lein-environ "1.1.0"]]
  :ring {:handler beerpressure.handler/app}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring/ring-mock "0.3.0"]]}})
