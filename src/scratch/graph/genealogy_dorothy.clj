(ns scratch.corelogic
  (:refer-clojure :exclude [==])
  (:use [clojure.core.logic]
        [dorothy.core])
  (:require [scratch.corelogic :as g]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;; dorothy (graphviz generation) play

(let [p (g/parents)]
  (-> (concat
       ;; shape
       ;; [[:node {:shape :box}]]
       ;; all the nodes
       (->> p
            (mapcat identity)
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
      (show! {:layout :neato})))
