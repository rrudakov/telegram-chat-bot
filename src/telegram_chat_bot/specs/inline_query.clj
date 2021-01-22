(ns telegram-chat-bot.specs.inline-query
  (:require [clojure.spec.alpha :as s]
            [telegram-chat-bot.specs.user :as user]
            [telegram-chat-bot.specs.location :as location]))

(s/def ::id string?)

(s/def ::query string?)

(s/def ::offset string?)

(s/def ::inline_query
  (s/keys :req-un [::id
                   ::user/from
                   ::query
                   ::offset]
          :opt-un [::location/location]))
