; applescript
(require '(clojure [string :as s]))
(import '[javax.script ScriptEngine ScriptEngineManager])

(def apple-script-engine (.getEngineByName (ScriptEngineManager.) "AppleScript"))
(defn- eval-apple-script [s] (.eval apple-script-engine s (.getContext apple-script-engine)))
(defn q [n] (str \" n \"))
(defn as-array [c] (str \{ (s/join \, (map (comp q name) c)) \}))
(defn tell [appname & s] (eval-apple-script (str "tell application " (q appname) "\n" (s/join s) "\nend tell")))
(defn tell-growl [& s] (apply tell "GrowlHelperApp" s))

(defn is-growl-enabled
  []
  (tell "System Events" "return count of (every process whose name is " (q "GrowlHelperApp") ") > 0"))

(defn growl-register
  [appname available-notifications enabled-notifications]
  (tell "GrowlHelperApp" "set the availableList to " (as-array available-notifications) "\n"
              "set the enabledList to " (as-array enabled-notifications) "\n"
              "register as application " (q appname) " all notifications availableList default notifications enabledList"))

(defn growl
  [appname notification-type message-title message-text]
  (tell "GrowlHelperApp" "notify with name " (q (name notification-type))
              " title " (q message-title)
              " description " (q message-text)
              " application name " (q appname)))

; Usage
;(comment
  (if (is-growl-enabled)
    (growl-register "my-fancy-app" [:alert :info :warn] [:alert :warn]))
 
  (growl "my-fancy-app" :alert "Printer on fire" "Please extinguish the printer")
;)

; http://dougscripts.com/itunes/itinfo/info01.php
; http://www.devdaily.com/apple/itunes-applescript-examples-scripts-mac-reference
;(tell "iTunes" "activate")
(tell "iTunes" "play\nSo Far from Home (For JR)")