(ns leiningen.toc)

(require '[markdown :as md])
(use 'jsoup.soup)

(load-file "code/57_grep2.clj")

(def output_file "toc.html")

(defn rd [file]
	(parse (md/md-to-html-string (slurp file))))

(defn write[text indent]
	(loop [t indent] (when (> t 1) (spit output_file " " :append true) (recur (- t 1))))
	(spit output_file text :append true)
	(spit output_file "\n" :append true))

(defn toc1 [content]
	(doseq [i (take 5 (iterate inc 1))] 
	  (let [
	  	h  (str "h" i)
		c (select h content)]
	  	(doseq [v c] (write (.toString v) i)))))
	  
(defn tocall[]
	(doseq [md (glob "**/*.md")] (toc1 (rd md))))

; (defn toc[project & args] 
; ↑ when I have time to debug this mess 
(defn toc[] 
	(spit output_file "")
	(tocall))