(defproject hamweather "1.0.1"
  :description "Ham weather from clojure"
  :main hamweather.core
  :aot [hamweather.core] 
  :dev-dependencies [
   [lein-eclipse/lein-eclipse "1.0.0"]
  ]
  :dependencies [
   [org.clojure/data.json "0.1.3"]
   [org.clojure/clojure "1.4.0"]])