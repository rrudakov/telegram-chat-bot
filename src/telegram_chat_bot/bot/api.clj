(ns telegram-chat-bot.bot.api
  (:require [clj-http.client :as client]
            [muuntaja.core :as m]
            [clojure.java.io :as io]))

(def ^:private api-url
  "Telegram bot API base URL."
  "https://api.telegram.org/bot")

(defn- url-for-command
  "Format URL with `token` for particular `command`."
  [token command]
  (str api-url token "/" command))

(defn- to-json
  [body]
  (->> body
       (m/encode "application/json")
       slurp))

(defn- execute-command
  "General interface for all available bot commands."
  [token command body]
  (client/post (url-for-command token command)
               {:body (to-json body)
                :as :json
                :content-type :json
                :accept :json}))

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
  (client/post (url-for-command token "sendVideo")
               {:multipart [{:name "chat_id" :content (str chat-id)}
                            {:name "video" :content (io/file video)}
                            {:name "caption" :content (or caption "")}]}))

(defn send-picture
  "Send `image` to chat with `chat-id` on behalf of bot."
  ([token chat-id image]
   (send-picture token chat-id image {}))
  ([token chat-id image options]
   (execute-command token "sendPhoto"
                    (-> options
                        (assoc :chat_id (str chat-id))
                        (assoc :photo image)))))
