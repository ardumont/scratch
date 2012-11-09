(ns scratch.corelogic.baby-step
  (:refer-clojure :exclude [==])
  (:use [clojure.core.logic]))

(comment ;; first query

  ;; unify, give me all the q which validate the relation 'q is true'
  (run* [q]
        (== true q))
  ;; (true)
  )
