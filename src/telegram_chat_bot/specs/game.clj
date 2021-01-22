(ns telegram-chat-bot.specs.game
  (:require [clojure.spec.alpha :as s]
            [telegram-chat-bot.specs.animation :as a]
            [telegram-chat-bot.specs.common :as common]
            [telegram-chat-bot.specs.message-entity :as me]
            [telegram-chat-bot.specs.photo-size :as ps]))

(s/def ::game
  (s/keys :req-un [::common/title
                   ::common/description
                   ::ps/photo]
          :opt-un [::common/text
                   ::me/text_entities
                   ::a/animation]))
