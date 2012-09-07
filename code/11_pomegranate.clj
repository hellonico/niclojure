=> (use '[cemerick.pomegranate :only (add-dependencies)])
nil
=> (add-dependencies :coordinates '[[incanter "1.2.3"]]
                     :repositories (merge cemerick.pomegranate.aether/maven-central
                                          {"clojars" "http://clojars.org/repo"}))
;...add-dependencies returns full dependency graph...
=> (require '(incanter core stats charts))
nil