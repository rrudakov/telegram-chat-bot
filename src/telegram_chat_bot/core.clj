(ns telegram-chat-bot.core
  (:gen-class)
  (:require
   [org.httpkit.server :refer [run-server]]
   [reitit.ring :as ring]
   [telegram-chat-bot.router :refer [router]]))

(def app
  (ring/ring-handler
   router
   (ring/create-default-handler
    {:not-found          (constantly {:status 404 :body "Not found"})
     :method-not-allowed (constantly {:status 405 :body "Not allowed"})
     :not-acceptable     (constantly {:status 406 :body "Not acceptable"})})))

(defn -main
  [& _args]
  (run-server #'app {:port 3001}))
