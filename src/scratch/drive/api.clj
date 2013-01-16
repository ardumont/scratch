(ns scratch.drive.api
  (:require [clj-http.client   :as c]
            [clojure.string    :as s]
            [oauth.google      :as oauth]))

;; your credentials in the ~/.sdk-drive/config.clj file
;; (def drive-credentials
;;   {:api-key        ""
;;    :client-id      ""
;;    :client-secret  ""
;;    :redirect-uri   ""
;;    :code           ""});; provided by the authorization entry in the browser

(load-file (str (System/getProperty "user.home") "/.sdk-drive/config.clj"))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;; authorization utility functions

(def ^{:doc "The google api urls for authorisation."
       :private true}
  url-api-auth "https://www.googleapis.com/auth")

(defn- url-api-authorization
  "Factor the code to compute the url needed for authorization"
  [s]
  (str url-api-auth s))

;; full list of scopes for the google drive api: https://developers.google.com/drive/scopes
(def ^{:doc "The scope we authorise the application to access."
       :private true}
  scopes
  {:drive            (url-api-authorization "/drive.readonly")})

;; :drive-readonly   (url-api-authorization "/drive.readonly")
;; :email            (url-api-authorization "/userinfo.email")
;; :profile          (url-api-authorization "/userinfo.profile")

(defn- get-scopes
  "Compute the scopes authorization entries"
  []
  (s/join " " (vals scopes)))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;; use case

(comment
  ;; authentication
  (oauth/oauth-authorization-url (:client-id drive-credentials)
                                 (:redirect-uri drive-credentials)
                                 :scope (get-scopes))

  ;; ****************** manual intervention here:
  ;; this needs to be executed to ask for the permission for the application
  ;; the browser will open and ask for permission
  ;; once accepted, you will be prompted with a code
  ;; i added the code in question in the map in the drive-credentials
  (oauth/oauth-authorize (:client-id    drive-credentials)
                         (:redirect-uri drive-credentials)
                         :scope (get-scopes))

  ;; obtain the oauth access from google
  (def oauth-access-token-app
    (oauth/oauth-access-token (:client-id     drive-credentials)
                              (:client-secret drive-credentials)
                              authorization-code
                              (:redirect-uri  drive-credentials))))

(def ^{:doc "Reference the possibles api url"} url-api
  {:drive "https://www.googleapis.com/drive/v2"})

(defn compute-url
  "Compute url with authentication needed."
  [url path]
  (format "%s%s" url path))

(comment
  (compute-url (:drive url-api) "/files"))

(defn api-query
  [method path & [opts]]
  (c/request
   (merge {:method     method
           :url        (compute-url (:drive url-api) path)
           :accept     :json
           :as         :json
           ;; :headers    {"Authorization" (str "Bearer " (oauth-access-token-app :access-token))}}
          opts)))

(comment
  (api-query :get "/files"))
