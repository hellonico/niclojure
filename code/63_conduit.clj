; conduit 
(ns conduit-samples
  (:use conduit.core :reload-all)
  (:use clojure.test))

;; This will simply return the range 1..5 as an array
(def conduit-inc (a-arr inc))
(is (= [1 2 3 4 5]
       (conduit-map conduit-inc
                    (range 5))))

; this will apply a * 2 operation to every value going through the stream
(def-arr conduit-double [x] (* 2 x))
(def inc-ints (a-comp conduit-inc conduit-double))

(is (= [2 4 6 8 10]
       (conduit-map inc-ints
                    (range 5))))