(ns telegram-chat-bot.specs.audio
  (:require [clojure.spec.alpha :as s]
            [telegram-chat-bot.specs.common :as common]
            [telegram-chat-bot.specs.photo-size :as ps]))

(s/def ::performer string?)

(s/def ::audio
  (s/keys :req-un [::common/file_id
                   ::common/file_unique_id
                   ::common/duration]
          :opt-un [::performer
                   ::common/title
                   ::common/file_name
                   ::common/mime_type
                   ::common/file_size
                   ::ps/thumb]))
