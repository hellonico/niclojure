; make sure you set up the library properly if on osx
; http://blog.iharder.net/2009/08/18/rxtx-java-6-and-librxtxserial-jnilib-on-intel-mac-os-x/

(use 'serial-port)

; list ports
(list-ports)

; open the port
(def port (open "/dev/tty.HUAWEIMobile-Pcui"))

; display whats coming through
(on-byte port #(println %))

; bytes
(byte-array [(byte 0) (byte 1) (byte 2)])
; write
(write-int-seq port bytes)

; close 
(close port)