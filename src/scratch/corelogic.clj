(ns scratch.corelogic
  (:refer-clojure :exclude [==])
  (:use [clojure.core.logic]))

(comment ;; first query
  (run* [q]
        (== true q)))
;; (true)

