(require '(incanter core stats charts))

(use '[cemerick.pomegranate :only (add-dependencies)])

(add-dependencies :coordinates '[[incanter "1.2.3"]]
                     :repositories (merge cemerick.pomegranate.aether/maven-central
                                          {"clojars" "http://clojars.org/repo"}))

 (require '(incanter core stats charts))