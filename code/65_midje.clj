(ns checkers.test
  (:use [midje.sweet])) 

; simple check
(fact 
  (+ 1 1) => 2)

; same with a description
(fact "addition has a unit element"
  (+ 12345 0) => 12345)

(fact
  "O wad some Pow'r the giftie gie us. To see oursels as ithers see us!"
  => #"giftie")

(fact
  (+ 1 1) => even?)

(defn function-that-returns-a-collection []
  [4])

; fails
(fact
  (function-that-returns-a-collection) => (contains 5))