(ns telegram-chat-bot.specs.encrypted-credentials
  (:require [clojure.spec.alpha :as s]))

(s/def ::data string?)

(s/def ::hash string?)

(s/def ::secret string?)

(s/def ::credentials
  (s/keys :req-un [::data ::hash ::secret]))
