(ns telegram-chat-bot.specs.sticker
  (:require [clojure.spec.alpha :as s]
            [telegram-chat-bot.specs.common :as common]
            [telegram-chat-bot.specs.mask-position :as mp]
            [telegram-chat-bot.specs.photo-size :as ps]))

(s/def ::is_animated boolean?)

(s/def ::set_name string?)

(s/def ::sticker
  (s/keys :req-un [::common/file_id
                   ::common/file_unique_id
                   ::common/width
                   ::common/height
                   ::is_animated]
          :opt-un [::ps/thumb
                   ::common/emoji
                   ::set_name
                   ::mp/mask_position
                   ::common/file_size]))
