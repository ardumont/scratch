(ns scratch.graph.genealogy-graphml
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
 :nodes [{:id :n1}
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
  [{:keys [id]}]
  (format "<node id='%s' />" id))

(comment
  (make-node {:id :test}))

(defn make-nodes
  "Constructs graphml nodes entries."
  [nodes]
  (->> nodes
       (map make-node)
       (s/join "\n")))

(comment
  (make-nodes [{:id :n1} {:id :n2}]))
