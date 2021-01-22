(ns telegram-chat-bot.specs.permissions
  (:require [clojure.spec.alpha :as s]))

(s/def ::can_send_messages boolean?)

(s/def ::can_send_media_messages boolean?)

(s/def ::can_send_polls boolean?)

(s/def ::can_send_other_messages boolean?)

(s/def ::can_add_web_page_previews boolean?)

(s/def ::can_change_info boolean?)

(s/def ::can_invite_users boolean?)

(s/def ::can_pin_messages boolean?)

(s/def ::permissions
  (s/keys :opt-un [::can_send_messages
                   ::can_send_media_messages
                   ::can_send_polls
                   ::can_send_other_messages
                   ::can_add_web_page_previews
                   ::can_change_info
                   ::can_invite_users
                   ::can_pin_messages]))
