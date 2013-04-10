(ns vaadin.jetty
  (:import [org.eclipse.jetty.server Server]
           [org.eclipse.jetty.servlet
            DefaultServlet
            ServletContextHandler
            ServletHolder]))

(def server (Server. 8090))

(defn start [& args]
  (let [context (ServletContextHandler. ServletContextHandler/SESSIONS)
        holder  (.addServlet context DefaultServlet "/tmp")]
    (.setContextPath context "/")
    (.setHandler server context)
    (doseq [[param value] [
			["resourceBase" "/tmp"] 
			["pathInfoOnly" "true"]]]
      (.setInitParameter holder  param value))
    (.addServlet context (ServletHolder. (vaadin.Servlet.)) "/*")
    (future
      (.start server)
      (.join server ))
    server))

(defn restart []
  (.stop server)
  (start))

(defn -main [& args]
  (start))