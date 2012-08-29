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

(defn swap-pairs
  [sequential]
  (into (empty sequential)
        (interleave (take-nth 2 (drop 1 sequential))
                    (take-nth 2 sequential))))

(fact
  (swap-pairs (apply list (range 10))) => '(8 9 6 7 4 5 2 3 0 1)
  (swap-pairs (apply vector (range 10))) => [1 0 3 2 5 4 7 6 9 8])
