(ns telegram-chat-bot.specs.login-url
  (:require [clojure.spec.alpha :as s]
            [telegram-chat-bot.specs.common :as common]))

(s/def ::forward_text string?)

(s/def ::bot_username string?)

(s/def ::request_write_access boolean?)

(s/def ::login_url
  (s/keys :req-un [::common/url
                   ::forward_text
                   ::bot_username
                   ::request_write_access]))
