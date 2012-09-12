(use 'xml-picker-seq.core)

(with-open [rdr (clojure.java.io/reader "http://www.loc.gov/standards/marcxml/xml/collection.xml")]
  (let [context (nu.xom.XPathContext. "marc" "http://www.loc.gov/MARC21/slim")
        titles (xml-picker-seq.core/xml-picker-seq
                rdr "record"
                (xml-picker-seq.core/xpath-query "//marc:datafield[@tag = '245']/marc:subfield[@code = 'a']"
                                                 :context context :final-fn first))]
    (doseq [title titles]
      (println title))))

(use 'clojure.java.io)
(defn contains-one[list string]
	(doseq [item list]
		
		)
	)

;(xml-picker-seq.core/xml-picker-seq rdr "entry" (xml-picker-seq.core/xpath-query "//")))
(with-open [rdr (reader "/Users/Niko/JMdict.xml")]	
	(doseq [l (filter #(= (.indexOf % "<pos") -1) (line-seq rdr))]
		(spit "dict.xml" (str l "\n") :append true)))