(ns factorial)

(defn compute [n]
   (apply * (take n (iterate inc 1))))

(println "Factorial of 10 :" (compute 15))