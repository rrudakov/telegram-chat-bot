(ns telegram-chat-bot.specs.photo-size
  (:require [clojure.spec.alpha :as s]
            [telegram-chat-bot.specs.common :as common]))

(s/def ::thumb
  (s/keys :req-un [::common/file_id
                   ::common/file_unique_id
                   ::common/width
                   ::common/height]
          :opt-un [::common/file_size]))

(s/def ::photo
  (s/coll-of ::thumb :kind vector? :into []))

(s/def ::new_chat_photo ::photo)
