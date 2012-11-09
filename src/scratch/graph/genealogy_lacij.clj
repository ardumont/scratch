(ns scratch.graph.genealogy-lacij
  (:use [lacij.layouts.layout]
        [lacij.graph.core]
        [lacij.graph.svg.graph])
  (:require [scratch.corelogic :as g]))

;; from https://github.com/pallix/lacij/blob/master/src/lacij/examples/hierarchicallayout.clj

(defn add-nodes
  [g & nodes]
  (reduce
   (fn [g node] (add-node g node (name node)))
   g
   nodes))

(defn add-edges
  [g & edges]
  (let [g (apply add-nodes g (set (flatten edges)))]
    (reduce (fn [g [src dst]]
              (let [id (keyword (str (name src) "-" (name dst)))]
                (add-edge g id src dst)))
            g
            edges)))

(comment ;; some example
  (defn gen-graph4
    []
    (-> (create-graph)
        (add-edges [:a1 :p]
                   [:pr :a1]
                   [:po :a1]
                   [:fo :a1]
                   [:a2 :po]
                   [:a3 :po]
                   [:ac :a2]
                   [:pu :a3]
                   [:a4 :ac]
                   [:a5 :ac]
                   [:a6 :ac]
                   [:a7 :ac]
                   [:a8 :pu]
                   [:a9 :pu]
                   [:a10 :pu]
                   [:pur :a4]
                   [:puf :a5]
                   [:pe :a6]
                   [:ab :a6]
                   [:th :a7]
                   [:pos :a7]
                   [:br :a8]
                   [:fl :a9]
                   [:ju :a10])))

  (let [g (-> (gen-graph4)
              (layout :hierarchical)
              (build))]
    (export g "/tmp/hierarchical.svg" :indent "yes")))
