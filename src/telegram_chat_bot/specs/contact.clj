(ns telegram-chat-bot.specs.contact
  (:require [clojure.spec.alpha :as s]
            [telegram-chat-bot.specs.common :as common]))

(s/def ::user_id pos-int?)

(s/def ::vcard string?)

(s/def ::contact
  (s/keys :req-un [::common/phone_number
                   ::common/first_name]
          :opt-un [::common/last_name
                   ::user_id
                   ::vcard]))
