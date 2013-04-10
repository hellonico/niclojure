(ns javafx.core
(:gen-class :extends javafx.application.Application)
(:import [javafx.application Application]
[javafx.scene Scene Group]
[javafx.stage Stage StageBuilder]))
 
(defn -start [this stage]
(let [scene (Scene. (Group.))
builder (doto (StageBuilder/create)
(.title "test")
(.scene scene)
(.x 100)
(.y 100)
(.width 300)
(.height 200)
)]
(.show (.build builder))))
 
(defn -main [& args]
	(Application/launch javafx.core args))