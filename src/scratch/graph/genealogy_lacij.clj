(ns scratch.graph.genealogy-lacij
  (:use [lacij.layouts.layout]
        [lacij.graph.core]
        [lacij.graph.svg.graph])
  (:require [scratch.corelogic :as g]))

;; from https://github.com/pallix/lacij/blob/master/src/lacij/examples/hierarchicallayout.clj

(defn add-nodes
  [g nodes]
  (reduce
   (fn [g node] (add-node g node (name node)))
   g
   nodes))

(defn add-edges
  [g edges]
  (let [g (add-nodes g (set (flatten edges)))]
    (reduce (fn [g [src dst]]
              (let [id (keyword (str (name src) "-" (name dst)))]
                (add-edge g id src dst)))
            g
            edges)))

(comment ;; some graph example
  (defn gen-graph
    []
    (-> (create-graph)
        (add-edges [[:claude :christelle]
                    [:madeleine :muguette]
                    [:marc :antoine]
                    [:christelle :theo]
                    [:charles-louis :rene]
                    [:marie :jeanne]
                    [:antoine :chloe]
                    [:jeanne :marie-paule]
                    [:rene :marie-paule]
                    [:jeanne :michel]
                    [:antoine :theo]
                    [:jeanne :laurence]
                    [:rene :michel]
                    [:louise :rene]
                    [:cesar :marthe]
                    [:marthe :marc]
                    [:rene :laurence]
                    [:laurence :antoine]
                    [:robert :marc]
                    [:muguette :xavier]
                    [:claude :xavier]
                    [:muguette :arnaud]
                    [:marius :muguette]
                    [:adele :robert]
                    [:claude :arnaud]
                    [:christelle :chloe]
                    [:louis :claude]
                    [:elise :marthe]
                    [:abel :jeanne]
                    [:blanche :claude]
                    [:robert-charles :robert]
                    [:muguette :christelle]])))

  (let [g (-> (gen-graph)
              (layout :hierarchical)
              (build))]
    (export g "/tmp/genealogy-tree.svg" :indent "yes")))

(comment
  (let [p (g/parents)]
    (->> p
         (map (fn [v] (map keyword v)))
         (map vec))))

(defn gen-tree
  "Generate the genealogy tree with the parents"
  []
  (-> (create-graph)
      (add-edges (->> (g/parents)
                      (map (fn [v] (map keyword v)))
                      (map vec)
                      doall))))

(comment
  ;; test the gen-tree
  (gen-tree)

  ;; test the generation
  (let [g (-> (gen-tree)
              (layout :hierarchical) ;; some imbroglio in the arrays
;;              (layout :radial) ;; more interesting
;;              (layout :random) ;; massive explosion and change to each execution
;;              (layout :naive) ;; the compilation does not finish
              (build))]
    (export g "/tmp/genealogy-tree.svg" :indent "yes")))
