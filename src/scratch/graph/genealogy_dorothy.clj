(ns scratch.graph.genealogy-dorothy
  (:use [dorothy.core])
  (:require [scratch.graph.genealogy :as g]
            [scratch.graph.genealogy-util :as u]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;; dorothy (graphviz generation) play

(defn add-nodes
  "Given a list of edges, compute the nodes"
  [edges]
  (->> edges
       (map keyword)
       set
       vec))

(comment
  (add-nodes (g/females))
  (add-nodes (g/males)))

(defn add-edges
  "Compute the edges"
  [edges]
  (->> edges
       (map (fn [v] (map keyword v)))
       (map vec)))

(let [procreate-relations (g/procreate)]
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

       [(node-attrs {:style :filled
                     :color :purple
                     :height "0"
                     :width "0"
                     :label ""})]
       ;; the couple nodes
       (add-nodes (map first (u/make-father-mother-edges procreate-relations)))

       [(edge-attrs {:arrowhead :none
                     :color :red})]
       ;; the couples that add children
       (add-edges (u/make-procreate-edges procreate-relations)))

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

      (save! "genealogy-family.graphviz.svg" {:format :svg})))
