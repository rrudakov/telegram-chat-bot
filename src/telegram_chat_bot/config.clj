(ns telegram-chat-bot.config
  (:require [aero.core :as aero]
            [clojure.java.io :as io]))

(def config
  "Read application configuration from resources."
  (aero/read-config (io/resource "config/config.edn")))

(defn telegram-bot-api-token
  [config]
  (get-in config [:app :telegram :bot-api-key]))

(defn youtube-dl-output-format
  [config]
  (get-in config [:app :youtube :output-format]))

(defn youtube-dl-output-folder
  [config]
  (get-in config [:app :youtube :output-folder]))

(defn bucket-name
  [config]
  (get-in config [:app :aws :s3-bucket-name]))

(defn aws-creds
  [config]
  (let [aws (get-in config [:app :aws])]
    {:access-key (:s3-access-key aws)
     :secret-key (:s3-secret-key aws)
     :endpoint   (:s3-endpoint aws)}))

(defn google-api-key
  [config]
  (get-in config [:app :coloring :api-key]))

(defn google-cx
  [config]
  (get-in config [:app :coloring :cx]))

(defn google-search-url
  [config]
  (get-in config [:app :coloring :search-url]))
