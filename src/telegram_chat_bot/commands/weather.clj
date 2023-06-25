(ns telegram-chat-bot.commands.weather
  (:require [telegram-chat-bot.config :as conf]
            [org.httpkit.client :as http]
            [clojure.data.json :as json]
            [telegram-chat-bot.commands.utils :as utils]
            [clojure.string :as str]
            [telegram-chat-bot.bot.api :as bot]))


(defn- by-city
  [city days config]
  (let [url      (conf/weather-search-url config)
        api-key  (conf/weather-api-key config)
        response @(http/get url {:query-params
                                 {:key    api-key
                                  :q      city
                                  :days   days
                                  :aqi    "no"
                                  :alerts "no"}
                                 :headers {"Accept" "application/json"}})]
    (if (< (:status response) 400)
      (-> response :body (json/read-str :key-fn keyword))
      (throw (ex-info "Weather API error"
                      response)))))

(defn- format-message
  [{:keys [location current]}]
  (->> [(format "*Here is the current weather in %s:*" (:name location))
        ""
        (get-in current [:condition :text])
        ""
        (format "*Current temperature:* %.1f℃" (:temp_c current))
        (format "*Feels like:* %.1f℃" (:feelslike_c current))
        (format "*Wind:* %.1f km/h" (:wind_kph current))]
       (str/join \newline)))

(defn execute-weather-command
  [config body]
  (let [message (or (:message body) (:edited_message body))
        token   (conf/telegram-bot-api-token config)
        chat-id (get-in message [:chat :id])
        query   (utils/extract-text body)]
    (if (str/blank? query)
      (bot/send-message token chat-id "А в каком городе?")
      (let [response (by-city query 1 config)
            picture  (get-in response [:current :condition :icon])]
        (bot/send-picture token
                          chat-id
                          (str "https:" picture)
                          {:parse_mode "MarkdownV2"
                           :caption    (-> response
                                           format-message
                                           (str/replace "." "\\."))})))))
