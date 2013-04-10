(defproject javafx "0.1.0-SNAPSHOT"
:description "Sample Program of JavaFX with Clojure"
:dependencies [[org.clojure/clojure "1.5.1"]]
:resource-paths ["lib/jfxrt.jar"]
:main javafx.core
:aot [javafx.core]
)