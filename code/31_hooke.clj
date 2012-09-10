(use 'robert.hooke)

(defn examine [x]
  (println x))

(defn microscope
  "The keen powers of observation enabled by Robert Hooke allow
  for a closer look at any object!"
  [f x]
  (f (.toUpperCase x)))

(defn doubler [f & args]
  (apply f args)
  (apply f args))

(defn telescope [f x]
  (f (apply str (interpose " " x))))

(add-hook #'examine #'microscope)
(add-hook #'examine #'doubler)
(add-hook #'examine #'telescope)

(examine "something")

; now we also have two append/prepend awesome macros 
; to speed up adding hooooooks.
; see how it goes
(prepend print-name
  (print "The following person is awesome:"))

(print-name "Gilbert K. Chesterton")