(ns telegram-chat-bot.specs.poll-answer
  (:require [clojure.spec.alpha :as s]
            [telegram-chat-bot.specs.user :as user]))

(s/def ::poll_id string?)

(s/def ::option_ids
  (s/coll-of int? :kind vector? :into []))

(s/def ::poll_answer
  (s/keys :req-un [::poll_id
                   ::user/user
                   ::option_ids]))
