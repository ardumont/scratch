(ns scratch.graph.genealogy-dorothy
  (:use [dorothy.core])
  (:require [scratch.graph.genealogy :as g]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;; dorothy (graphviz generation) play

(defn make-procreate-edge
  []
  (let [procreate (g/procreate)]
    (concat
     ;; compute the links [father-mother child]
     (->> procreate
          (mapcat (fn [[m f :as v]]
                    (let [common (keyword (format "%s-%s" m f))]
                      (->> v
                           (mapcat g/children)
                           (map (fn [c] [common (keyword c)]))))))
          set
          vec)
     ;; compute the links [[father father-mother] [mother father-mother]]
     (->> procreate
          (mapcat (fn [[m f :as v]]
                    (let [common (keyword (format "%s-%s" m f))]
                      [[(keyword m) common] [(keyword f) common]])))))))
(comment
  (make-procreate-edge))

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

(comment
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
       (->> (make-procreate-edge)
            (map first)
            add-nodes)

       [(edge-attrs {:arrowhead :none
                     :color :red})]
       ;; the couples that add children
       (add-edges (make-procreate-edge)))

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

      (save! "genealogy-family-christelle-antoine.svg" {:format :svg})))
