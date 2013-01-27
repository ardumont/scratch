(ns scratch.cv.gravatar
  "My cv as pure data. After all, everything is data!"
  (:require [clojure
             [string :as s]
             [pprint :as p]]
            [clj-http.client :as c]))

(def url "http://en.gravatar.com/ardumont.json")

(defn query
  [method url & [opts]]
  (c/request
   (merge {:method     method
           :url        url
           :accept     :json
           :as         :auto}
          opts)))

(query :get "http://en.gravatar.com/ardumont.json")

{:trace-redirects ["http://en.gravatar.com/ardumont.json"]
 :request-time 333
 :status 200
 :headers {"last-modified" "Sun, 27 Jan 2013 18:37:33 GMT"
           "server" "nginx"
           "content-encoding" "gzip"
           "p3p" "CP=\"CAO PSA\""
           "content-type" "application/json"
           "date" "Sun, 27 Jan 2013 19:03:17 GMT"
           "cache-control" "max-age=900"
           "vary" "Accept-Encoding"
           "expires" "Sun, 27 Jan 2013 19:18:17 GMT"
           "transfer-encoding" "chunked"
           "pragma" "no-cache"
           "connection" "close"}
 :body {:entry [{:name {:givenName "Antoine, Romain"
                        :familyName "Romain"
                        :formatted "Antoine Romain Dumont"}
                 :thumbnailUrl "http://1.gravatar.com/avatar/fa65a26193f2474749b3a5f22472160d"
                 :hash "fa65a26193f2474749b3a5f22472160d"
                 :accounts [{:domain "facebook.com"
                             :display "facebook.com"
                             :url "http://www.facebook.com/profile.php?id=642018541"
                             :username "facebook.com"
                             :verified "true"
                             :shortname "facebook"}
                            {:domain "plus.google.com"
                             :display "plus.google.com"
                             :url "https://plus.google.com/116847346708543486262"
                             :userid "116847346708543486262"
                             :verified "true"
                             :shortname "google"}
                            {:domain "linkedin.com"
                             :display "linkedin.com"
                             :url "http://www.linkedin.com/pub/antoine-romain-dumont/5/158/655"
                             :username "linkedin.com"
                             :verified "true"
                             :shortname "linkedin"}
                            {:domain "twitter.com"
                             :display "@ardumont"
                             :url "http://twitter.com/ardumont"
                             :username "ardumont"
                             :verified "true"
                             :shortname "twitter"}
                            {:domain "adumont.fr"
                             :display "adumont.fr"
                             :url "http://adumont.fr/"
                             :username "adumont.fr"
                             :verified "true"
                             :shortname "wordpress"}
                            {:domain "youtube.com"
                             :display "ToNyX522"
                             :url "http://www.youtube.com/user/ToNyX522"
                             :username "ToNyX522"
                             :verified "true"
                             :shortname "youtube"}]
                 :requestHash "ardumont"
                 :urls [{:value "http://j.mp/dWMPPr" :title "Linkedin"}
                        {:value "http://j.mp/ibIAVM" :title "Viadeo"}
                        {:value "http://github.com/ardumont" :title "My github dashboard"}
                        {:value "http://adumont.fr" :title "tony's blog"}]
                 :photos [{:value "http://1.gravatar.com/avatar/fa65a26193f2474749b3a5f22472160d"
                           :type "thumbnail"}]
                 :displayName "Antoine Romain Dumont"
                 :profileUrl "http://gravatar.com/ardumont"
                 :aboutMe "{:job :developer\n :specifics :geek\n :passion [:free-software :gnu-linux :emacs :clojure :haskell :lisp :simplicity :feedback :repl :tests :git :guitar]}"
                 :preferredUsername "ardumont"
                 :currentLocation "France"
                 :id "18952282"}]}}
