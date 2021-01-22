(ns telegram-chat-bot.specs.successful-payment
  (:require [clojure.spec.alpha :as s]
            [telegram-chat-bot.specs.common :as common]
            [telegram-chat-bot.specs.order-info :as oi]))

(s/def ::telegram_payment_charge_id string?)

(s/def ::provider_payment_charge_id string?)

(s/def ::successful_payment
  (s/keys :req-un [::common/currency
                   ::common/total_amount
                   ::common/invoice_payload
                   ::telegram_payment_charge_id
                   ::provider_payment_charge_id]
          :opt-un [::common/shipping_option_id
                   ::oi/order_info]))
