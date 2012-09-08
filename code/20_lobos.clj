; create SQL tables with lobos
(use 'lobos.connectivity
     'lobos.core
     'lobos.schema)
 
(def h2
  {:classname   "org.h2.Driver"
   :subprotocol "h2"
   :subname     "./korma"})
 
 ; seems lobos is not working anymore without clojure-contrib
(create h2
  (table :users
    (varchar :first 100)
    (varchar :last 100)))