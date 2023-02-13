(ns telegram-chat-bot.core
  (:gen-class)
  (:require
   [clojure.string :as str]
   [clojure.tools.cli :refer [parse-opts]]
   [org.httpkit.server :refer [run-server server-stop!]]
   [reitit.ring :as ring]
   [taoensso.timbre :as log]
   [telegram-chat-bot.router :refer [router]]
   [telegram-chat-bot.config :as conf]))

(def ^:private cli-options
  [["-p" "--port PORT" "Port number"
    :default 3001
    :parse-fn parse-long
    :validate [#(< 0 % 0x10000) "Must be a number between 0 and 65536"]]
   ["-e" "--env ENV" "Environment keyword"
    :default :prod
    :parse-fn keyword
    :validate [#{:prod :local} "Allowed values: prod or local"]]
   ["-h" "--help"]])

(defn- usage
  [options-summary]
  (->> ["Telegram chat bot."
        ""
        "Usage: java -jar telegram-chat-bot.jar [options]"
        ""
        "Options:"
        options-summary]
       (str/join \newline)))

(defn- error-msg [errors]
  (str "The following errors occurred while parsing your command:\n\n"
       (str/join \newline errors)))

(defn- exit
  [status msg]
  (log/log (if (= 0 status) :info :error) msg)
  (System/exit status))

(defn- app
  [env]
  (let [app-config (conf/config env)]
    (log/infof "Start telegram chat bot: %s" env)
    (ring/ring-handler
      (router {:config app-config})
      (ring/create-default-handler
        {:not-found          (constantly {:status 404 :body "Not found"})
         :method-not-allowed (constantly {:status 405 :body "Not allowed"})
         :not-acceptable     (constantly {:status 406 :body "Not acceptable"})}))))

(defn -main
  [& args]
  (let [{:keys [options summary errors]}
        (parse-opts args cli-options)]
    (if (:help options)
      (exit 0 (usage summary))
      (if errors
        (exit 1 (error-msg errors))
        (run-server (app (:env options))
                    {:port                 (:port options)
                     :legacy-return-value? false})))))


(defonce server (atom nil))

(comment

  (parse-opts '("-h") cli-options)


  (let [s (-main "-e" "local")]
    (reset! server s))

  @(server-stop! @server))
