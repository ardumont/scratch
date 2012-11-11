(ns scratch.graph.genealogy-graphml
  (:require [clojure.string :as s]
            [scratch.graph.genealogy :as g]
            [scratch.graph.graphml :as gml]))

(defn make-nodes
  "Compute the nodes in the graphml namespace format."
  [nodes]
  (->> nodes
       flatten
       (map keyword)
       set
       (map (fn [n] {:id (keyword n)}))))

(make-nodes (g/parents))

(defn make-edges
  "Compute the edges in the graphml namespace format."
  [edges]
  (->> edges
       (map (fn [[p c]] {:id (str p "-" c)
                        :source (keyword p)
                        :target (keyword c)}))))

(make-edges (g/parents))

(defn make-genealogy-graph
 []
 (let [n (g/parents)]
   (gml/make-graphml
    [{:edgedefault "directed"
      :nodes (make-nodes n)
      :edges (make-edges n)}])))

(comment
  (make-genealogy-graph)
  ;; generate the graphml file and open it with yEd
  (spit "/tmp/genealogy-graph.graphml" (make-genealogy-graph)))
