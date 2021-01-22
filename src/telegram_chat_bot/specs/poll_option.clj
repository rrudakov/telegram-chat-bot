(ns telegram-chat-bot.specs.poll-option
  (:require [clojure.spec.alpha :as s]
            [telegram-chat-bot.specs.common :as common]))

(s/def ::voter_count int?)

(s/def ::poll_option (s/keys :req-un [::common/text ::voter_count]))

(s/def ::options (s/coll-of ::poll_option :kind vector? :into []))
