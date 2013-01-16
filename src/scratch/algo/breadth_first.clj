(ns ^{:doc "A namespace to fool around with depth-first walk"}
  scratch.algo.depth-first
  [:use [midje.sweet :only [fact future-fact]] ])

(defn mktree
  "Create a tree representing a journey"
  ([node & children] (cons node children))
  ([leaf] (cons leaf nil)))

(fact "Building a tree"
  (mktree :a
          (mktree :b
                  (mktree :d
                          (mktree :j)
                          (mktree :k)
                          (mktree :l))
                  (mktree :e)
                  (mktree :f))
          (mktree :c
                  (mktree :g)
                  (mktree :h)
                  (mktree :i)))      => '(:a (:b (:d
                                                   (:j)
                                                   (:k)
                                                   (:l))
                                                 (:e)
                                                 (:f))
                                             (:c (:g)
                                                 (:h)
                                                 (:i))))

(defn breadth-first
  "breadth-first"
  [tree]
  (loop [ret [] queue (conj clojure.lang.PersistentQueue/EMPTY tree)]
    (if (seq queue)
      (let [[node & children] (peek queue)]
        (recur (conj ret node) (into (pop queue) children)))
      ret)))

(fact "Breadth first approach"
  (breadth-first '(:a (:b (:d
                           (:j)
                           (:k)
                           (:l))
                          (:e)
                          (:f))
                      (:c (:g)
                          (:h)
                          (:i)))) => '(:a :b :c :d :e :f :g :h :i :j :k :l))
