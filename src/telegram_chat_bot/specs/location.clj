(ns telegram-chat-bot.specs.location
  (:require [clojure.spec.alpha :as s]))

(s/def ::longitude float?)

(s/def ::latitude float?)

(s/def ::horizontal_accuracy float?)

(s/def ::live_period int?)

(s/def ::heading int?)

(s/def ::proximity_alert_radius int?)

(s/def ::location
  (s/keys :req-un [::longitude
                   ::lati]
          :opt-un [::horizontal_accuracy
                   ::live_period
                   ::heading
                   ::proximity_alert_radius]))
