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