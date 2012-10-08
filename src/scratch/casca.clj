(ns scratch.casca
  (:use cascalog.playground))

(bootstrap)

(?<- (stdout) [?person] (age ?person 25))


