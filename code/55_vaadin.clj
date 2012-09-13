(ns test.tlp)
 
(defn main [args]
  (proxy [com.vaadin.Application] []
    (init [] 
      (let [app this]
        (.setMainWindow this
          (doto (new com.vaadin.ui.Window "Test application")
            (.addComponent 
              (new com.vaadin.ui.Label "Hello Vaadin/LISP user!"))
            (.addComponent 
              (doto (new com.vaadin.ui.Button "button")
                (.addListener (proxy [com.vaadin.ui.Button$ClickListener] []
                                (buttonClick [event] (. (. app (getMainWindow)) (showNotification "test")))))))))))))