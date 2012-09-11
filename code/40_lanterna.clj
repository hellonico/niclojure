(require '[lanterna.screen :as s])

(def scr (s/get-screen))

(s/start scr)

(s/put-string scr 10 10 "Hello, world!")
(s/put-string scr 10 11 "Press any key to exit!")
;(s/get-key-blocking scr)

(s/stop scr)