(ns telegram-chat-bot.specs.proximity-alert-triggered
  (:require [clojure.spec.alpha :as s]
            [telegram-chat-bot.specs.user :as u]))

(s/def ::distance int?)

(s/def ::proximity_alert_triggered
  (s/keys :req-un [::u/traveler
                   ::u/watcher
                   ::distance]))
