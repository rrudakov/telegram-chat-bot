(ns telegram-chat-bot.specs.poll
  (:require [clojure.spec.alpha :as s]
            [telegram-chat-bot.specs.poll-option :as po]
            [telegram-chat-bot.specs.message-entity :as me]))

(s/def ::id string?)

(s/def ::question string?)

(s/def ::total_voter_count int?)

(s/def ::is_closed boolean?)

(s/def ::is_anonymous boolean?)

(s/def ::type #{"regular" "quiz"})

(s/def ::allows_multiple_answers boolean?)

(s/def ::correct_option_id int?)

(s/def ::explanation string?)

(s/def ::open_period int?)

(s/def ::close_date int?)

(s/def ::poll
  (s/keys :req-un [::id
                   ::question
                   ::po/options
                   ::total_voter_count
                   ::is_closed
                   ::is_anonymous
                   ::type
                   ::allows_multiple_answers]
          :opt-un [::correct_option_id
                   ::explanation
                   ::me/explanation_entities
                   ::open_period
                   ::close_date]))
