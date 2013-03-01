(defproject scratch/scratch "1.0.0-SNAPSHOT"
  :min-lein-version "2.0.0"
  :profiles {:dev {:dependencies [[org.apache.hadoop/hadoop-core "0.20.2-dev"]
                                  [midje                         "1.4.0"]]}}
  :dependencies [[org.clojure/clojure                       "1.5.0"]
                 [dorothy                                   "0.0.3"]
                 [midje                                     "1.4.0"]
                 [org/jaudiotagger                          "2.0.3"]
                 [shake                                     "0.2.0"]
                 [incanter                                  "1.3.0"]
                 [robert/hooke                              "1.1.2"]
                 [cascalog                                  "1.10.0"]
                 [korma                                     "0.3.0-beta11"]
                 [mysql/mysql-connector-java                "5.1.21"]
                 [clj-http                                  "0.6.3"]
                 [org.clojure/core.logic                    "0.7.5"]
                 [lacij                                     "0.7.1"]
                 [com.google.apis/google-api-services-drive "v2-rev25-1.12.0-beta"]
                 [oauth-clj                                 "0.1.0"]
                 [com.datomic/datomic-free                  "0.8.3619"]
                 [org.slf4j/slf4j-api                       "1.7.2"]]
  :description "a scratch project to rapidly test stuff")
