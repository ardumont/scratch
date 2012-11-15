(ns scratch.graph.graphml
  (:require [clojure.string :as s]
            [scratch.graph.genealogy :as g]))

;; target:
;; <graphml>
;; <graph edgedefault='directed'>
;; <node id='n1' />
;; <node id='n2' />
;; <node id='n3' />
;; <node id='n4' />
;; <edge id='e1' source='n1' target='n3' />
;; <edge id='e2' source='n2' target='n3' />
;; </graph>
;; </graphml>

;; proposed structure
{:edgedefault :directed
 :nodes [{:id :n1
          :name :n1}
         {:id :n2}
         {:id :n3}
         {:id :n4}]
 :edges [{:id :e1
          :source :n1
          :target :n3}
         {:id :e1
          :source :n2
          :target :n3}]}

(defn make-node
  "Constructs graphml node entry."
  [{:keys [id name]}]
  (format "<node id='%s' attr.name='%s' />" id name))

(comment
  (make-node {:id :test})
  (make-node {:id :test :name "test"}))

(defn make-nodes
  "Constructs graphml nodes entries."
  [nodes]
  (->> nodes
       (map make-node)
       (s/join "\n")))

(comment
  (make-nodes [{:id :n1} {:id :n2}]))

(defn make-edge
  "Constructs graphml edge entry."
  [{:keys [id source target]}]
  (format "<edge id='%s' source='%s' target='%s'/>" id source target))

(comment
  (make-edge {:id "test" :source :src :target :dest}))

(defn make-edges
  "Constructs graphml edges entries"
  [edges]
  (->> edges
       (map make-edge)
       (s/join "\n")))

(comment
  (make-edges [{:id :e1
                :source :n1
                :target :n3}
               {:id :e1
                :source :n2
                :target :n3}]))

(defn make-graph
   "Construct the graph (indepedent from the complete graphml)"
  [{:keys [id edgedefault nodes edges]}]
  (s/join "\n"
          [(format "<graph id='%s' edgedefault='%s'>" id edgedefault)
           (make-nodes nodes)
           (make-edges edges)
           "</graph>"]))

(comment
  (make-graph {:id :some-graph
               :edgedefault :directed
               :nodes [{:id :n1}
                       {:id :n2}
                       {:id :n3}
                       {:id :n4}]
               :edges [{:id :e1
                        :source :n1
                        :target :n3}
                       {:id :e1
                        :source :n2
                        :target :n3}]}))

(defn make-graphml-headers
  "Generate the standard graphml header."
  []
  "<?xml version=\"1.0\" encoding=\"UTF-8\"?>
<graphml xmlns=\"http://graphml.graphdrawing.org/xmlns\"
    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"
    xsi:schemaLocation=\"http://graphml.graphdrawing.org/xmlns
     http://graphml.graphdrawing.org/xmlns/1.0/graphml.xsd\">")

(comment
  (make-graphml-headers))

(defn make-graphml
  "Compute the overall graphml file."
  [graphs]
  (s/join "\n"
          [(make-graphml-headers)
           (->> graphs
                (map make-graph)
                (s/join "\n"))
           "</graphml>"]))

(comment
  (make-graphml [{:id :some-graph
                   :edgedefault :directed
                   :nodes [{:id :n1}
                           {:id :n2}
                           {:id :n3}
                           {:id :n4}]
                   :edges [{:id :e1
                            :source :n1
                            :target :n3}
                           {:id :e1
                            :source :n2
                            :target :n3}]}]))

