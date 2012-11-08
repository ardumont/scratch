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
                [rene marie-paule]])

;; give me all the relationships father/child
(run* [q]
      (fresh [f c]
             (father f c)
             (== q [f c])))

(defrel mother m c)
(facts mother '[[christelle chloe]
                [christelle theo]
                [laurence antoine]
                [jeanne laurence]
                [jeanne michel]
                [jeanne marie-paule]
                [marthe marc]])

;; give me all the mother/child relationship
(run* [q]
      (fresh [m c]
             (mother m c)
             (== q [m c])))



