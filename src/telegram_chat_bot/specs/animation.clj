(ns telegram-chat-bot.specs.animation
  (:require [clojure.spec.alpha :as s]
            [telegram-chat-bot.specs.common :as common]
            [telegram-chat-bot.specs.photo-size :as ps]))

(s/def ::animation
  (s/keys :req-un [::common/file_id
                   ::common/file_unique_id
                   ::common/width
                   ::common/height
                   ::common/duration]
          :opt-un [::ps/thumb
                   ::common/file_name
                   ::common/mime_type]))
