    (use 'clj-growlnotify)
    
    (growl-notify "my title" "my message")
    (growl-notify "my title" "my message" :name "myapp" :sticky true)
    (growl-notify {:title "my title" :message "my message" :name "myapp" :sticky true})