(ns scratch.rss
  (:use     [clojure
             [pprint    :only [pprint pp]]
             [inspector :only [inspect-tree inspect inspect-table]]])
  (:require [clj-http.client :as c]
            [clojure.xml     :as x]
            [clojure.zip     :as z]
            [clojure.string  :as s]))

(defn map-from
  "Compute a hash-map from an array"
  [arr]
  (zipmap arr (range (count arr))))

(def ^{:doc "Explicit key mapping over range for the :item entry."}
  tags (map-from [:title :link :category :pub-date :description :enclosure :comments :guid :torrent]))

(def ^{:doc "Explicit key mapping over range for the map on the torrent index."}
  tags-torrent (map-from [:file-name :content-length :info-hash :magnet-uri]))

(defn itemize
  "Given a parsed feed, return the list of :item."
  [feed]
  (for [x (xml-seq feed) :when (= (:tag x) :item)] x))

(comment
  (def items (itemize (x/parse "http://www.ezrss.it/feed/")))
  (def torrent-list (mapcat #(get-in % [:content (:title tags) :content]) items)))

(defn- magnet-links
  "Given a list of items, retrieve the magnet-links"
  [items]
  (mapcat #(get-in %
                   [:content (:torrent tags)
                    :content (:magnet-uri tags-torrent)
                    :content])
          items))

(comment
  (def mls (magnet-links items)))

(defn- possible-combi
  "Given a title, try to create some naming combinations."
  [title]
  (-> (for [x ["." "-" "_"]] (s/replace title #"\s" x))
      (conj title)
      set))

(comment
  (possible-combi "have you met ted")
  (possible-combi "dude"))

(defn filters
  "Given a feed and a desired list of entries, filter them"
  [filt feed-parsed]
  (->> filt
       (mapcat possible-combi)
       (map #(re-pattern (str "(?i)" %)))
       (mapcat (fn [pattern] (filter #(re-find pattern %) feed-parsed)))
       set))

(comment
  (def links-to-test ["lskadfjalksdjflks.retxed.skfajsdlkfj"
                      "clumsy.skfajsdlkfj"
                      "aslkdfsRETXEDlakdfjsd"
                      "some-useless-entry"
                      "another-useless"
                      "*****have you met ted#######"
                      "*****some-dude_i like#######"
                      "####flkdsjfl;sa a';sdf;sdaf#@%^"
                      "&()_*( %$%^T.,asdjflksdjflks"])

  (def white-list ["some-dude-i-like"
                   "clumsy"
                   "have you met ted"
                   "crapy theory"
                   "retxed"])

  (filters white-list links-to-test))

(defn magnet-links-from-url
  [url filt]
  (->> url
       x/parse
       itemize
       magnet-links
       (filters filt)))

(comment
  ;; find all magnet links regarding the criminal minds and haven tv shows
  (magnet-links-from-url "http://www.ezrss.it/feed/" ["criminal minds" "haven"])
  (magnet-links-from-url "http://www.ezrss.it/search/index.php?simple&show_name=haven&mode=rss" ["haven"]))

(defn compute-rss-links
  "Compute the ezrss.it rss flux"
  [tv-shows]
  (for [tvs tv-shows]
    (str "http://www.ezrss.it/search/index.php?simple&show_name=" (s/replace tvs #"\s" "+") "&mode=rss")))

(comment
  (compute-rss-links ["some" "tv shows" "i like"])

  (def some-mls (let [tv-shows ["big bang theory"
                                "how i met your mother"
                                "dexter"
                                "fringe"
                                "walking dead"
                                "game of thrones"
                                "misfits"]]
                  (mapcat #(magnet-links-from-url % tv-shows) (compute-rss-links tv-shows)))))
