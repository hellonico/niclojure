; then use korma to query and insert
(use 'korma.db)
(use 'korma.core)

(defdb h2
  {:classname   "org.h2.Driver"
   :subprotocol "h2"
   :subname     "./korma"})

(defentity users
	(entity-fields :first :last))

(insert users
  (values {:first "john" :last "doe"}))