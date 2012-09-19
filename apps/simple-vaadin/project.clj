(defproject clj-vaadin "1.0.0"
  :description "Clojure Vaadin"
  :dependencies [
	[org.clojure/clojure "1.4.0"]
    [core.logic "0.6.1-SNAPSHOT"]
    [com.vaadin/vaadin "6.6.2"]
    [org.eclipse.jetty/jetty-server "8.0.0.M3"]
    [org.eclipse.jetty/jetty-webapp "8.0.0.M3"]]
  :aot [vaadin.servlet]
  :main vaadin.jetty)