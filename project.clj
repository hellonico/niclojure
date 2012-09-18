(defproject niclojure "1.0.0-SNAPSHOT"
  :description "Niclojure"

  :repositories {
    "mandubian-mvn" "http://mandubian-mvn.googlecode.com/svn/trunk/mandubian-mvn/repository"
    "xuggle" "http://xuggle.googlecode.com/svn/trunk/repo/share/java"
  }

  ; the below is dangerous, prevent loading java and scala classes
  ;:eval-in-leiningen true

  :dev-dependencies [
     ; scala compulation
     [lein-scalac "0.1.0"]
     [lein-eclipse "1.0.0"]
     [lein-marginalia "0.7.1"]
     [lein-jruby "0.1.0"]
     ; maybe need lein 2.0
     ;[lein-groovyc "0.2.0"]
  ]
  ;:groovyc-source-path "src/groovy"
  :scala-source-path "src/scala"
  :prep-tasks ["compile"]

  :source-paths ["code"]
  :java-source-path "src/java"

  :dependencies [
  ; core clojure
  [org.clojure/clojure "1.4.0"]
  ; colors for terminal 
  [colorize "0.1.0"]
  ; dependencies management
  [com.cemerick/pomegranate "0.0.13"]
  ; google api
  [com.google.gdata/gdata-contacts-3.0 "1.41.5"]
  ; opengl
  [bronsa/penumbra "0.6.0-SNAPSHOT"] 
  ; server and websockets
  [aleph "0.3.0-SNAPSHOT"]
  ; serial
  [serial-port "1.1.0"]
  ; tail call optimization
  [ctco "0.2.1"]
  ; heavy xml
  [xml-picker-seq "0.0.2"]
  ; opencl
  [calx "0.2.1"]
  ; selenium
  [clj-webdriver "0.6.0-alpha11"]
  ; dns
  [com.brweber2/clj-dns "0.0.2"]
  ; growl
  [clj-growlnotify "0.1.1"]
  ; rss
  [clj-rss "0.1.2"]
  ; json
  [cheshire "4.0.2"]
  ; http 
  [me.shenfeng/http-kit "1.1.0"]
  ; statistics and graph
  [incanter "1.3.0"]
  ; digest 
  [digest "1.3.0"]
  ; terminal 
  [clojure-lanterna "0.9.2"]
  ; opennlp
  [clojure-opennlp "0.1.10"]
  ; joda time
  [clj-time "0.4.3"]
  ; swing ui
  [seesaw "1.4.2"]
  ; csv
  [clojure-csv/clojure-csv "2.0.0-alpha1"]
  ; templates
  [de.ubercode.clostache/clostache "1.3.0"]
  ; navigation
  [org.clojure/data.zip "0.1.1"]
  ; excel
  [dk.ative/docjure "1.6.0-SNAPSHOT"] 
  ; sql schema
  [korma "0.3.0-beta7"]
  ; embedded database
  [com.h2database/h2 "1.3.154"]
  ; sql queries
  [lobos "1.0.0-SNAPSHOT"]
  ; scala 
  [org.scala-lang/scala-library "2.9.2"]
  ; emails
  [com.draines/postal "1.8.0"]
  ; full xpath
  [clj-xpath "1.3.0"]
  ; html soup
  [clojure-soup/clojure-soup "0.0.1"]
  ; ssh
  [clj-ssh "0.4.0"]
  ; lamina
  [lamina "0.5.0-alpha2"]

  ; http: this version is clashing with selenium, which includes it as well
  ; [clj-http "0.1.3"]
  ; xuggle 34M :)
  ; comment out until I find a repository that works
  ;[xuggle.xuggle-xuggler/xuggle-xuggler "5.4"]
  ; maybe not compatible with lein2 :(
  ;[jark "0.4.2" :exclusions [org.clojure/clojure]]
  ])