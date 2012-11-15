(ns scratch.graph.genealogy
  (:refer-clojure :exclude [==])
  (:use [clojure.core.logic]))

(defrel male m)
(facts male '[[antoine]
              [marc]
              [theo]
              [robert-charles]
              [robert]
              [rene]
              [michel]
              [charles-louis]
              [louis]
              [claude]
              [marius]
              [cesar]
              [abel]
              [arnaud]
              [xavier]])

(comment
  (run* [q]
        (male q)))

(defrel female f)
(facts female '[[christelle]
                [chloe]
                [muguette]
                [laurence]
                [louise]
                [blanche]
                [jeanne]
                [marie-paule]])

(comment
  (run* [q]
        (female q)))

(defrel father f c)
(facts father '[[antoine chloe]
                [antoine theo]
                [marc antoine]
                [robert-charles robert]
                [robert marc]
                [rene laurence]
                [rene michel]
                [rene marie-paule]
                [charles-louis rene]
                [claude christelle]
                [claude arnaud]
                [claude xavier]
                [louis claude]
                [marius muguette]
                [cesar marthe]
                [abel jeanne]])

(comment
  ;; give me all the relationships father/child
  (run* [q]
        (fresh [f c]
               (father f c)
               (== q [f c])))

  ;; laurence's father?
  (run* [q]
        (father q 'laurence))

  ;; claude's child?
  (run* [q]
        (father 'claude q)))

(defrel mother m c)
(facts mother '[[christelle chloe]
                [christelle theo]
                [laurence antoine]
                [jeanne laurence]
                [jeanne michel]
                [jeanne marie-paule]
                [marthe marc]
                [louise rene]
                [muguette christelle]
                [muguette arnaud]
                [muguette xavier]
                [blanche claude]
                [madeleine muguette]
                [elise marthe]
                [adele robert]
                [marie jeanne]])

(comment
  ;; give me all the mother/child relationships
  (run* [q]
        (fresh [m c]
               (mother m c)
               (== q [m c]))))

(defn mothers
  "Compute all the mothers relationships"
  []
  (run* [q]
        (fresh [m c]
               (mother m c)
               (== q [m c]))))

(comment
  (mothers)

  ;; give me all the parents relationships
  (run* [q]
        (fresh [p c]
               (conde
                ((mother p c))
                ((father p c)))
               (== q [p c]))))

;; give me all the parents
(defn parent
  [p c]
  (conde
   ((father p c))
   ((mother p c))))

(comment
  ;; the same query as before but with a function
  (run* [q]
        (fresh [p c]
               (parent p c)
               (== q [p c]))))

(defn parents
  "Computes all the parents relationships."
  []
  (run* [q]
        (fresh [p c]
               (parent p c)
               (== q [p c]))))

;; compute the ancestors
(defn ancestorso [a c]
  (fresh [x]
         (conde
          ((parent a c))
          ((parent x c)
           (ancestorso a x)))))

(comment
  ;; compute all the ancestors/children pairs
  (run* [q]
        (fresh [a c]
               (ancestorso a c)
               (== q [a c]))))

(comment
  ;; compute my ancestors
  (run* [q]
        (ancestorso q 'antoine))
  ;; the ancestors of my children
  (run* [q]
        (ancestorso q 'chloe)
        (ancestorso q 'theo)))

(comment
  ;; compute all of louis' (my maternal great grand father) descendants
  (run* [q]
        (ancestorso 'louis q))
  ;; compute all of robert-charles (my paternal great grand father) descendants
  (run* [q]
        (ancestorso 'robert-charles q)))

(defn descendants
  "compute the descendants of p."
  [p]
  (run* [q]
        (ancestorso p q)))

(comment
  (descendants 'robert-charles))

(defn ancestors
  [p]
  (run* [q]
        (ancestorso q p)))

(comment
  (ancestors 'theo))

(comment ;; compute the relations [wife husband] (children list)
  (reduce (fn [m [k v]] (assoc m k (map last v)))
          {}
          (group-by (fn [[m f c]] [m f]) (run* [q]
                                              (fresh [m f c]
                                                     (mother m c)
                                                     (father f c)
                                                     (== q [m f c])))))

  {[blanche louis] (claude)
   [marie abel] (jeanne)
   [elise cesar] (marthe)
   [madeleine marius] (muguette)
   [louise charles-louis] (rene)
   [laurence marc] (antoine)
   [christelle antoine] (theo chloe)
   [adele robert-charles] (robert)
   [jeanne rene] (marie-paule michel laurence)
   [marthe robert] (marc)
   [muguette claude] (xavier arnaud christelle)})

(comment
  ;; give me all christelle's bretherins
  (set (run* [q]
             (fresh [p b]
                    (parent p 'christelle)
                    (parent p b)
                    (!= q 'christelle)
                    (== q b))))

  ;; all christelle's brothers
  (set (run* [q]
             (fresh [p b]
                    (parent p 'christelle)
                    (parent p b)
                    (male b)
                    (!= q 'christelle)
                    (== q b)))))

(defn brothers "Give me s's brothers"
  [s]
  (set (run* [q]
           (fresh [p b]
                  (parent p s)
                  (parent p b)
                  (male b)
                  (!= q s)
                  (== q b)))))

(comment
  (brothers 'christelle)
  (brothers 'chloe)

  ;; all christelle's sisters
  (set (run* [q]
             (fresh [p b]
                    (parent p 'christelle)
                    (parent p b)
                    (female b)
                    (!= q 'christelle)
                    (== q b)))))

(defn sisters "Give me s's sisters"
  [s]
  (set (run* [q]
           (fresh [p b]
                  (parent p s)
                  (parent p b)
                  (female b)
                  (!= q s)
                  (== q b)))))

(comment
  (sisters 'christelle)
  (sisters 'theo))
