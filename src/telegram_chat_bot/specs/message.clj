(ns telegram-chat-bot.specs.message
  (:require [clojure.spec.alpha :as s]
            [telegram-chat-bot.specs.animation :as a]
            [telegram-chat-bot.specs.common :as common]
            [telegram-chat-bot.specs.contact :as con]
            [telegram-chat-bot.specs.dice :as dice]
            [telegram-chat-bot.specs.game :as game]
            [telegram-chat-bot.specs.inline-keyboard-markup :as ikm]
            [telegram-chat-bot.specs.invoice :as invoice]
            [telegram-chat-bot.specs.location :as l]
            [telegram-chat-bot.specs.message-entity :as me]
            [telegram-chat-bot.specs.passport-data :as pd]
            [telegram-chat-bot.specs.photo-size :as ps]
            [telegram-chat-bot.specs.poll :as poll]
            [telegram-chat-bot.specs.proximity-alert-triggered :as pat]
            [telegram-chat-bot.specs.successful-payment :as sp]
            [telegram-chat-bot.specs.user :as u]
            [telegram-chat-bot.specs.venue :as venue]
            [telegram-chat-bot.specs.video :as vi]
            [telegram-chat-bot.specs.video-note :as vin]
            [telegram-chat-bot.specs.voice :as voi]
            [telegram-chat-bot.specs.photo :as p]
            [telegram-chat-bot.specs.chat-location :as cl]))

;; Chat
;; I had to put it here to avoid cyclic dependencies
(s/def ::id pos-int?)

(s/def ::type #{"private" "group" "supergroup" "channel"})

(s/def ::bio string?)

(s/def ::invite_link string?)

(s/def ::slow_mode_delay pos-int?)

(s/def ::sticker_set_name string?)

(s/def ::can_set_sticker_set boolean?)

(s/def ::linked_chat_id pos-int?)

(s/def ::chat
  (s/keys :req-un [::id
                   ::type]
          :opt-un [::common/title
                   ::common/username
                   ::common/first_name
                   ::common/last_name
                   ::p/photo
                   ::bio
                   ::common/description
                   ::invite_link
                   ::pinned_message
                   ::permissions
                   ::slow_mode_delay
                   ::sticker_set_name
                   ::can_set_sticker_set
                   ::linked_chat_id
                   ::cl/location]))

;; Aliases for chat
(s/def ::sender_chat ::chat)
(s/def ::forward_from_chat ::chat)

;; Message
(s/def ::message_id pos-int?)

(s/def ::date pos-int?)

(s/def ::forward_from_message_id pos-int?)

(s/def ::forward_signature string?)

(s/def ::forward_sender_name string?)

(s/def ::forward_date pos-int?)

(s/def ::edit_date pos-int?)

(s/def ::media_group_id string?)

(s/def ::author_signature string?)

(s/def ::caption string?)

(s/def ::new_chat_title string?)

(s/def ::delete_chat_photo true?)

(s/def ::group_chat_created true?)

(s/def ::supergroup_chat_created true?)

(s/def ::channel_chat_created true?)

(s/def ::migrate_to_chat_id int?)

(s/def ::migrate_from_chat_id int?)

(s/def ::connected_website string?)

(s/def ::message
  (s/keys :req-un [::message_id
                   ::date]
          :opt-un [::u/from
                   ::sender_chat
                   ::chat
                   ::u/forward_from
                   ::forward_from_chat
                   ::forward_from_message_id
                   ::forward_signature
                   ::forward_sender_name
                   ::forward_date
                   ::reply_to_message
                   ::u/via_bot
                   ::edit_date
                   ::media_group_id
                   ::author_signature
                   ::common/text
                   ::me/entities
                   ::a/animation
                   ::vi/video
                   ::vin/video_note
                   ::voi/voice
                   ::caption
                   ::me/caption_entities
                   ::con/contact
                   ::dice/dice
                   ::game/game
                   ::poll/poll
                   ::venue/venue
                   ::l/location
                   ::u/new_chat_members
                   ::u/left_chat_member
                   ::new_chat_title
                   ::ps/new_chat_photo
                   ::delete_chat_photo
                   ::group_chat_created
                   ::supergroup_chat_created
                   ::channel_chat_created
                   ::migrate_to_chat_id
                   ::migrate_from_chat_id
                   ::pinned_message
                   ::invoice/invoice
                   ::sp/successful_payment
                   ::connected_website
                   ::pd/passport_data
                   ::pat/proximity_alert_triggered
                   ::ikm/reply_markup]))

;; Aliases for message
(s/def ::reply_to_message ::message)
(s/def ::pinned_message ::message)
(s/def ::edited_message ::message)
(s/def ::channel_post ::message)
(s/def ::edited_channel_post ::message)
