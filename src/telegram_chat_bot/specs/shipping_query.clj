(ns telegram-chat-bot.specs.shipping-query
  (:require [clojure.spec.alpha :as s]
            [telegram-chat-bot.specs.user :as user]
            [telegram-chat-bot.specs.shipping-address :as sa]
            [telegram-chat-bot.specs.common :as common]))

(s/def ::id string?)

(s/def ::shipping_query
  (s/keys :req-un [::id
                   ::user/from
                   ::common/invoice_payload
                   ::sa/shipping_address]))
