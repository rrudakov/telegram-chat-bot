(ns telegram-chat-bot.specs.user
  (:require [clojure.spec.alpha :as s]
            [telegram-chat-bot.specs.common :as common]))

(s/def ::id pos-int?)

(s/def ::is_bot boolean?)

(s/def ::language_code string?)

(s/def ::can_join_groups boolean?)

(s/def ::can_read_all_group_messages boolean?)

(s/def ::supports_inline_queries boolean?)

(s/def ::user
  (s/keys :req-un [::id
                   ::is_bot
                   ::common/first_name]
          :opt-un [::common/last_name
                   ::common/username
                   ::language_code
                   ::can_join_groups
                   ::can_read_all_group_messages
                   ::supports_inline_queries]))

;; User aliases
(s/def ::from ::user)
(s/def ::forward_from ::user)
(s/def ::via_bot ::user)
(s/def ::left_chat_member ::user)
(s/def ::traveler ::user)
(s/def ::watcher ::user)

(s/def ::new_chat_members
  (s/coll-of ::user :kind vector? :into []))
