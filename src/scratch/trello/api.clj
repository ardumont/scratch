(ns scratch.trello.api
  (:require [clj-http.client   :as c]
            [clojure.string    :as s]))

;; your credentials in the ~/.trello/config.clj file
;; (def trello-credentials {:developer-api-key "developer-api-key"
;;                          :secret-oauth-key  "secret-oauth-key"})

(load-file (str (System/getProperty "user.home") "/.trello/config.clj"))

(defn url
  "The needed prefix url for trello"
  []
  "https://api.trello.com/1")

(defn compute-url
  "Compute url with authentication needed."
  [url path]
  (format "%s%s?key=%s&token=%s"
          url
          path
          (:developer-api-key trello-credentials)
          (:secret-token trello-credentials)))

(comment
  (compute-url (url) "/members/ardumont"))

(defn api-query
  [method path & [opts]]
  (c/request
   (merge {:method     method
           :url        (compute-url (url) path)
           :accept     :json
           :as         :json}
          opts)))

(comment
  ;; reading public data
  (api-query :get "/members/ardumont")
  (api-query :get "/boards/4d5ea62fd76aa1136000000c")
  (api-query :get "/organizations/fogcreek")

  ;; reading private data needs a token entry in the url
  (api-query :get "/members/me/boards"))
