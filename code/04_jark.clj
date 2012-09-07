; project.clj

(defproject book01 "1.0.0-SNAPSHOT"
  :description "FIXME: write description"
  :dependencies [
  	[org.clojure/clojure "1.3.0"]
  	[jark "0.4.2" :exclusions [org.clojure/clojure]] 
  ])

; core.clj
(ns book01.core
	(:require [clojure.tools.nrepl :as nrepl]))

(nrepl/start-server 9000)