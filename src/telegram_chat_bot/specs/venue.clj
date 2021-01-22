(ns telegram-chat-bot.specs.venue
  (:require [clojure.spec.alpha :as s]
            [telegram-chat-bot.specs.location :as l]
            [telegram-chat-bot.specs.common :as common]))

(s/def ::foursquare_id string?)

(s/def ::foursquare_type string?)

(s/def ::google_place_id string?)

(s/def ::google_place_type string?)

(s/def ::venue
  (s/keys :req-un [::l/location
                   ::common/title
                   ::common/address]
          :opt-un [::foursquare_id
                   ::foursquare_type
                   ::google_place_id
                   ::google_place_type]))
