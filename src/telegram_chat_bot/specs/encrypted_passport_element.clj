(ns telegram-chat-bot.specs.encrypted-passport-element
  (:require [clojure.spec.alpha :as s]
            [telegram-chat-bot.specs.common :as common]
            [telegram-chat-bot.specs.passport-file :as pf]))

(s/def ::type
  #{"personal_details"
    "passport"
    "driver_license"
    "identity_card"
    "internal_passport"
    "address"
    "utility_bill"
    "bank_statement"
    "rental_agreement"
    "passport_registration"
    "temporary_registration"
    "phone_number"
    "email"})

(s/def ::data string?)

(s/def ::hash string?)

(s/def ::encrypted_passport_element
  (s/keys :req-un [::type
                   ::hash]
          :opt-un [::data
                   ::common/phone_number
                   ::common/email
                   ::pf/files
                   ::pf/front_side
                   ::pf/reverse_side
                   ::pf/selfie
                   ::pf/translation]))
