(ns telegram-chat-bot.specs.photo
  (:require [clojure.spec.alpha :as s]))

(s/def ::small_file_id string?)

(s/def ::small_file_unique_id string?)

(s/def ::big_file_id string?)

(s/def ::big_file_unique_id string?)

(s/def ::photo
  (s/keys :req-un [::small_file_id
                   ::small_file_unique_id
                   ::big_file_id
                   ::big_file_unique_id]))
