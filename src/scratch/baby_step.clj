(ns clojure-station.baby-step
  (:use [midje.sweet]))

;; ala 4 clj ;;;
;;; A expression evaluator

;; example
(def exp (every-pred
          (fn [m] (= (m "a") "1"))
          (fn [m] (= (m "b") "2"))))

(def exp (every-pred
          (comp (partial = "1") #(% "a"))
          (comp (partial = "2") #(% "b"))))

(defmulti to-pred3 "convert a parsed query element to a predicate"
  :tag)

(defmethod to-pred3 :symbol
  [{[c] :content}] c)

(fact "to-pred3 :symbol"
      (to-pred3 {:tag :symbol, :content ["a"]}) => "a")

(defmethod to-pred3 :key-value
  [{[a _ b] :content}] (fn [m] (= (m (to-pred3 a))
                                 (to-pred3 b))))

(fact "to-pred3"
      (let [p (to-pred3 {:tag :key-value,
                         :content
                         [{:tag :symbol, :content ["a"]} ":" {:tag :symbol, :content ["1"]}]})]
        (p {"a" "1"}) => truthy
        (p {"a" "2"}) => falsey
        (p {"b" "1"}) => falsey))

(defmethod to-pred3 :binary-op
  [{[op] :content}]
  (cond (= "AND" op) every-pred
        (= "OR"  op) some-fn))

(fact
  (to-pred3 {:tag :binary-op, :content ["AND"]}) => every-pred
  (to-pred3 {:tag :binary-op, :content ["OR"]}) => some-fn)

(def q ["("
           {:tag :key-value,
            :content
            [{:tag :symbol, :content ["a"]} ":" {:tag :symbol, :content ["1"]}]}
           " "
           {:tag :binary-op, :content ["AND"]}
           " "
           {:tag :key-value,
            :content
            [{:tag :symbol, :content ["b"]} ":" {:tag :symbol, :content ["2"]}]}
           ")"])

(defn f
  [s] (fn [m] ))

(future-fact
 ((f q) {"a" "1", "b" "2"}) => true)

(future-fact
 ((f q) {"a" "1", "b" "3"}) => false)
