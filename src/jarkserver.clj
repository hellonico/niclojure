; start me with 
; lein run -m jarkserver
(ns jarkserver)

(require '[clojure.tools.nrepl :as nrepl])

(defn -main
	[& args]
	(nrepl/start-server 9000)
	(.join (Thread/currentThread)))