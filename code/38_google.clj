; http://code.google.com/p/gdata-java-client/
; http://maurits.wordpress.com/2010/10/27/using-google-data-apis-with-clojure/

(import
 '(com.google.gdata.client.contacts ContactsService)
 '(com.google.gdata.data.contacts ContactFeed)
 '(java.net URL))

(defn contacts-service 
  "set the credential for the contact service"
  [service-name username password]
  (let [cs (new ContactsService service-name)]
    (.setUserCredentials cs username password)
    cs))

(defn get-first-email-address 
  "find the first email address"
  [entry]
  (let [email-addresses (.getEmailAddresses entry)]
    (if (empty? email-addresses)
      "no address"
      (.getAddress (first email-addresses)))))

(defn contacts
  "Collect google contacts for an account"
  [username password]
  (let [
        cs          (contacts-service "clojure-test" username password)
        max-results 10 
        url         (URL. (str "http://www.google.com/m8/feeds/contacts/" username "/full?max-results=" max-results))
        feed        (.getFeed cs url (class (new ContactFeed)))
        entries     (get-entries (.getEntries feed))]
    (println "version: " (.getServiceVersion cs))
    (println "count: " (count entries))
    (println (map #(get-first-email-address %) entries))))

; This library is old, so maybe we should look into this:
; http://code.google.com/p/google-api-java-client/