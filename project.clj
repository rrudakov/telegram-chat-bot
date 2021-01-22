(defproject telegram-chat-bot "0.1.0"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [ring/ring-jetty-adapter "1.7.1"]
                 [metosin/reitit-core "0.5.11"]
                 [metosin/reitit-ring "0.5.11"]
                 [metosin/reitit-middleware "0.5.11"]
                 [metosin/reitit-spec "0.5.11"]
                 [metosin/reitit-swagger "0.5.11"]
                 [metosin/reitit-swagger-ui "0.5.11"]
                 [aero "1.1.6"]
                 [clj-http "3.10.3"]
                 [cheshire "5.10.0"]
                 [com.fasterxml.jackson.core/jackson-core "2.12.1"]]
  :repl-options {:init-ns telegram-chat-bot.core}
  :plugins [[lein-ring "0.12.5"]
            [lein-cljfmt "0.7.0"]]
  :cljfmt {}
  :ring {:handler telegram-chat-bot.core/app
         :port 3001})
