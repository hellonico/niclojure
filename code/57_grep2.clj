(use 'clojure.java.io)
(import '[java.io File])

; glob
(defn- glob->regex
  "Takes a glob-format string and returns a regex."
  [s]
  (loop [stream s
         re ""
         curly-depth 0]
    (let [[c j] stream]
        (cond
         (nil? c) (re-pattern (str (if (= \. (first s)) "" "(?=[^\\.])") re))
         (= c \\) (recur (nnext stream) (str re c c) curly-depth)
         (= c \/) (recur (next stream) (str re (if (= \. j) c "/(?=[^\\.])"))
                         curly-depth)
         (= c \*) (recur (next stream) (str re "[^/]*") curly-depth)
         (= c \?) (recur (next stream) (str re "[^/]") curly-depth)
         (= c \{) (recur (next stream) (str re \() (inc curly-depth))
         (= c \}) (recur (next stream) (str re \)) (dec curly-depth))
         (and (= c \,) (< 0 curly-depth)) (recur (next stream) (str re \|)
                                                 curly-depth)
         (#{\. \( \) \| \+ \^ \$ \@ \%} c) (recur (next stream) (str re \\ c)
                                                  curly-depth)
         :else (recur (next stream) (str re c) curly-depth)))))

;; compromise to aid in testing
(defn- get-root-file
  [root-name]
  (as-file (str root-name "/")))

(defn- get-cwd-file
  []
  (as-file "."))

(defn- filter-dir
  "Filters dir for files with names matching pattern re"
  [^File dir re]
  (filter #(re-matches re (.getName ^File %))
          (.listFiles dir)))

(defn glob
  "Returns a seq of java.io.File instances that match the given glob pattern.
  Ignores dot files unless explicitly included.

  Examples: (glob \"*.{jpg,gif}\") (glob \".*\") (glob \"/usr/*/se*\")"
  [pattern]
  (let [[root & _ :as parts] (.split #"[\\/]" pattern)
        abs? (or (empty? root) ;unix
                 (= \: (second root))) ;windows
        start-dir (if abs? (get-root-file root) (get-cwd-file))
        patterns (map glob->regex (if abs? (rest parts) parts))]
    (reduce
     (fn [files re]
       (mapcat #(filter-dir % re) files))
     [start-dir]
     patterns)))



;; by Craig Andera
(defn dir-descendants [dir]
  (let [children (.listFiles (File. dir))]
    (lazy-cat 
     (map (memfn getPath) (filter (memfn isFile) children)) 
     (mapcat dir-descendants 
	     (map (memfn getPath) (filter (memfn isDirectory) children))))))


(defn numbered-lines [lines]
  (map vector (iterate inc 0) lines))

; old contrib.read-lines
(defn read-lines[file]
 (line-seq (clojure.java.io/reader (clojure.java.io/file file))))

(defn grep-in-file [pattern file]
  {file (vec (filter #(re-find pattern (second %)) (numbered-lines (read-lines file))))})

(defn expand-file
  [file]
  (if (.isDirectory (java.io.File. file))
    (dir-descendants file)
    file))

(defn all-files
  [files]
  (if (string? files)
    (expand-file files)
    (flatten (map expand-file files))))

(defn grep-in-files [pattern files & {:keys [parallel]}]
  (let [map-fn (if parallel pmap map)]
    (apply merge (map-fn #(grep-in-file pattern %) (all-files files)))))

(defn print-matches [matches]
  (doseq [[fname submatches] matches
	  [line-no match] submatches]
    (printf "%s:%s:%s\n" fname line-no match)))

; (defn -main [& args]
;   (with-command-line args
;     "Simple grep, written in Clojure"
;     [[parallel? p? "Run in parallel" false]
;      remaining]
;     (let [[pattern & files] remaining]
;       (if (or (nil? pattern) (empty? files))
; 	(println "Usage: grep <pattern> <file...>")
; 	(do 
; 	  (.println *err* (str "Running with pattern " pattern
; 			       ", parallel set to " parallel?
; 			       " and files "
; 			       (apply str (interpose " " files))))
; 	  (print-matches (grep-in-files (re-pattern pattern) files :parallel parallel?))
; 	  (shutdown-agents)
; 	  (.println *err* "Done."))))))