(use 'clj-ssh.cli)

; probably not needed
(default-session-options {:strict-host-key-checking :no})


(def host "jp-3.srv.fivecool.net")
(def user "nicolas")

; get the output of the command
(def out
	((ssh host "ls /var/www" :username user) :out))

; parse the output as a vector
(split out #"\n")

(let [agent (ssh-agent {:use-system-ssh-agent false})]
  (add-identity agent "/user/name/.ssh/id_rsa_five")
  (let [session (session agent host :username user {:strict-host-key-checking :no})]))


(sftp host :username user :get "/var/www/index.php" "./download.log")

