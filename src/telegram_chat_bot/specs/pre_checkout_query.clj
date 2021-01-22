(ns telegram-chat-bot.specs.pre-checkout-query
  (:require [clojure.spec.alpha :as s]
            [telegram-chat-bot.specs.user :as user]
            [telegram-chat-bot.specs.common :as common]
            [telegram-chat-bot.specs.order-info :as oi]))

(s/def ::id string?)

(s/def ::pre_checkout_query
  (s/keys :req-un [::id
                   ::user/from
                   ::common/currency
                   ::common/total_amount
                   ::common/invoice_payload
                   ::common/shipping_option_id
                   ::oi/order_info]))
