; https://gist.github.com/2364544
(use '[clojure.java.io :as io])

(defn indexed [coll]
  (map vector (iterate inc 1) coll))

(defn grep [re file]
  (with-open [rdr (io/reader file)]
    (doall
     (map #(printf "%d: %s\n" (first %) (nth % 1))
          (filter #(re-find (re-pattern re) (nth % 1)) (indexed (line-seq rdr)))))))