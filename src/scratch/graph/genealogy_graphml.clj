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
