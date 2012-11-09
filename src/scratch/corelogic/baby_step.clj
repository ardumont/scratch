(ns scratch.corelogic.baby-step
  (:refer-clojure :exclude [==])
  (:use [clojure.core.logic]))

(comment ;; first query

  ;; unify, give me all the q which validate the relation 'q is true'
  (run* [q]
        (== true q))
  ;; (true)

  ;; membero, give me all the q that satisfies the relation 'q is a member of the list '(:a :b :c)
  (run* [q]
        (membero q '(:a :b :c)))
  ;; '(:a :b :c)

  ;; some logical intersection
  (run* [q]
        (membero q '(:a :b :c))
        (membero q '(:b :c :d)))
  ;; '(:b :c)

  ;; this can also be written with the fresh and unify operations
  (run* [q]
        (fresh [m]
               (membero m '(:a :b :c))
               (membero q '(:b :c :d))
               (== m q)))
  ;; '(:b :c)

  ;; now for some logical union
  (run* [q]
        (conde
         ((membero q '(:a :b :c)))
         ((membero q '(:b :c :d)))))
  ;; '(:a :b :b :c :c :d)

  (run* [q]
        (conde
         [(membero q '(:a :b :c))]
         [(membero q '(:b :c :d))]))
  ;; '(:a :b :b :c :c :d)
  )
