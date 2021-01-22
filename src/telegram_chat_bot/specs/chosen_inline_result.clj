(ns telegram-chat-bot.specs.chosen-inline-result
  (:require [clojure.spec.alpha :as s]
            [telegram-chat-bot.specs.user :as user]
            [telegram-chat-bot.specs.location :as location]))

(s/def ::result_id string?)

(s/def ::inline_message_id string?)

(s/def ::query string?)

(s/def ::chosen_inline_result
  (s/keys :req-un [::result_id
                   ::user/from
                   ::query]
          :opt-un [::location/location
                   ::inline_message_id]))
