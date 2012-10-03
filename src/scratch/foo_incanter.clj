(ns scratch.foo-incanter
  "Some scratch namespace to play with incanter"
  (:use [incanter core charts stats]))

(-> (sample-normal 1000) (histogram) (view))

(view (function-plot cos -10 10))

(doc function-plot)
(find-doc "function")
