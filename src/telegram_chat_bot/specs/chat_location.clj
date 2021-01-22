(ns telegram-chat-bot.specs.chat-location
  (:require [clojure.spec.alpha :as s]
            [telegram-chat-bot.specs.location :as l]
            [telegram-chat-bot.specs.common :as common]))

(s/def ::location
  (s/keys :req-un [::l/location ::common/address]))
