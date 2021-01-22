(ns telegram-chat-bot.specs.inline-keyboard-markup
  (:require [clojure.spec.alpha :as s]
            [telegram-chat-bot.specs.inline-keyboard-button :as ikb]))

(s/def ::inline_keyboard
  (s/coll-of ::ikb/inline_keyboard_buttons :kind vector? :into []))

(s/def ::reply_markup
  (s/keys :req-un [::inline_keyboard]))
