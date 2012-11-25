(ns scratch.drive.api
  (:require [clj-http.client   :as c]
            [clojure.string    :as s]
            [oauth.google      :as oauth]))

;; your credentials in the ~/.sdk-drive/config.clj file
;; (def sdk-drive-credentials
;;   {:api-key        ""
;;    :client-id      ""
;;    :client-secret  ""
;;    :redirect-uri   ""})

(load-file (str (System/getProperty "user.home") "/.sdk-drive/config.clj"))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;; authentication

(comment
  ;; compute the authorization url
  (oauth/oauth-authorization-url (:client-id sdk-drive-credentials)
                                 (:redirect-uri sdk-drive-credentials))

  (comment

    ;; ****************** manual intervention here:
    ;; this needs to be executed to ask for the permission for the application
    ;; the browser will open and ask for permission
    ;; once accepted, you will be prompted with a code
    ;; i added the code in question in the map in the sdk-drive-credentials
    (oauth/oauth-authorize (:client-id sdk-drive-credentials)
                           (:redirect-uri sdk-drive-credentials))

    ;; obtain the oauth access from google
    (def oauth-access-token-app
      (oauth/oauth-access-token (:client-id     sdk-drive-credentials)
                                (:client-secret sdk-drive-credentials)
                                (:code          sdk-drive-credentials)
                                (:redirect-uri  sdk-drive-credentials)))
    ;; format of the token
    ;;    {:id-token "some-token",
    ;;     :expires-in 3600,
    ;;     :token-type "Bearer",
    ;;     :refresh-token "some-refresh-token",
    ;;     :access-token "some-access-token"}

    (def oauth-client-app (oauth/oauth-client (oauth-access-token-app :access-token)))

    (oauth/user-info oauth-client-app)
    ;; {:gender         "male",
    ;;  :link           "https://plus.google.com/some-id",
    ;;  :name           "Antoine R. Dumont",
    ;;  :given-name     "Antoine R.",
    ;;  :locale         "en",
    ;;  :verified-email true,
    ;;  :family-name    "Dumont",
    ;;  :email          "some-email@some-provider",
    ;;  :id             "some-id",
    ;;  :birthday       "some-birthday",
    ;;  :picture        "some-uri-pointing-to-his-her-picture"}
    ))


(defn url
  "drive api url"
  []
  "https://www.googleapis.com/drive/v2")

(defn compute-url
  "Compute url with authentication needed."
  [url path]
  (format "%s%s" url path))

(defn api-query
  [method path & [opts]]
  (c/request
   (merge {:method     method
           :url        (compute-url (url) path)
           :accept     :json
           :as         :json
           :oauth-token (oauth-access-token-app :access-token)}
          opts)))

(api-query :get "/files")
;; 403 - the query is ok but i do not have permissions to access such content
;; 403 Forbidden
;; The request was a valid request, but the server is refusing to respond to it.[2] Unlike a 401 Unauthorized response, authenticating will make no difference.[2] On servers where authentication is required, this commonly means that the provided credentials were successfully authenticated but that the credentials still do not grant the client permission to access the resource (e.g. a recognized user attempting to access restricted content).
