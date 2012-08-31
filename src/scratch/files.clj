(ns scratch.files
  (:use [midje.sweet])
  (:require [clojure.java.io :as io]))


;; opens a file and read it, return the string containing the content of the file
#_ (slurp "/home/tony/.bashrc")

;; read a file line by line
(defn read-file-line-by-line "Just some code to print line by line a file."
  [path-to-file]
  (with-open [r (io/reader path-to-file)]
    (doseq [l (line-seq r)]
      (print l))))

#_ (read-file-line-by-line "/home/tony/.bashrc")

