(use 'me.shenfeng.http.server)

(defasync async [req]
  (.start (Thread. (fn []
                     (Thread/sleep 1000)
                     ;; return a ring spec response
                     ;; call (:cb req) when response ready
                     ((:cb req) {:status 200 :body "hello async"})))))

(run-server async {:port 8080})