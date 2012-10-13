(ns scratch.rss
  (:use     [clojure
             [pprint    :only [pprint pp]]
             [inspector :only [inspect-tree inspect inspect-table]]])
  (:require [clj-http.client :as c]
            [clojure.xml     :as x]
            [clojure.zip     :as z]
            [clojure.string  :as s]))

(def tags (zipmap [:title
                   :link
                   :category
                   :pub-date
                   :description
                   :enclosure
                   :comments
                   :guid
                   :torrent]
                  (range 10)))

(def tags-torrent (zipmap [:file-name
                           :content-length
                           :info-hash
                           :magnet-uri]
                          (range 5)))

(def feed "http://www.ezrss.it/feed/")

(def feed-parsed (parse feed))

(def items (for [x (xml-seq feed-parsed) :when (= (:tag x) :item)] x))

(comment
  (def root (z/xml-zip feed-parsed))

  (def torrent-list (mapcat #(get-in % [:content (:title tags) :content]) items)))

(def magnet-links
  (mapcat #(get-in %
                   [:content (:torrent tags)
                    :content (:magnet-uri tags-torrent)
                    :content])
          items))

(filter #(re-find #"(?i)criminal" %) magnet-links)

(defn possible-combi
  "Given a title, try to create some naming combinations."
  [title]
  (-> (map #(s/replace title #"\s" %) ["." "-" "_"])
      (conj title)
      set))

(possible-combi "how i met your mother")
(possible-combi "dexter")

(def white-list ["some-dude-i-like"
                 "clumsy"
                 "have you met ted"
                 "crapy theory"
                 "retxed"])

(def all-white-list (mapcat possible-combi white-list))

(def magnet-links2
  (-> magnet-links
      (conj "lskadfjalksdjflks.retxed.skfajsdlkfj")
      (conj "clumsy.skfajsdlkfj")
      (conj "*****have you met ted#######")
      (conj "*****some-dude_i like#######")))

;; filter the magnet links according to the white list
(set
 (mapcat
  (fn [pattern] (filter #(re-find pattern %) magnet-links2))
  (map #(re-pattern (str "(?i)" %)) all-white-list)))






