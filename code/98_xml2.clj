; http://stackoverflow.com/questions/9671966/clojure-hierarchical-diff-of-two-xml-files

(use 'clojure.data)
(use 'clojure.xml)

(def nhkworld_program (slurp "http://www3.nhk.or.jp/nhkworld/english/epg/nhkworld_program.xml"))
(def nhkworld_program2 (slurp "http://www3.nhk.or.jp/nhkworld/english/epg/nhkworld_program.xml"))
; (def file1 "/Users/Niko/")
; (def file2 )
(def d (clojure.data/diff (clojure.xml/parse nhkworld_program) (clojure.xml/parse nhkworld_program)))

