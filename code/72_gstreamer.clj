(ns audio
  (:import [org.gstreamer Gst Pipeline Bin Element ElementFactory State
                          StateChangeReturn Bus$EOS Bus$ERROR Bus$STATE_CHANGED]
    [org.gstreamer.io InputStreamSrc OutputStreamSink]
    [java.io InputStream OutputStream])
 (:use clojure.contrib.logging))

(Gst/init)

(defn transcode [^InputStream in ^OutputStream out]
  (let [id (gensym (quote transcode))
        src (InputStreamSrc. in (str "in stream " id))
        dec0 (ElementFactory/make "amrparse" (str "amr parser " id))
        dec1 (ElementFactory/make "amrnbdec" (str "amr decoder " id))
        enc (doto (ElementFactory/make "lamemp3enc" (str "mp3 encoder " id))
               (.set "mono" true)
               (.set "target" 0)
               (.set "quality" 2))
        out (doto (OutputStreamSink. out (str "out stream " id))
               (.setSync false))
        pipe (doto (Pipeline. (str "transcoder pipe " id))
                (.add src)
                (.add dec0)
                (.add dec1)
                (.add enc)
                (.add out))
        clean (fn []
                (.setState src nil)
                (.setState dec0 nil)
                (.setState dec1 nil)
                (.setState enc nil)
                (.setState out nil)
                (.setState pipe nil))]
    (prn "starting transcode " id)
    (.link src dec0)
    (.link dec0 dec1)
    (.link dec1 enc)
    (.link enc out)

    (doto (.getBus pipe)
      (.connect 
        (reify Bus$EOS
          (endOfStream [this src]
            (prn "Bus finished " src)
            (clean)
            (Gst/quit))))
      (.connect
        (reify Bus$ERROR
          (errorMessage [this src code msg]
          (prn "Bus Error " src code msg)
          (clean)
          (Gst/quit))))
      (.connect 
        (reify Bus$STATE_CHANGED
          (stateChanged [this src old now pending]
          (prn "Bus State change " src old now pending)))))
    (.setState pipe State/PLAYING)
    (Gst/main)))