; then use korma to query and insert
(use 'korma.db)
(use 'korma.core)

; define the connection
(defdb h2
  {:classname   "org.h2.Driver"
   :subprotocol "h2"
   :subname     "./korma"})

; this will be used later 
(defentity users
	(entity-fields :first :last))

; insert as hash map
(insert users (values {:first "john" :last "doe"}))

; query all users
(select users)

; delete all users with last = "doe"
(delete users
  (where {:last "doe"}))

; query all users
(select users)  