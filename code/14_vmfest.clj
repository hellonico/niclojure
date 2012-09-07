; you can define your machine this way:
(def my-machine 
     (instance my-server "my-vmfest-vm" 
        {:uuid "/Users/tbatchelli/imgs/vmfest-Debian-6.0.2.1-64bit-v0.3.vdi"}  
        {:memory-size 512
         :cpu-count 1
         :network [{:attachment-type :host-only
                    :host-only-interface "vboxnet0"}
                   {:attachment-type :nat}]
         :storage [{:name "IDE Controller"
                    :bus :ide
                    :devices [nil nil {:device-type :dvd} nil]}]
         :boot-mount-point ["IDE Controller" 0]}))

; then this is the way to start/stop.. any of the virtual machines you have
(start my-machine)
(pause my-machine)
(resume my-machine)
(stop my-machine)
(destroy my-machine)