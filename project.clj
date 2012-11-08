(defproject scratch/scratch "1.0.0-SNAPSHOT"
  :min-lein-version "2.0.0"
  :profiles {:dev {:dependencies [[org.apache.hadoop/hadoop-core "0.20.2-dev"]]}}
  :dependencies [[org.clojure/clojure        "1.4.0"]
                 [dorothy                    "0.0.3"]
                 [midje                      "1.4.0"]
                 [org/jaudiotagger           "2.0.3"]
                 [shake                      "0.2.0"]
                 [incanter                   "1.3.0"]
                 [robert/hooke               "1.1.2"]
                 [cascalog                   "1.10.0"]
                 [korma                      "0.3.0-beta11"]
                 [mysql/mysql-connector-java "5.1.21"]
                 [clj-http                   "0.4.0"]]
  :description "a scratch project to rapidly test stuff")
