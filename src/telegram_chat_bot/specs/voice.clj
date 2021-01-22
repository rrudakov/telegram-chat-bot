(ns telegram-chat-bot.specs.voice
  (:require [clojure.spec.alpha :as s]
            [telegram-chat-bot.specs.common :as common]))

(s/def ::voice
  (s/keys :req-un [::common/file_id
                   ::common/file_unique_id
                   ::common/duration]
          :opt-un [::common/mime_type
                   ::common/file_size]))
