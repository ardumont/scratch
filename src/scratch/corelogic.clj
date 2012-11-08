(ns scratch.corelogic
  (:refer-clojure :exclude [==])
  (:use [clojure.core.logic]
        [dorothy.core]))

(comment ;; first query
  (run* [q]
        (== true q)))
;; (true)

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

(comment
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

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;; dorothy (graphviz generation) play

;; all the nodes
(->> (run* [q]
           (fresh [f c]
                  (parent f c)
                  (== q [f c])))
     (mapcat identity)
     (map keyword)
     set
     vec)

;; all the relations parent/child
(->> (run* [q]
           (fresh [f c]
                  (parent f c)
                  (== q [f c])))
     (map (fn [v] (map keyword v))))

(-> (concat
     ;; shape
;; [[:node {:shape :box}]]
     ;; all the nodes
     (->> (run* [q]
                (fresh [f c]
                       (parent f c)
                       (== q [f c])))
          (mapcat identity)
          (map keyword)
          set
          vec)
     (->> (run* [q]
                (fresh [f c]
                       (parent f c)
                       (== q [f c])))
          (map (fn [v] (map keyword v)))
          (map vec)))
    doall
    vec
    digraph
    dot
    ;; render a svg format
    ;;    (render {:format :svg})
    ;;    (render {:format :png})
    ;; persist the file
    ;;    (save! "out.png" {:format :png})
    (show! {:layout :neato}))
