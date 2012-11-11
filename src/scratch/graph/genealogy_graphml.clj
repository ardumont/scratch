(ns scratch.graph.genealogy-graphml
  (:require [clojure.string :as s]
            [scratch.graph.genealogy :as g]
            [scratch.graph.graphml :as gml]))

(defn node-id
  "Create a node id"
  [n]
  (str n))

(defn make-nodes
  "Compute the nodes in the graphml namespace format."
  [nodes]
  (->> nodes
       flatten
       set
       (map (fn [n] {:id (node-id n)}))))

(make-nodes (g/parents))

(defn make-edges
  "Compute the edges in the graphml namespace format."
  [edges]
  (->> edges
       (map (fn [[p c]] {:id (str p "-" c)
                        :source (node-id p)
                        :target (node-id c)}))))

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

