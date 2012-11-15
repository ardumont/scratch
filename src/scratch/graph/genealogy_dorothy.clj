(ns scratch.graph.genealogy-dorothy
  (:use [dorothy.core])
  (:require [scratch.graph.genealogy :as g]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;; dorothy (graphviz generation) play

(defn add-edges
  [edges]
  (->> edges
       (map (fn [v] (map keyword v)))
       (map vec)))

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
       ;; all the mothers
       (add-edges (g/mothers))
       ;; all the fathers
       (add-edges (g/fathers)))
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
