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

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;; setup

(def url {:api          "https://www.googleapis.com/drive/v2"
          :authenticate "https://accounts.google.com/o/oauth2/auth"})

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;; utils

(defn- url-encode
  "Interface to url encoding (to be able to change the implementation if need be."
  [s]
  (java.net.URLEncoder/encode s))

(defn- complete-url
  "Complete the initial url with the needed remaining ones."
  [partial-url & other-params]
  (apply conj partial-url other-params))

(defn- key-pair-url-encode
  "Url encode the key value pair and join them with the '='."
  [[k v :as kv]]
  (s/join \= (map url-encode kv)))

(defn- http-encode
  [params]
  (s/join \& (map key-pair-url-encode params)))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;; authentication

;; Forming the url needed for the authentication
(defn- authentication-parameters
  []
  {"response_type"   "code"
   "client_id"       (:client-id sdk-drive-credentials)
   "redirect_uri"    (:redirect-uri sdk-drive-credentials)
   "scope"           "https://www.googleapis.com/auth/userinfo.email+https://www.googleapis.com/auth/userinfo.profile"
   "state"           "/profile"
   "access_type"     "online"
   "approval_prompt" "auto"})

(http-encode (authentication-parameters))

;; Handling the response


;; Calling the api

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;; api

(defn compute-url
  "Compute url with authentication needed."
  ([url path]
     (format "%s%s?key=%s"
             url
             path
             (:api-key sdk-drive-credentials)))
  ([url path params]
     (let [prefix (compute-url url path)]
       (if-let [suffix (http-encode params)]
         (str prefix \& suffix)
         prefix))))

(comment
  (compute-url (:api url) "/about")
  (compute-url (:api url) "/about" nil)
  (compute-url (:api url) "/files")
  (compute-url (:authenticate url) "/about" (authentication-parameters)))

(defn auth-drive-query
  "Authentication query"
  [method path & [opts]]
  (c/request
   (merge {:method     method
           :url        (compute-url (:authenticate url) path (authentication-parameters))
           :accept     :json
           :as         :json}
          opts)))

(comment
  (auth-drive-query :get "/about")
  ;; listings files
  (auth-drive-query :get "/files"))

(defn drive-query
  "Api drive query"
  [method path & [opts]]
  (c/request
   (merge {:method     method
           :url        (compute-url (:api url) path)
           :accept     :json
           :as         :json}
          opts)))

(comment
  (drive-query :get "/about")
  ;; listings files
  (drive-query :get "/files"))
