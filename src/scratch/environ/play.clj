(ns scratch.environ.play
  (:use [environ.core :reload true]
        [midje.sweet :only [fact]]))

(fact
 (env :some-key)       => "wouhou"
 (env :some-other-key) => "it works!!!")
