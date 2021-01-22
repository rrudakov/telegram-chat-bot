(ns telegram-chat-bot.specs.common
  (:require [clojure.spec.alpha :as s]))

(s/def ::first_name string?)

(s/def ::last_name string?)

(s/def ::username string?)

(s/def ::phone_number string?)

(s/def ::email string?)

(s/def ::url string?)

(s/def ::title string?)

(s/def ::description string?)

(s/def ::file_id string?)

(s/def ::file_unique_id string?)

(s/def ::duration int?)

(s/def ::width int?)

(s/def ::height int?)

(s/def ::file_name string?)

(s/def ::file_size int?)

(s/def ::mime_type string?)

(s/def ::emoji string?)

(s/def ::text string?)

(s/def ::address string?)

(s/def ::currency string?)

(s/def ::total_amount int?)

(s/def ::invoice_payload string?)

(s/def ::shipping_option_id string?)
