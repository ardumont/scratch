(ns scratch.test.rss-test
  (:use [midje.sweet :only [fact]]
        [scratch.rss :as r]))

(fact
 (map-from [:a :b :c]) => {:a 0 :b 1 :c 2})
