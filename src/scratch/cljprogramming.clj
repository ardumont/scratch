(ns scratch.cljprogramming
  (:use [midje.sweet]))

;; just to be sure to have understood :D
(fact "play with take-nth"
  (take-nth 2 (range 10)) => [0 2 4 6 8]
  (take-nth 3 (range 10)) => [0 3 6 9])

(fact "play with empty"
  (empty [1 2 3 4])   => []
  (empty '(1 2 3 4))  => '()
  (empty #{1 2 3})    => #{}
  (empty {:a 1 :b 2}) => {})

(fact "play again with interleave"
  (interleave [1 2 3 4] [:a :b :c :d]) => [1 :a 2 :b 3 :c 4 :d]
  (interleave [1 2 3] [:a :b :c :d])   => [1 :a 2 :b 3 :c]
  (interleave [1 2 3 4] [:a :b :c])    => [1 :a 2 :b 3 :c])


