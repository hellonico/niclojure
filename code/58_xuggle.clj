; http://stackoverflow.com/questions/502753/programmatically-convert-a-video-to-flv

(import '(com.xuggle.mediatool ToolFactory))
(import '(com.xuggle.mediatool IMediaDebugListener IMediaDebugListener$Event))

(defn readerRecurse
  "calls .readPacket until there's nothing left to do2"
  [reader]
  (if (not (nil? (.readPacket reader))) ; here .readPacket actually does the processing as a side-effect.
    true                                   ; it returns null when it has MORE ro process, and signals an error when done... 
    (recur reader)))

(defn convert
  "takes video and converts it to a new type of video"
  [videoInput videoOutput]
  (let [reader (ToolFactory/makeReader videoInput)]
    (doto reader
      (.addListener (ToolFactory/makeWriter videoOutput reader))
      (.addListener (ToolFactory/makeDebugListener (into-array [IMediaDebugListener$Event/META_DATA]))))
    (readerRecurse reader)))

; it works !!!! painfully slow.
(convert "/Users/Niko/Downloads/Roppongi2.mp4" "/Users/Niko/nico.mpg")