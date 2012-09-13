; Copyright Nakkaya dot com
; http://nakkaya.com/2009/11/25/cryptography-with-clojure-one-time-pad/

; totally useless encryption

(defn rand-bytes [size]
  (let [rand (java.security.SecureRandom/getInstance "SHA1PRNG")
        buffer (make-array Byte/TYPE size)]
    (.nextBytes rand buffer) 
    buffer))

(defn encrypt [m]
  (let [message (.getBytes m)
        size (count message)
        pad  (rand-bytes size)
        code (map bit-xor message pad)]
    {:pad (vec pad) :msg (vec code)}))    

(defn decrypt [pad message]
  (apply str (map char (map bit-xor pad message))))

 (def k (encrypt "hello coffee time"))
 (println (decrypt (k :pad) (k :msg)))