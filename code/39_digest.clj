(require 'digest)
(use 'clojure.java.io)

; foo !
(digest/md5 "foo")

; from file
(digest/sha-256 (as-file "project.clj"))