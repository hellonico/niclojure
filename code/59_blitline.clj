; http://stackoverflow.com/questions/12433314/how-to-translate-curl-call-to-blitline-json-service-into-clojure
; 
; curl "http://api.blitline.com/job" -d json='{ "application_id": "sgOob0A3b3RdYaqwTEJCpA", "src" : "http://www.google.com/logos/2011/yokoyama11-hp.jpg", "functions" : [ {"name": "blur", "params" : {"radius" : 0.0, "sigma" : 2.0}, "save" : { 
; "image_identifier" : "some_id" }} ]}' 

; http://www.blitline.com/docs/quickstart

(require '[clj-http.client :as http])
(require '[clojure.data.json :as json])

(def post
(http/post "http://api.blitline.com/job" {
	:body 
		(json/json-str 
			{ "json" 
    			{ "application_id" "sgOob0A3b3RdYaqwTEJCpA"
			      "src" "http://www.google.com/logos/2011/yokoyama11-hp.jpg"
			      "functions" [ {
			  		"name" "blur"
			  		"params" {
			  			"radius" 0.0
			  			"sigma" 2.0
			  		 }
			  		"save" { "image_identifier" "some_id" }}]}}) 
	:body-encoding "UTF-8"
   	:content-type :json
   	:accept :json
   	}))

(pprint (json/read-json (:body post)))