(ns scratch.zipper
  (:use [midje.sweet])
  (:require [clojure.zip :as z]))

(def v [[1 2 [3 4]] [5 6]])
;;= #'scratch.zipper/v

(fact "Movement on zipper"
  (let [vz (z/vector-zip v)]
    (-> vz z/node)                               => [[1 2 [3 4]] [5 6]]
    (-> vz z/down z/node)                        => [1 2 [3 4]]
    (-> vz z/down z/down z/node)                 => 1
    (-> vz z/down z/down z/right z/node)         => 2
    (-> vz z/down z/right z/node)                => [5 6]
    (-> vz z/down z/right z/down z/right z/node) => 6))

(fact "Editing zipper"
  (let [vz (z/vector-zip v)]
    (-> vz z/down z/right (z/replace 56) z/root) => [[1 2 [3 4]] 56]
    (-> vz z/down z/right (z/remove) z/node)     => 4
    (-> vz z/down z/right (z/remove) z/root)     => [[1 2 [3 4]]]
    (-> vz z/down z/right (z/replace 56) (z/edit * 2) z/root) => [[1 2 [3 4]] 112]))










