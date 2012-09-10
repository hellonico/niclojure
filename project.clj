(defproject test "1.0.0-SNAPSHOT"
  :description "FIXME: write description"

  ; the below is dangerous, prevent loading java and scala classes
  ;:eval-in-leiningen true
  :dev-dependencies [
     [lein-scalac "0.1.0"]
  ]
  :scala-source-path "src/scala"
  :prep-tasks ["compile"]

  :source-paths ["code"]
  :java-source-path "src/java"

  :dependencies [
  [com.brweber2/clj-dns "0.0.2"]
  [clj-growlnotify "0.1.1"]
  [clj-rss "0.1.2"]
  [cheshire "4.0.2"]
  [me.shenfeng/http-kit "1.1.0"]

  [clojure-csv/clojure-csv "2.0.0-alpha1"]
  [de.ubercode.clostache/clostache "1.3.0"]

  [org.clojure/data.zip "0.1.1"]
  [org.clojure/clojure "1.4.0"]

  [dk.ative/docjure "1.6.0-SNAPSHOT"] 
  [korma "0.3.0-beta7"]
  [com.h2database/h2 "1.3.154"]
  [lobos "1.0.0-SNAPSHOT"]

  ; scala me 
  [org.scala-lang/scala-library "2.9.2"]

  [com.draines/postal "1.8.0"]
  
  ;[clojure-soup "0.0.1"]
  [clj-xpath "1.3.0"]
  [clojure-soup/clojure-soup "0.0.1"]
  [jark "0.4.2" :exclusions [org.clojure/clojure]]
  [clj-ssh "0.4.0"]
  ])