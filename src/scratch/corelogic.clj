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

;; give me all the mother/child relationships
(run* [q]
      (fresh [m c]
             (mother m c)
             (== q [m c])))

;; give me all the parents relationships
(run* [q]
      (fresh [p c]
             (conde
              ((mother p c))
              ((father p c)))
             (== q [p c])))


;; give me all the parents
(defn parent
  [p c]
  (conde
   ((father p c))
   ((mother p c))))

;; the same query as before but with a function
(run* [q]
      (fresh [p c]
             (parent p c)
             (== q [p c])))

