; http://alexcollins.org/blog/2012/clojures-concurrency-easy-atoms-example-tutorial

; create an atom and let me refer to it by the identifier a 
(def a (atom 0)) 

; get its value
@a 

; increment the value of a
(swap! a inc)

@a

; use a validator for changing the value
(def a (atom 0 :validator even?)) 

; try to incremental a value again
(swap! a inc)

; this is validated
(reset! a 4)

; this is not
(reset! a 1)