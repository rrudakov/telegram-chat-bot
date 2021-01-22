(ns telegram-chat-bot.specs.invoice
  (:require [clojure.spec.alpha :as s]
            [telegram-chat-bot.specs.common :as common]))

(s/def ::start_parameter string?)

(s/def ::invoice
  (s/keys :req-un [::common/title
                   ::common/description
                   ::start_parameter
                   ::common/currency
                   ::common/total_amount]))
