(ns scratch.babystep
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

(defmulti to-pred5 "convert a parsed query element to a predicate"
  (fn [e]
    (cond (and (vector? e) (= "(" (first e))) :parens-open
          (vector? e) :parens
          :else (:tag e))))

(defmethod to-pred5 :symbol
  [{[c] :content}] c)

(fact "to-pred5 :symbol"
  (to-pred5 {:tag :symbol, :content ["a"]}) => "a")

(defmethod to-pred5 :key-value
  [{[a _ b] :content}] (fn [m] (= (m (to-pred5 a))
                                 (to-pred5 b))))

(fact "to-pred5"
  (let [p (to-pred5 {:tag :key-value,
                     :content
                     [{:tag :symbol, :content ["a"]} ":" {:tag :symbol, :content ["1"]}]})]
    (p {"a" "1"}) => truthy
    (p {"a" "2"}) => falsey
    (p {"b" "1"}) => falsey))

(defmethod to-pred5 :binary-op
  [{[op] :content}]
  (cond (= "AND" op) every-pred
        (= "OR"  op) some-fn))

(fact
  (to-pred5 {:tag :binary-op, :content ["AND"]}) => every-pred
  (to-pred5 {:tag :binary-op, :content ["OR"]}) => some-fn)

(defmethod to-pred5 :parens-open
  [s]
  (let [r (butlast (rest s))]
    (to-pred5 r)))

;; smaller target to achieve
(fact
  (to-pred5 ["(" :expr :expr2 :expr3 :expr4 ")"]) => (comp :some-pred-fn)
  (provided
    (to-pred5 [:expr :expr2 :expr3 :expr4]) => :some-pred-fn))

;; small target to achieve
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

(future-fact
  ((to-pred5 q) {"a" "1", "b" "2"}) => true)

(future-fact
 ((to-pred5 q) {"a" "1", "b" "3"}) => false)
