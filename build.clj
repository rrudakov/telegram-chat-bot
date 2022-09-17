(ns build
  (:require
   [clojure.tools.build.api :as b]))

(def lib 'rrudakov/telegram-chat-bot)
(def version (format "1.2.%s" (b/git-count-revs nil)))
(def class-dir "target/classes")
(def basis (b/create-basis {:project "deps.edn"}))
(def uber-file (format "target/%s-%s.jar" (name lib) version))

(defn clean
  [_]
  (println "Clean target")
  (b/delete {:path "target"}))

(defn uber
  [_]
  (clean nil)
  (println "Write pom.xml")
  (b/write-pom {:class-dir class-dir
                :lib       lib
                :version   version
                :basis     basis
                :src-dirs  ["src"]})
  (println "Copy sources and resources")
  (b/copy-dir {:src-dirs   ["src" "resources"]
               :target-dir class-dir})
  (println "Build uberjar")
  (b/uber {:class-dir class-dir
           :uber-file uber-file
           :basis     basis
           :main      'telegram-chat-bot.core})
  (println "Created" uber-file))

(defn release
  [_]
  (println "Release version" version)
  (b/git-process {:git-args ["tag" "--sign" version "-a" "-m" (str "Release " version)]})
  (println "Success!"))
