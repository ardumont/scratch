(ns scratch.hooke
  (:require [robert.hooke :as h]))

(defn sum
  [& r]
  (apply + r))

(sum 1 2 34 4 5)

(defn spy
  [f & x]
  (let [r (apply f x)]
    (println (format "(%s %s) => %s" f x r))
    r))

(h/remove-hook #'sum #'spy)
(h/add-hook #'sum #'spy)
