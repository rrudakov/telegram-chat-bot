(defproject telegram-chat-bot "1.1.0-SNAPSHOT"
  :description "Telegram chat bot for my personal needs"
  :url "https://github.com/rrudakov/telegram-chat-bot"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url  "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [org.clojure/core.async "1.5.648"]
                 [ring/ring-jetty-adapter "1.9.5"]
                 [metosin/reitit-core "0.5.18"]
                 [metosin/reitit-ring "0.5.18"]
                 [metosin/reitit-middleware "0.5.18"]
                 [metosin/reitit-spec "0.5.18"]
                 [metosin/reitit-swagger "0.5.18"]
                 [metosin/reitit-swagger-ui "0.5.18"]
                 [aero "1.1.6"]
                 [clj-http "3.12.3"]
                 [cheshire "5.11.0"]
                 [amazonica "0.3.161"]
                 [clj-time "0.15.2"]]
  :repl-options {:init-ns telegram-chat-bot.core}
  :release-tasks [["vcs" "assert-committed"]
                  ["change" "version" "leiningen.release/bump-version"]
                  ["change" "version"
                   "leiningen.release/bump-version" "release"]
                  ["vcs" "commit"]
                  ["vcs" "tag"]
                  ["change" "version" "str" "\"-SNAPSHOT\""]
                  ["vcs" "commit"]
                  ["vcs" "push"]]
  :plugins [[lein-ring "0.12.5"]
            [lein-cljfmt "0.7.0"]]
  :cljfmt {}
  :ring {:handler telegram-chat-bot.core/app
         :port    3001})
