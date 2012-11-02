(ns leiningen.toc
	(:require [markdown :as md])
	(:use jsoup.soup)
	(:import [java.awt Desktop])
	(:import [java.io File]))

(load-file "code/57_grep2.clj")

(def output_file "html/toc.html")
(def desktop (Desktop/getDesktop))

(defn rd [file]
	(parse (md/md-to-html-string (slurp file))))

(defn write[text]
	(spit output_file text :append true))

(defn tic[content]
	(doseq [t ["p" "ul" "li" "a" "img" "pre" "code" "blockquote" ]]
		(.remove (select t content)))
	content)

(defn toc1 [content]
	  (let [cleaned (tic content)]
		(write ($ cleaned "body > *"))))
	  
(defn tocall[base]
	(doseq [md (glob (str base "/*.md"))] 
		(toc1 (rd md))))

(defn openf[file]
	(.open desktop (File. file)))

(defn toc[base] 
	(spit output_file (slurp "html/header.html"))
	(tocall base)
	(spit output_file (slurp "html/footer.html") :append true)
	(openf output_file))

(defn tocja[]
	(toc "textja"))
(defn tocen[]
	(toc "text"))
;
;(defn toc[project & args] 
;   (tocja))