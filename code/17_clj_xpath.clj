; import clj
(use 'clj-xpath.core)

; slurp the remote document
(def xdoc (slurp "https://raw.github.com/Atmosphere/atmosphere/master/samples/jquery-pubsub/pom.xml"))

; show the top element
($x:tag "/*" xdoc)

; show the developer name
($x:text* "/project/developers/developer/name" xdoc)

; describe all the dependencies
(doseq [node ($x "/project/dependencies/dependency" xdoc)]
  (prn (format "%s %s %s"
               ($x:text "./groupId"    node)
               ($x:text "./artifactId" node)
               ($x:text "./version"    node))))