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

(defn youtube-dl-base-url
  [config]
  (get-in config [:app :youtube :base-url]))
