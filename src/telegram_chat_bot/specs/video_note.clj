(ns telegram-chat-bot.specs.video-note
  (:require [clojure.spec.alpha :as s]
            [telegram-chat-bot.specs.common :as common]
            [telegram-chat-bot.specs.photo-size :as ps]))

(s/def ::length int?)

(s/def ::video_note
  (s/keys :req-un [::common/file_id
                   ::common/file_unique_id
                   ::length
                   ::common/duration]
          :opt-un [::ps/thumb
                   ::common/file_size]))
