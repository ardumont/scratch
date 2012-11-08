(ns scratch.corelogic
  (:refer-clojure :exclude [==])
  (:use [clojure.core.logic]))

(run* [q]
      (== true q))
;; (true)

(defrel father f c)
(facts father '[[antoine chloe]
                [antoine theo]
                [marc antoine]
                [robert marc]
                [rene laurence]
                [louis rene]
                [rene michel]
                [renel marie-paule]])

;; give me all the relationships father/child
(run* [q]
      (fresh [f c]
             (father f c)
             (== q [f c])))
