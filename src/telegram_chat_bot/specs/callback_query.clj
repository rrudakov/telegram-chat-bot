(ns telegram-chat-bot.specs.callback-query
  (:require [clojure.spec.alpha :as s]
            [telegram-chat-bot.specs.user :as user]
            [telegram-chat-bot.specs.message :as message]))

(s/def ::id string?)

(s/def ::inline_message_id string?)

(s/def ::chat_instance string?)

(s/def ::data string?)

(s/def ::game_short_name string?)

(s/def ::callback_query
  (s/keys :req-un [::id
                   ::user/from]
          :opt-un [::message/message
                   ::inline_message_id
                   ::chat_instance
                   ::data
                   ::game_short_name]))
