(ns scratch.core
  (:use [midje.sweet])
  (:require [clojure.zip :as zip]
            [clojure.xml :as xml]))

(fact (+ 1 1) => 2)

;; ----------------------------- rss feed

;; Retrieve a rss feed
(def my-rss (xml/parse "http://adumont.fr/blog/feed/"))

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