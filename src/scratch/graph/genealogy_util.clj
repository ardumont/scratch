(ns scratch.graph.genealogy-util
  (:require [scratch.graph.genealogy :as g]))

(defn kwd-father-mother
  "Compute the keyword father-mother"
  [f m]
  (-> "%s-%s"
      (format m f)
      keyword))

(defn make-father-mother-edges
  "Compute the links (father-mother child)"
  [procreate]
  (->> procreate
          (mapcat (fn [[m f :as v]]
                    (let [common (kwd-father-mother f m)]
                      (->> v
                           (mapcat g/children)
                           (map (fn [c] [common (keyword c)]))))))
          set
          vec))

(comment
  (make-father-mother-edges (g/procreate)))

(defn make-father-father-mother-and-mother-father-mother-edges
  "Compute the links (father father-mother) (mother father-mother) edges"
  [procreate]
  (mapcat (fn [[m f :as v]]
            (let [common (kwd-father-mother f m)]
              [[(keyword m) common] [(keyword f) common]]))
          procreate))
(comment
  (make-father-father-mother-and-mother-father-mother-edges (g/procreate)))

(defn make-procreate-edges
  "Compute the edges 'procreation'"
  [procreate-relations]
  (concat
   ;; compute the links [father-mother child]
   (make-father-mother-edges procreate-relations)
   ;; compute the links [[father father-mother] [mothe  father-mother]]
   (make-father-father-mother-and-mother-father-mother-edges procreate-relations)))

(comment
  (make-procreate-edges (g/procreate)))
