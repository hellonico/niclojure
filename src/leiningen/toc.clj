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

(defn tic[content]
	(doseq [t ["p" "ul" "li" "a" "img" "pre" "code" "blockquote" ]]
		(.remove (select t content)))
	content)

(defn toc1 [content]
	  (spit "toc_debug.html" content :append true)
	  (let [cleaned (tic content)]
		(write ($ cleaned "body > *"))))
	  
(defn tocall[]
	(doseq [md (glob "**/*.md")] 
		(toc1 (rd md))))

(def headers (str
		"<html><head>"
		"<link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\" media=\"screen\" />"
		"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />"
		"</head>"))

(defn openf[file]
	((.open (Desktop/getDesktop) (File. file))))

(defn toc[] 
	(spit "toc_debug.html" "")
	(spit output_file headers)
	(tocall)
	(openf output_file))