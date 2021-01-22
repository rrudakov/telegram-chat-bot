(ns telegram-chat-bot.specs.dice
  (:require [clojure.spec.alpha :as s]
            [telegram-chat-bot.specs.common :as common]))

(s/def ::value int?)

(s/def ::dice (s/keys :req-un [::common/emoji ::value]))
