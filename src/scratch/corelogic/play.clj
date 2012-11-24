(ns scratch.corelogic.play
  (:refer-clojure :exclude [==])
  (:use [clojure.core.logic])
  (:require [clojure.core.logic.arithmetic :as a]))

;; from http://vimeo.com/46163091 david nolen's core logic presentation

(comment
  (run* [q]
        (== q true)
        (== q false))
  ;; ()

  (run* [q]
        (conde
         [(== q true)]
         [(== q false)]))
  ;; (true false)

  (run* [q]
        (conso 'a q '(a)))
  ;; (())

  (run* [q]
        (conso q () '(a)))
  ;; (a)

  (run* [q]
        (conso 'a () q))
  ;; ((a))

  (run* [q]
        (fresh [d]
               (conso 'a d q)))

  ;; ((a . _.0))
  ;; one list with head a and anything as a rest
  ;; (same notation for pair as in lisp)

  (run* [q]
        (fresh [d]
               (conso 'a d q)
               (== d ())))
  ;; ((a))

  (run* [q]
        (fresh [x y]
               (== q [x y])))
  ;; ([_.0 _.1])

  (run* [q]
        (fresh [x y]
               (== q [x y])
               (== x y)))
  ;; ([_.0 _.0])

  (run* [q]
        (a/< 1 2))
  ;; (_.0)
  ;; i supppose this is so because it's not only natural
  ;; numbers so there exists an infinity of numbers between 1 and 2

  (run* [q]
        (a/< 1 q))
  ;; kabooommmm
  ;; java.lang.ClassCastException: clojure.core.logic.LVar cannot be cast to java.lang.Number

  ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;; Dinesman's multiple-dwelling problem

  ;; Baker, Cooper, Fletcher, Miller, and Smith live on different floors of
  ;; an apartment house that contains only five floors.
  ;; - Baker does not live on the top floor.
  ;; - Cooper does not live on the bottom floor.
  ;; - Fletcher does not live on either the top or the bottom floor.
  ;; - Miller lives on a higher floor than does Cooper.
  ;; - Smith does not live on a floor adjacent to Fletcher's.
  ;; - Fletcher does not live on a floor adjacent to Cooper's.
  ;; Where does everyone live?

  ;; (1 2 3 4 5)                          floors
  ;; (above adjacent)                     relation
  ;; (baker cooper fletcher miller smith) people

  ;; (run* [q]
  ;;       (!= :baker 5)
  ;;       (!= :cooper 1)
  ;;       (!= :fletcher 1 5)
  ;;       (a/< :cooper :miller)
  ;;       (!= :smith :fletcher)
  ;;       (!= :fletcher :cooper)
  ;;       (== q [:baker :cooper :fletcher :miller :smith]))
  )
