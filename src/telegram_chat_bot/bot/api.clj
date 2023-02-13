(ns telegram-chat-bot.bot.api
  (:require
   [clojure.data.json :as json]
   [clojure.java.io :as io]
   [org.httpkit.client :as http]))

(def ^:private api-url
  "Telegram bot API base URL."
  "https://api.telegram.org/bot")

(defn- url-for-command
  "Format URL with `token` for particular `command`."
  [token command]
  (str api-url token "/" command))

(defn- ->json
  [body]
  (json/write-str body))

(defn- execute-command
  "General interface for all available bot commands."
  [token command body]
  (http/post (url-for-command token command)
             {:body    (->json body)
              :headers {"Content-Type" "application/json"}}))

(defn send-message
  "Send message on behalf of bot."
  ([token chat-id text]
   (send-message token chat-id text {}))
  ([token chat-id text options]
   (execute-command token
                    "sendMessage"
                    (-> options
                        (assoc :chat_id chat-id)
                        (assoc :text text)))))

(defn send-video
  "Send `video` file to chat with `chat-id` on behalf of bot."
  [token chat-id video & {:keys [caption]}]
  (let [file     (io/file video)
        filename (.getName file)]
    (http/post (url-for-command token "sendVideo")
               {:multipart [{:name "chat_id" :content (str chat-id)}
                            {:name "video" :content file :filename filename}
                            {:name "caption" :content (or caption "")}]})))

(defn send-audio
  "Send `audio` file to chat with `chat-id` on behalf of bot."
  [token chat-id audio & {:keys [caption title]}]
  (let [file           (io/file audio)
        filename       (.getName file)
        multipart-data (cond-> [{:name "chat_id" :content (str chat-id)}
                                {:name "audio" :content file :filename filename}]
                         (some? caption) (conj {:name "caption" :content caption})
                         (some? title)   (conj {:name "title" :content title}))]
    (http/post (url-for-command token "sendAudio")
               {:multipart multipart-data})))

(defn send-picture
  "Send `image` to chat with `chat-id` on behalf of bot."
  ([token chat-id image]
   (send-picture token chat-id image {}))
  ([token chat-id image options]
   (execute-command token "sendPhoto"
                    (-> options
                        (assoc :chat_id (str chat-id))
                        (assoc :photo image)))))
