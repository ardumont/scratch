(ns scratch.cljinaction
  (:use [midje.sweet]))

;; ----------------------- chapter 11 - Scaling through messaging

(defn dispatch-fn
  [dispatch-class [name & body]]
  `(defmethod ~name ~dispatch-class ~@body))

(defmacro details-mo [mo-name dispatch-class & bodies]
  `(do
     ~@(map #(dispatch-fn dispatch-class %) bodies)))

;; ----------------------- chapter 12 - Data processing with clojure

(defn parse-line
  [l]
  (let [tokens (clojure.string/split l #" ")]
    (map #(vector % 1) tokens)))

(fact
  (parse-line "this is a a test") => [["this" 1] ["is" 1] ["a" 1] ["a" 1] ["test" 1]])
