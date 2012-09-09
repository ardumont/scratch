(ns scratch.zipper
  (:use [midje.sweet])
  (:require [clojure.zip :as z]))

(def v [[1 2 [3 4]] [5 6]])
;;= #'scratch.zipper/v

(fact
  (-> v z/vector-zip z/node) => [[1 2 [3 4]] [5 6]]
  (-> v z/vector-zip z/down z/node) => [1 2 [3 4]]
  (-> v z/vector-zip z/down z/down z/node) => 1
  (-> v z/vector-zip z/down z/down z/right z/node) => 2
  (-> v z/vector-zip z/down z/right z/node) => [5 6]
  (-> v z/vector-zip z/down z/right z/down z/right z/node) => 6)










