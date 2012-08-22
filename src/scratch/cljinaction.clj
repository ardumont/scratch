(ns scratch.cljinaction
  (:use [midje.sweet]))

;; ----------------------- chapter 11 - Scaling through messaging

(defn dispatch-fn
  [dispatch-class [name & body]]
  `(defmethod ~name ~dispatch-class ~@body))

(defmacro details-mo [mo-name dispatch-class & bodies]
  `(do
     ~@(map #(dispatch-fn dispatch-class %) bodies)))

;; ----------------------- chapter 12 - Data processing with clojure
