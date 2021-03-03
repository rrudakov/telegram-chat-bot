(ns telegram-chat-bot.commands.coloring
  (:require [clj-http.client :as client]
            [telegram-chat-bot.config :as conf]
            [telegram-chat-bot.commands.utils :as utils]
            [clojure.string :as str]
            [telegram-chat-bot.bot.api :as bot]))

(defn get-random-picture
  [config query]
  (let [url     (conf/google-search-url config)
        api-key (conf/google-api-key config)
        cx      (conf/google-cx config)]
    (->> (client/get url
                     {:accept :json
                      :as     :json
                      :query-params
                      {"key" api-key
                       "cx"  cx
                       "q"   (str "раскраска " query)}})
        (:body)
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
