(ns scratch.cljinaction
  (:use [midje.sweet]))

(defn dispatch-fn
  [dispatch-class [name & body]]
  `(defmethod ~name ~dispatch-class ~@body))

#_(fact
  (dispatch-fn java.lang.String '(foo bar foobar)) => `(defmethod foo java.lang.String '(foo bar bar)))

(defmacro details-mo [mo-name dispatch-class & bodies]
  `(do
     ~@(map #(dispatch-fn dispatch-class %) bodies)))
