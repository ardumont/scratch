(ns scratch.drive.api
  (:require [clj-http.client   :as c]
            [clojure.string    :as s]))

;; your credentials in the ~/.sdk-drive/config.clj file
;; (def sdk-drive-credentials
;;   {:api-key        ""
;;    :client-id      ""
;;    :client-secret  ""
;;    :redirect-uri   ""})

(load-file (str (System/getProperty "user.home") "/.sdk-drive/config.clj"))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;; authentication

{"response_type"   "code"
 "client_id"       (:client-id sdk-drive-credentials)
 "redirect_uri"    (:redirect-uri sdk-drive-credentials)
 "scope"           "https://www.googleapis.com/auth/userinfo.email+https://www.googleapis.com/auth/userinfo.profile"
 "state"           "/profile"
 "access_type"     "online"
 "approval_prompt" "auto"

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;; api

(defn url
  "The needed prefix url for trello"
  []
  "https://www.googleapis.com/drive/v2")

(defn compute-url
  "Compute url with authentication needed."
  [url path]
  (format "%s%s?key=%s"
          url
          path
          (:api-key sdk-drive-credentials)))

(comment
  (compute-url (url) "/about")
  (compute-url (url) "/files"))

(defn drive-query
  [method path & [opts]]
  (c/request
   (merge {:method     method
           :url        (compute-url (url) path)
           :accept     :json
           :as         :json}
          opts)))

(comment
  (drive-query :get "/about")
  ;; listings files
  (drive-query :get "/files"))
