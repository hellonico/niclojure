(ns hamweather.core
    (:gen-class 
    :name net.hellonico.hamweather
    :main true
    :state state
    :constructors {[String String] []}
    :init init
    :methods [
      [getWeather [String] Object]
      [getWeatherIcon [String] java.net.URL]
      ;[setKeys [String String] void]
    ])
    (:use [clojure.pprint])
    (:require [clojure.string :as string])
    (:require [clojure.data.json :as json])
    (:import [java.io BufferedReader InputStreamReader])
    (:import [java.net URL]))

(def client_id "fKiQ3R8p2azHyTn5xKsDL")
(def client_secret "LNH7U7R671IoOksFMJveq5fxIrWzMiTGvrKufBpB")

(def BASE_URL "http://api.aerisapi.com/")

(defn parse_params[map_of_map]
  (string/join "&" (map #(string/join "=" %) map_of_map)))
  
(defn prepare_url [endpoint params id]
    (URL. (str BASE_URL endpoint "/" id "?" (parse_params params))))
    
(defn get_hamweather
    [endpoint id params]
    (let [ url (prepare_url endpoint params id)
          reader (BufferedReader. (InputStreamReader. (.openStream url)))]
    (json/read-json reader)))

(defn whats_the_weather[id]
  (println (get_hamweather "forecasts" id [["client_id" client_id] ["client_secret" client_secret]["limit" "1"]])))

; java interop
(defn -init[key1 key2] [[] (atom {:client key1 :secret key2 })])
(defn setfield [this key value] (swap! (.state this) into {key value}))
(defn getfield [this key] (@(.state this) key))
(defn -toString[this] (getfield this :client))
 
(defn -setKeys[this clientId secret]
    (setfield this :client clientId)
    (setfield this :secret secret)
    this)
             
(defn -getWeather[this id]
    (get_hamweather "forecasts" 
        id 
        [["client_id" (getfield this :client)] 
         ["client_secret" (getfield this :secret)] 
         ["limit" "1"]]))
         
; (defn -getWeatherIcon[this id]
;   (let [w 
;     (get_hamweather "forecasts" 
;         id 
;         [["client_id" (getfield this :client)] 
;          ["client_secret" (getfield this :secret)] 
;          ["limit" "1"]])
;          icon_path (str "/icons/" 
;                       ((first ((first (w :response)) :periods)) :icon))
;          ]
;     (.getResource (.getClass this) icon_path)))
         
; this is the java way of calling the method
(defn -main[& args]
  (println 
      (.getWeather 
          (net.hellonico.hamweather. 
            "fKiQ3R8p2azHyTn5xKsDL" 
            "LNH7U7R671IoOksFMJveq5fxIrWzMiTGvrKufBpB" ) 
          (first args))))