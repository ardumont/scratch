(ns scratch.graph.genealogy-dorothy
  (:use [dorothy.core])
  (:require [scratch.graph.genealogy :as g]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;; dorothy (graphviz generation) play

(defn add-nodes
  "Given a list of edges, compute the nodes"
  [edges]
  (->> edges
       flatten
       (map keyword)
       set
       vec))

(defn add-edges
  "Compute the edges"
  [edges]
  (->> edges
       (map (fn [v] (map keyword v)))
       (map vec)))

(let [mothers (g/mothers)
      fathers (g/fathers)
      parents (concat mothers fathers)]
  (-> (concat
       ;; shape
       ;; [[:node {:shape :box}]]
       ;; all the nodes
       (add-nodes parents)
       ;; all the mothers
       (add-edges mothers)
       ;; all the fathers
       (add-edges fathers))
      doall
      vec
      digraph
      dot
      ;; render a svg format
      ;;    (render {:format :svg})
      ;;    (render {:format :png})
      ;; persist the file
      ;;    (save! "out.png" {:format :png})
      (show!)))
