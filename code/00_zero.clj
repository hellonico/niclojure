; use the existing method map
(doc map)
(map #(inc %) [1 2 3])

; define a method with doc
(defn function-with-doc
	"Make sure you have this string to explain what the method is doing.\n Print the parameters"
	[& i]
	(println i))
(doc function-with-doc)
(function-with-doc 2)

; macro to avoid java painfulness
; warning, already exist in clojure.core
(defmacro with-open [args & body]
  `(let ~args
    (try ~@body
     (finally (.close ~(first args))))))

; use the method
(with-open [stream (.openStream (java.net.URL. "http://www.google.com"))]
	(.available stream))

; do some regexp
(re-find #"abc" "This is a string that contains abc")

; doseq
(doseq [x [1 2 3] y [1 2 3]] 
	(println (* x y)))
; returns 
; 1 2 3
; 2 4 6
; 3 6 9

; some old xml code. round trip xml parsing
(use 'clojure.xml)
(emit (parse "http://feeds.feedburner.com/burningonesblog"))

; every line of a file in sequences
(line-seq (clojure.java.io/reader (clojure.java.io/file “project.clj”)))