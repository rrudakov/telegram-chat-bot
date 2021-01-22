(ns telegram-chat-bot.specs.mask-position
  (:require [clojure.spec.alpha :as s]))

(s/def ::point string?)

(s/def ::x_shift float?)

(s/def ::y_shift float?)

(s/def ::scale float?)

(s/def ::mask_position
  (s/keys :req-un [::point ::x_shift ::y_shift ::scale]))
