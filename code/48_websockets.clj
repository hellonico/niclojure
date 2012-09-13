(use 'lamina.core 'aleph.http)

(def broadcast-channel (channel))

(defn chat-handler [ch handshake]
  (receive ch
    (fn [name]
      (siphon (map* #(str name ": " %) ch) broadcast-channel)
      (siphon broadcast-channel ch))))

(start-http-server chat-handler {:port 8080 :websocket true})