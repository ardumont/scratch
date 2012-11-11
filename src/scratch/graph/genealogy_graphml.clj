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
