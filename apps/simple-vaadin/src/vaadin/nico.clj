(ns test.tlp
	(import 
		[com.vaadin.ui 
		Window 
		Label 
		Button 
		Button$ClickListener]))

(defn main [args]
  	(proxy [com.vaadin.Application] []
    (init [] 
      (let [app this]
        (.setMainWindow this
          (doto (Window. "Test application")
            (.addComponent 
              (Label. "Hello Vaadin!"))
            (.addComponent 
              (doto (Button. "button")
                (.addListener 
					(proxy [Button$ClickListener] []
                        (buttonClick [event] (. (. app (getMainWindow)) 
							(showNotification "Time for sushi")))))))))))))