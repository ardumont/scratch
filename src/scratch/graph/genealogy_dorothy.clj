(ns scratch.graph.genealogy-dorothy
  (:use [dorothy.core])
  (:require [scratch.graph.genealogy :as g]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;; dorothy (graphviz generation) play

(defn add-nodes
  "Given a list of edges, compute the nodes"
  [edges]
  (->> edges
       (map keyword)
       set
       vec))

(add-nodes (g/females))
(add-nodes (g/males))

(defn add-edges
  "Compute the edges"
  [edges]
  (->> edges
       (map (fn [v] (map keyword v)))
       (map vec)))

(-> (concat
     ;; shape
     ;; [[:node {:shape :box}]]

     ;; all the nodes

     ;; mothers
     [(node-attrs {:style :filled
                   :color :pink
                   :shape :ellipse})]
     (add-nodes (g/females))

     ;; fathers
     [(node-attrs {:style :filled
                   :color :cyan})]
     (add-nodes (g/males))

     ;; all the mothers
     (add-edges (g/mothers))

     ;; all the fathers
     (add-edges (g/fathers)))
    doall
    vec
    digraph
    dot
    ;; render a svg format
    ;; (render {:format :svg})
    ;;    (render {:format :png})
    ;; persist the file
    ;;    (save! "out.png" {:format :png})
    ;;    (show!)

    (save! "genealogy-family-christelle-antoine.svg" {:format :svg}))
