#!/Usr/bin/env jark

(ns factorial)

(defn compute [n]
   (apply *' (take n (iterate inc 1))))

(println "Factorial of 30 :" (compute 30))

(defn -main[& args]
	(println args))