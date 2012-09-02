(defproject scratch/scratch "1.0.0-SNAPSHOT"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [midje/midje "1.4.0"]
                 [org/jaudiotagger "2.0.3"]]
  :profiles {:dev
             {:dependencies
              [[com.intelie/lazytest
                "1.0.0-SNAPSHOT"
                :exclusions
                [swank-clojure]]
               [midje "1.4.0"]]}}
  :description "a scratch project to rapidly test stuff")
