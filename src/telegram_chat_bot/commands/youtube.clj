(ns telegram-chat-bot.commands.youtube
  (:require [clojure.java.shell :as shell]
            [clojure.string :as str]
            [telegram-chat-bot.bot.api :as bot]
            [telegram-chat-bot.commands.utils :as utils]
            [telegram-chat-bot.config :as conf])
  (:import [java.nio.file Files Paths]))

(defn- extract-best-format-code
  [url]
  (some->> (shell/sh "youtube-dl" "-F" url)
           (:out)
           (str/split-lines)
           (filter #(str/includes? % "(best)"))
           (last)
           (re-find #"^(\d+)\s")
           (second)))

(defn- youtube-dl-get-video-name
  [output-format format-code url]
  (->> (shell/sh "youtube-dl"
                 "-f" format-code
                 "--get-filename"
                 "-o" output-format
                 url
                 "--restrict-filenames")
       (:out)
       (str/trim)))

(defn- youtube-dl-download-video
  [url format-code output-format]
  (:exit (shell/sh "youtube-dl"
                   "-f" format-code
                   "-q"
                   "-o" output-format
                   url
                   "--restrict-filenames")))

(defn- rm-file
  [file-name]
  (shell/sh "rm" "-f" file-name))

(defn- get-file-size
  [file-path]
  (-> file-path
      (Paths/get (into-array String []))
      (Files/size)
      (/ 1024 1024)))

(defn- check-file-size-and-send-video
  [token chat-id video-path video-url]
  (if (< (get-file-size video-path) 50)
    (do
      (bot/send-video token chat-id video-path :caption "Готово!")
      (rm-file video-path))
    (bot/send-message token chat-id (str "Готово! " video-url)
                      {:disable_web_page_preview true})))

(defn- download-youtube-video
  [token chat-id config url]
  (bot/send-message token chat-id "Уже качаю")
  (let [output-format    (conf/youtube-dl-output-format config)
        output-folder    (conf/youtube-dl-output-folder config)
        base-url         (conf/youtube-dl-base-url config)
        best-format-code (extract-best-format-code url)
        video-name       (youtube-dl-get-video-name output-format best-format-code url)
        video-path       (str/join [output-folder video-name])
        video-url        (str/join [base-url video-name])]
    (case (youtube-dl-download-video url best-format-code video-path)
      0 (check-file-size-and-send-video token chat-id video-path video-url)
      (bot/send-message token chat-id "Че то никак :("))))

(defn execute-download-command
  [config body]
  (let [message (or (:message body) (:edited_message body))
        token   (conf/telegram-bot-api-token config)
        chat-id (get-in message [:chat :id])
        url     (utils/extract-entity body "url")]
    (if url
      (download-youtube-video token chat-id config url)
      (bot/send-message token chat-id "А что скачать то надо?"))))
