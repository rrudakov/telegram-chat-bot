(ns telegram-chat-bot.commands.youtube
  (:require
   [amazonica.aws.s3 :as s3]
   [clj-time.core :as t]
   [clojure.java.shell :as shell]
   [clojure.string :as str]
   [telegram-chat-bot.bot.api :as bot]
   [telegram-chat-bot.commands.utils :as utils]
   [telegram-chat-bot.config :as conf]))

(defn- extract-best-format-code
  [url]
  (some->> (shell/sh "youtube-dl" "-F" url)
           (:out)
           (str/split-lines)
           (filter #(str/includes? % "(best)"))
           (last)
           (re-find #"^(\d+)\s")
           (second)))

(defn- extract-audio-format-code
  [url]
  (some->> (shell/sh "youtube-dl" "-F" url)
           (:out)
           (str/split-lines)
           (filterv #(and (str/includes? % "audio only")
                          (str/includes? % "m4a")))
           (last)
           (re-find #"^(\d+)\s")
           (second)))

(defn- youtube-dl-get-name
  [output-format format-code url]
  (->> (shell/sh "youtube-dl"
                 "-f" format-code
                 "--get-filename"
                 "-o" output-format
                 url
                 "--restrict-filenames")
       (:out)
       (str/trim)))

(defn- youtube-dl-download
  [url format-code output-format]
  (:exit (shell/sh "youtube-dl"
                   "-f" format-code
                   "-q"
                   "-o" output-format
                   url
                   "--restrict-filenames")))

(defn- upload-to-s3
  [config key file-path]
  (let [bucket-name (conf/bucket-name config)
        cred        (conf/aws-creds config)]
    (s3/put-object cred
                   :bucket-name bucket-name
                   :key key
                   :file file-path)
    (str (s3/generate-presigned-url cred bucket-name key (-> 6 t/hours t/from-now)))))

(defn- rm-file
  [file-name]
  (shell/sh "rm" "-f" file-name))

(defn- send-link-to-the-chat
  [token chat-id video-path video-name config]
  (let [link (upload-to-s3 config video-name video-path)]
    (bot/send-message token chat-id (str "Готово! " link))
    (rm-file video-path)))

(defn- send-audio-to-the-chat
  [token chat-id audio-path audio-name]
  (bot/send-audio token chat-id audio-path :title audio-name)
  (rm-file audio-path))

(defn- download-youtube-video
  [token chat-id config url]
  (bot/send-message token chat-id "Уже качаю")
  (let [output-format    (conf/youtube-dl-output-format config)
        output-folder    (conf/youtube-dl-output-folder config)
        best-format-code (extract-best-format-code url)
        video-name       (youtube-dl-get-name output-format best-format-code url)
        video-path       (str/join [output-folder video-name])]
    (case (youtube-dl-download url best-format-code video-path)
      0 (send-link-to-the-chat token chat-id video-path video-name config)
      (bot/send-message token chat-id "Че то никак :("))))

(defn- download-youtube-audio
  [token chat-id config url]
  (bot/send-message token chat-id "Уже качаю")
  (let [output-format    (conf/youtube-dl-output-format config)
        output-folder    (conf/youtube-dl-output-folder config)
        best-format-code (extract-audio-format-code url)
        audio-name       (youtube-dl-get-name output-format best-format-code url)
        audio-path       (str/join [output-folder audio-name])]
    (case (youtube-dl-download url best-format-code audio-path)
      0 (send-audio-to-the-chat token chat-id audio-path audio-name)
      (bot/send-message token chat-id "Чё то никак :("))))

(defn execute-download-command
  [config body audio?]
  (let [message (or (:message body) (:edited_message body))
        token   (conf/telegram-bot-api-token config)
        chat-id (get-in message [:chat :id])
        url     (utils/extract-entity body "url")]
    (if url
      (if audio?
        (download-youtube-audio token chat-id config url)
        (download-youtube-video token chat-id config url))
      (bot/send-message token chat-id "А что скачать то надо?"))))
