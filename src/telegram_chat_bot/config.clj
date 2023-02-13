(ns telegram-chat-bot.config
  (:require
   [aero.core :as aero]
   [clojure.java.io :as io]))

(defn config
  "Read application configuration from resources."
  [profile]
  (-> "config/config.edn"
      io/resource
      (aero/read-config {:profile profile})))

(defn telegram-bot-api-token
  [config]
  (get-in config [:app :telegram :bot-api-key]))

(defn youtube-dl-output-format
  [config]
  (get-in config [:app :youtube :output-format]))

(defn youtube-dl-output-folder
  [config]
  (get-in config [:app :youtube :output-folder]))

(defn bucket-name
  [config]
  (get-in config [:app :aws :s3-bucket-name]))

(defn aws-region
  [config]
  (get-in config [:app :aws :s3-region]))

(defn aws-creds
  [config]
  (let [aws (get-in config [:app :aws])]
    {:access-key-id     (:s3-access-key aws)
     :secret-access-key (:s3-secret-key aws)}))

(defn google-api-key
  [config]
  (get-in config [:app :coloring :api-key]))

(defn google-cx
  [config]
  (get-in config [:app :coloring :cx]))

(defn google-search-url
  [config]
  (get-in config [:app :coloring :search-url]))


(def wrap-app-config
  {:name    ::wrap-app-config
   :compile (fn [{:keys [app-config]} _]
              (fn [handler]
                (fn [request]
                  (handler (assoc request :app-config app-config)))))})
