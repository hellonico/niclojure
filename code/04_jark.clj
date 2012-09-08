; project.clj
(comment 
	; include this in project.clj
(defproject book01 "1.0.0-SNAPSHOT"
  :dependencies [
  	[org.clojure/clojure "1.3.0"]
  	[jark "0.4.2" :exclusions [org.clojure/clojure]] 
  ]))

; now let's start the embedded repl
(require '[clojure.tools.nrepl :as nrepl])
(nrepl/start-server 9000)