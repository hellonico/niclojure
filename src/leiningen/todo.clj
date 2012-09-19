(ns leiningen.todo)
(use 'clojure.pprint)

(load-file "code/57_grep2.clj")

(defn todo [project & args] 
	(doall (map #(println %) (grep-in-files #"TODO" (map #(.getPath %) (glob "**/*.md"))))))