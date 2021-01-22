(ns telegram-chat-bot.core
  (:require [reitit.ring :as ring]
            [telegram-chat-bot.router :refer [router]]))

(def app
  (ring/ring-handler
   router
   (ring/create-default-handler
    {:not-found          (constantly {:status 404 :body "Not found"})
     :method-not-allowed (constantly {:status 405 :body "Not allowed"})
     :not-acceptable     (constantly {:status 406 :body "Not acceptable"})})))
