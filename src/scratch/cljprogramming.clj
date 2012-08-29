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

;; ----------------------------- swap-pairs

(defn swap-pairs
  [sequential]
  (into (empty sequential)
        (interleave (take-nth 2 (drop 1 sequential))
                    (take-nth 2 sequential))))

(fact
  (swap-pairs (apply list (range 10)))   => '(8 9 6 7 4 5 2 3 0 1);; look it's a list!
  (swap-pairs (apply vector (range 10))) => [1 0 3 2 5 4 7 6 9 8]);; it's a vec!

;; ----------------------------- map-map

(defn map-map "map f inside a map (keep the order of the initial map)"
  [f m]
  (into (empty m)
        (for [[k v] m]
          [k (f v)])))

(fact
  (map-map inc (hash-map :a 1 :z 10 :c 41))   => (just [:a 2] [:z 11] [:c 42] :in-any-order)
  (map-map inc (sorted-map :z 10 :c 41 :a 1)) => (sorted-map :z 11 :c 42 :a 2))

(defn map-map2 "Same as before with reduce"
  [f m]
  (reduce (fn [n [k v]] (assoc n k (f v))) (empty m) m))

(fact
  (map-map2 inc (hash-map :a 1 :z 10 :c 41))   => (just [:a 2] [:z 11] [:c 42] :in-any-order)
  (map-map2 inc (sorted-map :z 10 :c 41 :a 1)) => (sorted-map :z 11 :c 42 :a 2))

;; ----------------------------- sequences

(fact
  (cons 0 [4 3 2 1])      => [0 4 3 2 1]
  (cons 0 '(4 3 2 1))     => '(0 4 3 2 1)
  (cons 10 (seq [:a :b])) => [10 :a :b])

(fact
  (cons 0 (cons 1 (cons 2 (cons 3 [:a :b])))) => [0 1 2 3 :a :b]
  (->> [:a :b]
       (cons 3)
       (cons 2)
       (cons 1)
       (cons 0))                              => [0 1 2 3 :a :b]
       (list* 0 1 2 3 [:a :b])                => [0 1 2 3 :a :b])

;; ----------------------------- sequences - lazyseq

(defn random-ints "Returns a lazy-seq of random integers [0,limit)"
  [limit]
  (lazy-seq
   (println "realizing a new number in the lazy-seq")
   (cons (rand-int limit) (random-ints limit))))

(defn random-ints2
  [limit]
  (repeatedly #(rand-int limit)))


