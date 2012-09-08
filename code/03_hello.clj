; yes indeed
(println "hello clojure!")

; include a new namespace in the current namespace
; and use a custom name
(require '[clojure.string :as string])

; use the newly imported namespace
(def new_string (string/replace "Hello World" "World" "Nico"))

; print
(println new_string)