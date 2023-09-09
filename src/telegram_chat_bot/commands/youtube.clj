(ns telegram-chat-bot.commands.youtube
  (:require
   [clojure.java.io :as io]
   [clojure.java.shell :as shell]
   [clojure.string :as str]
   [cognitect.aws.client.api :as aws]
   [cognitect.aws.credentials :as credentials]
   [telegram-chat-bot.bot.api :as bot]
   [telegram-chat-bot.commands.utils :as utils]
   [telegram-chat-bot.config :as conf]
   [taoensso.timbre :as log]
   [clojure.data.json :as json]))

(defn- extract-best-format-code
  [url]
  (some->> (shell/sh "yt-dlp" "-F" "-S" "quality,res" url)
           (:out)
           (str/split-lines)
           (last)
           (re-find #"^(\d+)\s")
           (second)))

(defn- youtube-dl-get-name
  [output-format url]
  (->> (shell/sh "yt-dlp"
                 "--print" "filename"
                 "-o" output-format
                 url
                 "--restrict-filenames")
       (:out)
       (str/trim)))

(defn- youtube-dl-download
  [url format-code output-format]
  (shell/sh "yt-dlp"
            "-f" format-code
            "-q"
            "-o" output-format
            url
            "--restrict-filenames"))

(defn- youtube-dl-download-mp3
  [url output-format]
  (shell/sh "yt-dlp"
            "-x"
            "--audio-format" "mp3"
            "-q"
            "-o" output-format
            url
            "--restrict-filenames"))

(defn- upload-to-s3
  [config key file-path]
  (let [bucket-name (conf/bucket-name config)
        region      (conf/aws-region config)
        cred        (credentials/basic-credentials-provider (conf/aws-creds config))
        s3          (aws/client {:api                  :s3
                                 :region               region
                                 :credentials-provider cred})]
    (aws/invoke s3 {:op      :PutObject
                    :request {:Bucket bucket-name
                              :Key    key
                              :Body   (io/input-stream (io/file file-path))}})
    (format "https://%s.s3.amazonaws.com/%s" bucket-name key)))

(defn- rm-file
  [file-name]
  (shell/sh "rm" "-f" file-name))

(defn- send-link-to-the-chat
  [token chat-id video-path video-name config]
  (if-let [link (upload-to-s3 config video-name video-path)]
    (let [{:keys [status body]}
          @(bot/send-message token chat-id (str "Готово! " link))]
      (if (< status 400)
        (do
          (log/infof "Video was sent successfully. Deleting the file %s"
                     video-path)
          (rm-file video-path))
        (throw (ex-info "Unable to send a link to the chat"
                        (json/read-str body)))))
    (throw (ex-info "Unable to upload file to S3" {}))))

(defn- send-audio-to-the-chat
  [token chat-id audio-path audio-name]
  (let [{:keys [status body]}
        @(bot/send-audio token chat-id audio-path :title audio-name)]
    (if (< status 400)
      (do
        (log/infof "Audio was sent successfully. Deleting the file %s"
                   audio-path)
        (rm-file audio-path))
      (throw (ex-info "Unable to send audio to the chat"
                      (json/read-str body))))))

(defn- download-youtube-video
  [token chat-id config url]
  (bot/send-message token chat-id "Уже качаю")
  (log/infof "Start downloading video from %s in chat %d" url chat-id)
  (let [output-format          (conf/youtube-dl-output-format config)
        output-folder          (conf/youtube-dl-output-folder config)
        best-format-code       (extract-best-format-code url)
        video-name             (youtube-dl-get-name output-format url)
        video-path             (str/join [output-folder video-name])
        {:keys [exit err out]} (youtube-dl-download url best-format-code video-path)]
    (case exit
      0 (send-link-to-the-chat token chat-id video-path video-name config)
      (do
        (log/errorf "Exit code %d. System error: %s. System out: %s" exit err out)
        (bot/send-message token chat-id "Че то никак :(")))))

(defn- download-youtube-audio
  [token chat-id config url]
  (bot/send-message token chat-id "Уже качаю")
  (log/infof "Start downloading audio from %s in chat %d" url chat-id)
  (let [output-format          (conf/youtube-dl-output-format-mp3 config)
        output-folder          (conf/youtube-dl-output-folder config)
        audio-name             (youtube-dl-get-name output-format url)
        audio-path             (str/join [output-folder audio-name])
        {:keys [exit err out]} (youtube-dl-download-mp3 url audio-path)]
    (case exit
      0 (send-audio-to-the-chat token chat-id audio-path audio-name)
      (do
        (log/errorf "Exit code %d. System error: %s. System out: %s" exit err out)
        (bot/send-message token chat-id "Чё то никак :(")))))

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
