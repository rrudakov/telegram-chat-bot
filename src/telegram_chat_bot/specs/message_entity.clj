(ns telegram-chat-bot.specs.message-entity
  (:require [clojure.spec.alpha :as s]
            [telegram-chat-bot.specs.user :as u]
            [telegram-chat-bot.specs.common :as common]))

(s/def ::type
  #{"mention"
    "hashtag"
    "cashtag"
    "bot_command"
    "url"
    "email"
    "phone_number"
    "bold"
    "italic"
    "underline"
    "strikethrough"
    "code"
    "pre"
    "text_link"
    "text_mention"})

(s/def ::offset int?)

(s/def ::length int?)

(s/def ::language string?)

(s/def ::entity (s/keys :req-un [::type ::offset ::length]
                        :opt-un [::common/url ::u/user ::language]))

(s/def ::entities (s/coll-of ::entity :kind vector? :into []))

;; Aliases for entities
(s/def ::caption_entities ::entities)
(s/def ::text_entities ::entities)
(s/def ::explanation_entities ::entities)
