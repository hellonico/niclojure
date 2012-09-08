(defproject test "1.0.0-SNAPSHOT"
  :description "FIXME: write description"


  :source-paths ["code"]
  :java-source-paths ["src/java"]

  :dependencies [
  [org.clojure/clojure "1.4.0"]

  [korma "0.3.0-beta7"]
  [com.h2database/h2 "1.3.154"]
  ;[clojure-soup "0.0.1"]
  [clj-xpath "1.3.0"]
  [clojure-soup/clojure-soup "0.0.1"]
  [jark "0.4.2" :exclusions [org.clojure/clojure]]
  [clj-ssh "0.4.0"]
  ])