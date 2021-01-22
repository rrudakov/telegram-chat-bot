(ns telegram-chat-bot.specs.update
  (:require [clojure.spec.alpha :as s]
            [telegram-chat-bot.specs.callback-query :as cq]
            [telegram-chat-bot.specs.chosen-inline-result :as cir]
            [telegram-chat-bot.specs.inline-query :as iq]
            [telegram-chat-bot.specs.message :as message]
            [telegram-chat-bot.specs.poll :as poll]
            [telegram-chat-bot.specs.poll-answer :as pa]
            [telegram-chat-bot.specs.pre-checkout-query :as pcq]
            [telegram-chat-bot.specs.shipping-query :as sq]))

(s/def ::update_id pos-int?)

(s/def ::update
  (s/keys :req-un [::update_id]
          :opt-un [::message/message
                   ::message/edited_message
                   ::message/channel_post
                   ::message/edited_channel_post
                   ::iq/inline_query
                   ::cir/chosen_inline_result
                   ::cq/callback_query
                   ::sq/shipping_query
                   ::pcq/pre_checkout_query
                   ::poll/poll
                   ::pa/poll_answer]))
