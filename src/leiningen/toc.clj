(ns leiningen.toc)

(require '[markdown :as md])
(use 'jsoup.soup)
(import 'java.awt.Desktop)
(import 'java.io.File)

(load-file "code/57_grep2.clj")

(def output_file "toc.html")

(defn rd [file]
	(parse (md/md-to-html-string (slurp file))))

(defn write[text]
	(spit output_file text :append true))

; awesome and dirty, remove the elements we do not want
(defn tic[content]
	(doseq [t ["p" "ul" "li" "a" "img" "pre" "code" "blockquote" ]]
		(.remove (select t content)))
	content)

(defn toc1 [content]
	  (let [cleaned (tic content)]
		(write ($ cleaned "body > *"))))
	  
(defn tocall[]
	(doseq [md (glob "**/*.md")] 
		(toc1 (rd md))))

(defn headers[]
	(str
		"<html><head>"
		"<link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\" media=\"screen\" />"
		"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />"
		"</head>"))
(defn openf[file]
	((.open (Desktop/getDesktop) (File. file))))

; (defn toc[project & args] 
; ↑ when I have time to debug this mess 
(defn toc[] 
	(spit output_file (headers))
	(tocall)
	(openf output_file))