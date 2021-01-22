(ns telegram-chat-bot.specs.order-info
  (:require [clojure.spec.alpha :as s]
            [telegram-chat-bot.specs.shipping-address :as sa]
            [telegram-chat-bot.specs.common :as common]))

(s/def ::name string?)

(s/def ::order_info
  (s/keys :opt-un [::name
                   ::common/phone_number
                   ::common/email
                   ::sa/shipping_address]))
