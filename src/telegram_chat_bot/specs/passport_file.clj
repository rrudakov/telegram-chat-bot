(ns telegram-chat-bot.specs.passport-file
  (:require [clojure.spec.alpha :as s]
            [telegram-chat-bot.specs.common :as common]))

(s/def ::file_date int?)

(s/def ::passport_file
  (s/keys :req-un [::common/file_id
                   ::common/file_unique_id
                   ::common/file_size
                   ::file_date]))

(s/def ::front_side ::passport_file)

(s/def ::reverse_side ::passport_file)

(s/def ::selfie ::passport_file)

(s/def ::files (s/coll-of ::passport_file :kind vector? :into []))

(s/def ::translation ::files)
