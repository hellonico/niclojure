; http://docs.atlassian.com/jira/REST/latest/

(require '[clj-http.client :as http])
(require '[clojure.data.json :as json])

(def jira-base-url
  "https://jira.fivecool.org/rest/api")

(def auth ["nico" "umeboshiplumpatties"])    

(defn get-issue
	[id]
	(json/read-json (
		 (http/get 
		 	(str jira-base-url "/latest/issue/" id) 
		    {:basic-auth auth :accept :json :content-type "application/json"}) :body)))

;(def sample-issue (get-issue "PSH-228"))

(defn get-dashboard
	"Return a list of dashboard. But only the system ones so not very interesting"
	[]
	(json/read-json ((http/get (str jira-base-url "/2/dashboard")) :body)))