(ns scratch.corelogic.baby-step
  (:refer-clojure :exclude [==])
  (:use [clojure.core.logic]))

(comment ;; first query

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;; simple query

  ;; unify, give me all the q which validate the relation 'q is true'
  (run* [q]
        (== true q))
  ;; (true)

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;; membero

  ;; membero, give me all the q that satisfies the relation 'q is a member of the list '(:a :b :c)
  (run* [q]
        (membero q '(:a :b :c)))
  ;; '(:a :b :c)

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;; logical intersection

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

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;; logical union - conde

  ;; now for some logical union
  (run* [q]
        (conde
         ((membero q '(:a :b :c)))
         ((membero q '(:b :c :d)))))
  ;; '(:a :b :b :c :c :d)

  ;; this also can be written like this
  (run* [q]
        (conde
         [(membero q '(:a :b :c))]
         [(membero q '(:b :c :d))]))
  ;; '(:a :b :b :c :c :d)

  (run* [q]
        (conde
         [succeed succeed succeed succeed]))
  ;; (_.0)

  (run* [q]
        (conde
         [succeed succeed fail succeed])) ;; careful it's an logical AND inside the []
  ;; ()

  (run* [q]
        (conde
         [succeed succeed fail succeed]      ;; fail
         [succeed succeed succeed succeed])) ;; but here success
  ;; (_.0)

  (run* [q]
        (conde
         [(== q 1) (== q 2)])) ;; not possible to be equal to 1 and 2
  ;; ()

  (run* [q]
        (conde
         [(== q 1)]
         [(== q 2)]))
  ;; (1 2)
  )
