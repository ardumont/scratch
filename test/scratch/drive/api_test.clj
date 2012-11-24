(ns scratch.drive.api-test
  (:use [midje.sweet :only [fact]]
        [scratch.drive.api]))

(fact
 (compute-url "url/" "test") => "url/test?key=api-key"
 (provided
  (:api-key sdk-drive-credentials) => "api-key"))
