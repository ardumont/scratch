(ns scratch.graph.genealogy-lacij
  (:use [lacij.layouts.layout]
        [lacij.graph.core]
        [lacij.graph.svg.graph])
  (:require [scratch.graph.genealogy :as g]
            [scratch.graph.genealogy-util :as u]))

;; from https://github.com/pallix/lacij/blob/master/src/lacij/examples/hierarchicallayout.clj

(defn add-nodes
  "Complete the graph g with the nodes. "
  [g nodes]
  (reduce
   (fn [g node]
     (add-node g node (name node)))
   g
   nodes))

(comment
  (add-nodes (create-graph) (->> (g/procreate)
                                 (u/make-father-mother-edges)
                                 (map first))))

(defn add-edges
  "Complete the graph g with the edges."
  [g edges]
  (reduce (fn [g [src dst]]
            (println "src" src "dst" dst)
            (let [id (keyword (str (name src) "-" (name dst)))]
              (add-edge g id src dst)))
          g
          edges))

(add-edges (-> (create-graph)
               (add-nodes (map keyword (g/females)))
               (add-nodes (map keyword (g/males)))
               (add-nodes (->> (g/procreate)
                               (u/make-father-mother-edges)
                               (map first))))
           (u/make-procreate-edges (g/procreate)))


(defn gen-tree
  "Generate the genealogy tree with the parents"
  []
  (let [procreate-relations (g/procreate)]
    (-> (create-graph)
        (add-default-node-attrs :fill "pink"
                                :width 50
                                :height 30
                                :shape :circle)
        (add-nodes (map keyword (g/females)))
        (add-default-node-attrs :fill "blue"
                                :width 50
                                :height 30
                                :shape :circle)
        (add-nodes (map keyword (g/males)))

        (add-default-node-attrs :fill "purple"
                                :width 0
                                :height 0
                                :shape :circle)
        (add-nodes (->> (g/procreate)
                        (u/make-father-mother-edges)
                        (map first)))

        (add-edges (u/make-procreate-edges procreate-relations)))))

;; test the generation
(let [g (-> (gen-tree)
            (layout :hierarchical) ;; some imbroglio in the arrays
            ;;(layout :radial :radius 90) ;; more interesting
            ;;              (layout :random) ;; massive explosion and change to each execution
            ;;              (layout :naive) ;; the compilation does not finish
            (build))]
  (export g "genealogy-family.lacij.svg" :indent "yes"))
