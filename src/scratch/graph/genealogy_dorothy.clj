(ns scratch.graph.genealogy-dorothy
  (:use [dorothy.core])
  (:require [scratch.graph.genealogy :as g]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;; dorothy (graphviz generation) play

(let [p (g/parents)]
  (-> (concat
       ;; shape
       ;; [[:node {:shape :box}]]
       ;; all the nodes
       (->> p
            flatten
            (map keyword)
            set
            vec)
       ;; all the edges
       (->> p
            (map (fn [v] (map keyword v)))
            (map vec)))
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
