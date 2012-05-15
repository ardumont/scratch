(ns scratch.core
  (:use [midje.sweet])
  (:require [clojure.zip :as zip]
            [clojure.xml :as xml]))

(fact (+ 1 1) => 2)

;; ----------------------------- rss feed

;; Retrieve a rss feed
(comment (def my-rss (xml/parse "http://adumont.fr/blog/feed/")))

(defn zip-str [s]
  (zip/xml-zip (xml/parse (java.io.ByteArrayInputStream. (.getBytes s)))))

(def paintings
  (zip-str "<?xml version='1.0' encoding='UTF-8'?>
    <painting>
      <img src='madonna.jpg' alt='Foligno Madonna, by Raphael'/>
      <caption>This is Raphael's 'Foligno' Madonna, painted in
      <date>1511</date>-<date>1512</date>.</caption>
    </painting>"))

;; ----------------------------- lazy-walk

(defn neighbors "Given a graph g and a node n, return the node n's neighbors."
  [g n]
  (g n))

(fact
  (neighbors {:a [:b :c] :b [] :c []} :a) => [:b :c])

(defn lazy-walk
  "Return a lazy sequence of the nodes of a graph starting a node n.
  Optionally, provide a set of visited notes (v) and a collection of nodes to visit (ns)."
  ([g n]
     (lazy-walk g [n] #{}))
  ([g ns v]
     (lazy-seq (let [s (seq (drop-while v ns))
                     n (first s)
                     ns (rest s)]
                 (when s
                   (cons n (lazy-walk g (concat (g n) ns) (conj v n))))))))

(fact "lazy-walk"
 (lazy-walk {:a [:d :b]
             :b [:c]} :a) => [:a :d :b :c]
 (lazy-walk {:a [:d :b]
             :b [:c]
             :d [:e :c]} :a) => [:a :d :e :c :b])

;; -----------------------------

(defn new-comp "Compute the new composant and returns the vector with the new composant and the remaining"
  [c h]
  (let [nr (+ c h)
        rmr (- 255 nr)]
    (if (pos? rmr)
      [nr 0]
      [255 (- 0 rmr)])))

(fact "new-comp"
  (new-comp 0 256) => [255 1]
  (new-comp 255 5) => [255 5]
  (new-comp 250 5) => [255 0]
  (new-comp 254 10) => [255 9]
  (new-comp 250 100) => [255 95])

(defmulti rgb :min)

;; Compute the new composant [r g b] - Distribute ~equally the composant g in the composant r and b.
(defmethod rgb :g [{:keys [comp]}]
  (let [[r g b] comp
        h (quot g 2)
        h1 (+ h (rem g 2))
        [nr rmr] (new-comp r h1)
        [nb ng] (new-comp b (+ h rmr))]
    [nr ng nb]))

(fact
  (rgb {:min :g :comp [254 2 255]})  => [255 1 255]
  (rgb {:min :g :comp [250 10 255]}) => [255 5 255]
  (rgb {:min :g :comp [1 2 1]})      => [2 0 2]
  (rgb {:min :g :comp [10 20 10]})   => [20 0 20]
  (rgb {:min :g :comp [10 19 10]})   => [20 0 19]
  (rgb {:min :g :comp [200 50 100]}) => [225 0 125]
  (rgb {:min :g :comp [254 20 250]}) => [255 14 255]
  (rgb {:min :g :comp [250 3 254]})  => [252 0 255]
  (rgb {:min :g :comp [250 10 254]}) => [255 4 255]
  (rgb {:min :g :comp [255 50 100]}) => [255 0 150])

(defmethod rgb :r [{:keys [comp]}]
  (let [[r g b] comp
        h (quot r 2)
        h1 (+ h (rem r 2))
        [ng rmg] (new-comp g h1)
        [nb nr] (new-comp b (+ h rmg))]
    [nr ng nb]))

(fact
  (rgb {:min :r :comp [2 254 255]})  => [1 255 255]
  (rgb {:min :r :comp [10 250 255]}) => [5 255 255]
  (rgb {:min :r :comp [2 1 1]})      => [0 2 2]
  (rgb {:min :r :comp [20 10 10]})   => [0 20 20]
  (rgb {:min :r :comp [19 10 10]})   => [0 20 19]
  (rgb {:min :r :comp [50 200 100]}) => [0 225 125]
  (rgb {:min :r :comp [20 254 250]}) => [14 255 255]
  (rgb {:min :r :comp [3 250 254]})  => [0 252 255]
  (rgb {:min :r :comp [10 250 254]}) => [4 255 255]
  (rgb {:min :r :comp [50 255 100]}) => [0 255 150])

(comment
  (defn distribute "Distribute the smallest composant rgb into the 2 others from left to right"
    [[r g b :as comp]]
    (let [m (apply min comp)]
      
      m))

  (fact
    (distribute [1 2 3]) => [0 3 3]))