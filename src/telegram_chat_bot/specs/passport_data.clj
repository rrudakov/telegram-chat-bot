(ns telegram-chat-bot.specs.passport-data
  (:require [clojure.spec.alpha :as s]
            [telegram-chat-bot.specs.encrypted-credentials :as ec]
            [telegram-chat-bot.specs.encrypted-passport-element :as epe]))

(s/def ::data
  (s/coll-of ::epe/encrypted_passport_element :kind vector? :into []))

(s/def ::passport_data
  (s/keys :req-un [::data ::ec/credentials]))
