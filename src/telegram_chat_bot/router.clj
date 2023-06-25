(ns telegram-chat-bot.router
  (:require
   [muuntaja.core :as m]
   [reitit.coercion.spec :as rcs]
   [reitit.ring :as ring]
   [reitit.ring.coercion :as rrc]
   [reitit.ring.middleware.exception :as exception]
   [reitit.ring.middleware.muuntaja :as muuntaja]
   [telegram-chat-bot.bot.api :as bot]
   [telegram-chat-bot.commands.coloring :as clr]
   [telegram-chat-bot.commands.utils :as utils]
   [telegram-chat-bot.commands.youtube :as yt]
   [telegram-chat-bot.config :as conf]
   [telegram-chat-bot.specs.update :as specs]
   [taoensso.timbre :as log]
   [telegram-chat-bot.commands.weather :as weather]))

(defn unknown-action
  [config body command]
  (let [message (or (:message body) (:edited_message body))
        chat-id (get-in message [:chat :id])
        text    (str "Извините, я пока не умею выполнять команду " command)]
    (bot/send-message (conf/telegram-bot-api-token config) chat-id text)))

(defn keep-conversation
  [config body]
  (let [message (or (:message body) (:edited_message body))
        chat-id (get-in message [:chat :id])]
    (bot/send-message (conf/telegram-bot-api-token config) chat-id "Не понимаю!")))

(defn execute-task
  [config body]
  (if-let [command (utils/extract-entity body "bot_command")]
    (case command
      "/video"    (yt/execute-download-command config body false)
      "/audio"    (yt/execute-download-command config body true)
      "/coloring" (clr/execute-coloring-comand config body)
      "/weather"  (weather/execute-weather-command config body)
      (unknown-action config body command))
    (keep-conversation config body)))

(defn handler
  [{:keys [parameters app-config]}]
  (future
    (try
      (execute-task app-config (:body parameters))
      (catch Exception e
        (log/error e (.getMessage e)))))
  {:status 200, :body "True"})

(defn router
  [{:keys [config]}]
  (ring/router
    ["/bot" {:post {:parameters {:body ::specs/update}
                    :handler    handler}}]
    {:data {:app-config config
            :muuntaja   m/instance
            :coercion   rcs/coercion
            :middleware [conf/wrap-app-config
                         exception/exception-middleware
                         muuntaja/format-middleware
                         rrc/coerce-exceptions-middleware
                         rrc/coerce-request-middleware
                         rrc/coerce-response-middleware]}}))
