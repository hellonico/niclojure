; http://stackoverflow.com/questions/12433314/how-to-translate-curl-call-to-blitline-json-service-into-clojure
; 
; curl "http://api.blitline.com/job" -d json='{ "application_id": "sgOob0A3b3RdYaqwTEJCpA", "src" : "http://www.google.com/logos/2011/yokoyama11-hp.jpg", "functions" : [ {"name": "blur", "params" : {"radius" : 0.0, "sigma" : 2.0}, "save" : { 
; "image_identifier" : "some_id" }} ]}' 

; how to get this to work with: https://github.com/dakrone/clj-http
; http://www.blitline.com/docs/quickstart

(require '[clj-http.client :as http])
(require '[clojure.data.json :as json])

(http/post "http://api.blitline.com/job" {
	:body 
		(json/json-str 
			{ "application_id" "sgOob0A3b3RdYaqwTEJCpA"
			  "src" "http://www.google.com/logos/2011/yokoyama11-hp.jpg"
			  "functions" [ {
			  		"name" "blur"
			  		"params" {
			  			"radius" 0.0
			  			"sigma" 2.0
			  		}
			  		}]}) 
	;:headers {"X-Api-Version" "2"}
	;:body-encoding "UTF-8"
   	;:content-type :json
   	;:socket-timeout 1000
   	;:conn-timeout 1000
   	;:accept :json
   	})