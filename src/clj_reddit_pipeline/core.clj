(ns clj-reddit-pipeline.core
  (:use
    [org.httpkit.server])
  (:require
    [reddit :as r]
    [clojure.string :as string]
    [clojure.data.json :as json]
    [clojure.core.async :as async :refer [go <! >! chan]])
  (:gen-class))

(def ch (chan))

; predicate for filtering the stream
(defn pred
  [item]
  (> (:score item) 2))

(defn uppercase-title
  [item]
  (assoc item
         :title (string/upper-case (:title item))))

(defn send-to-listener
  [item]
  (go (>! ch item)))

(defn make-json
  [item]
  (let [keep-keys [:title :score :body :url :subreddit]
        small-item (into {} (map #(vector % (% item)) keep-keys))]
    (json/write-str small-item)))

; transducers version
(def pipeline
  (comp
    (filter pred)
    (map uppercase-title)
    (map make-json)
    (map send-to-listener)))
(def stream
  (sequence pipeline
            (r/items (r/subreddit-new "all"))))

;;;;;;;;;;;; server stuff
(def ^:dynamic ws nil)

(defn handler [request]
  (with-channel request channel
    (def ws channel)))
;;;;;;;;;;;; end of server stuff


(defn -main
  [& args]
  ; start listener
  (go (while true
        (let [json-msg (<! ch)]
          (if (not (nil? ws))
            (send! ws json-msg)))))
  ; start server
  (run-server handler {:port 8080})
  ; process the stream
  (dorun stream))
