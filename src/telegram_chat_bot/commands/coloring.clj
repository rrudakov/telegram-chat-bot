(ns telegram-chat-bot.commands.coloring
  (:require
   [clojure.data.json :as json]
   [clojure.string :as str]
   [org.httpkit.client :as http]
   [telegram-chat-bot.bot.api :as bot]
   [telegram-chat-bot.commands.utils :as utils]
   [telegram-chat-bot.config :as conf]))

(defn- <-json
  [body]
  (json/read-str body :key-fn keyword))

(defn get-random-picture
  [config query]
  (let [url     (conf/google-search-url config)
        api-key (conf/google-api-key config)
        cx      (conf/google-cx config)]
    (->> (http/get url {:query-params
                        {"key" api-key
                         "cx"  cx
                         "q"   (str "раскраска " query)}
                        :headers {"Accept" "application/json"}})
         (deref)
         (:body)
         (<-json)
         (:items)
         (mapv :pagemap)
         (mapv :cse_image)
         (flatten)
         (mapv :src)
         (rand-nth))))

(defn execute-coloring-comand
  [config body]
  (let [message (or (:message body) (:edited_message body))
        token   (conf/telegram-bot-api-token config)
        chat-id (get-in message [:chat :id])
        query   (utils/extract-text body)]
    (if (str/blank? query)
      (bot/send-message token chat-id "А какая нужна раскраска?")
      (let [img (get-random-picture config query)]
        (if-not (nil? img)
          (bot/send-picture token chat-id img {:caption "Пожалуйста!"})
          (bot/send-message token chat-id "Ничего не нашел :("))))))
