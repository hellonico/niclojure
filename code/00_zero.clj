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

; multi dimensional loops
(doseq [x [1 2 3] y [1 2 3]] 
	(println (* x y)))
; returns 
; 1 2 3
; 2 4 6
; 3 6 9

; some old xml code. round trip xml parsing
(use 'clojure.xml)
(emit (parse "http://feeds.feedburner.com/burningonesblog"))

; text is code is text !
(eval '(apply str [1 2 3 4 5]))

; every line of a file in sequences
(line-seq (clojure.java.io/reader "project.clj"))

; cool destructuring
; http://stackoverflow.com/questions/11990986/how-to-iterate-over-a-nested-array-hash-structure-in-clojure?rq=1s
(def config [{:host "test", :port 1},{:host "testtest", :port 2}])

(for [{h :host p :port} config]
  (format "host: %s ; port: %s" h p))

; filter even numbers
(filter even? (range 10))
; (0 2 4 6 8)

(filter #(> (second %) 100) 
	{:a 1 :b 2 :c 101 :d 102 :e -1})
; ([:c 101] [:d 102])

; futures in one line
(def f (future (Thread/sleep 10000) (println "done") 100))
; blocks until the value has been computed
@f