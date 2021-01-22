(ns telegram-chat-bot.specs.shipping-address
  (:require [clojure.spec.alpha :as s]))

(s/def ::country_code string?)

(s/def ::state string?)

(s/def ::city string?)

(s/def ::street_line1 string?)

(s/def ::street_line2 string?)

(s/def ::post_code string?)

(s/def ::shipping_address
  (s/keys :req-un [::country_code
                   ::state
                   ::city
                   ::street_line1
                   ::street_line2
                   ::post_code]))
