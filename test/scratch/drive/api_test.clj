(ns scratch.drive.api-test
  (:use [midje.sweet]
        [scratch.drive.api]))

(future-fact
 (compute-url "url/" "test") => "url/test?key=api-key"
 (provided
  (:api-key sdk-drive-credentials) => "api-key"))
