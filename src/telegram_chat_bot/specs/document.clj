(ns telegram-chat-bot.specs.document
  (:require [clojure.spec.alpha :as s]
            [telegram-chat-bot.specs.common :as common]
            [telegram-chat-bot.specs.photo-size :as ps]))

(s/def ::document
  (s/keys :req-un [::common/file_id
                   ::common/file_unique_id]
          :opt-un [::ps/thumb
                   ::common/file_name
                   ::common/mime_type
                   ::common/file_size]))
