(ns telegram-chat-bot.specs.inline-keyboard-button
  (:require [clojure.spec.alpha :as s]
            [telegram-chat-bot.specs.common :as common]
            [telegram-chat-bot.specs.login-url :as lu]))

(s/def ::callback_data string?)

(s/def ::switch_inline_query string?)

(s/def ::switch_inline_query_current_chat string?)

(s/def ::pay boolean?)

;; NOTE: There also `callback_game` field, but according to official
;; documentation it doesn't hold any information
(s/def ::inline_keyboard_button
  (s/keys :req-un [::common/text]
          :opt-un [::common/url
                   ::lu/login_url
                   ::callback_data
                   ::switch_inline_query_current_chat
                   ::pay]))

(s/def ::inline_keyboard_buttons
  (s/coll-of ::inline_keyboard_button :kind vector? :into []))
