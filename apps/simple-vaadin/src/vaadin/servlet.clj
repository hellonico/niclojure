(ns vaadin.servlet
  (:import [clojure.lang RT])
  (:gen-class
    :extends com.vaadin.terminal.gwt.server.AbstractApplicationServlet
    :name vaadin.Servlet))
 
(defn ^Class -getApplicationClass [this]
  (class com.vaadin.Application))

(defn -getNewApplication [this request]
	(RT/load "vaadin/nico" true)
	(.invoke (RT/var "test.tlp" "main") (object-array 0)))