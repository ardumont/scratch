(ns scratch.jaudiotagger
  (:import [org.jaudiotagger.audio AudioFileIO]
           [org.jaudiotagger.tag FieldKey])
  (:require [clojure.pprint :as p]
            [clojure.java.io :as io]))

;; inspired from: https://gist.github.com/1968570

(defn tags "Metadata about the music itself (artist, album, title, etc...)."
  [file]
  (let [fields (apply conj {} (map (fn [n] [(keyword (. (. n toString) toLowerCase)) n])
                                   (. FieldKey values)))
        tag (. file (getTag))]
    (apply conj {}
           (filter (fn [[name val]] (and val (not (empty? val))))
                   (map (fn [[name val]]
                          [name (seq (map #(. % getContent) (. tag (getFields val))))])
                        fields)))))

(defn audioheader "Metadata about the music file (encoding, etc...)"
  [file]
  (bean (. file (getAudioHeader))))

(defn metadata "Create the map of metadata from the filename."
  [filename]
  (let [file (AudioFileIO/read (io/file filename))]
    {:tags (tags file)
     :audioheader (audioheader file)}))

(defn filename-from-tags "Some utility function that helps extract the name of the file"
  [tags]
  (let [track (-> tags :track first)
        name (-> tags :title first)]
    (when (and track name)
      (format "%s-%s" track name))))

