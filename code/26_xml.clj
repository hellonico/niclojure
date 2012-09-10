(require '[clojure.xml :as xml])
(require '[clojure.zip :as zip])
(use 'clojure.pprint)
(use 'clojure.data.zip.xml)

(defn parse-str [s]
    (zip/xml-zip (xml/parse (new org.xml.sax.InputSource
                            (new java.io.StringReader s)))))
(defn parse-file [f]
    (zip/xml-zip (xml/parse (new org.xml.sax.InputSource
                            (new java.io.FileReader f)))))

(def atom1 (parse-str "<?xml version='1.0' encoding='UTF-8'?>
                      <feed xmlns='http://www.w3.org/2005/Atom'>
                        <id>tag:blogger.com,1999:blog-28403206</id>
                        <updated>2008-02-14T08:00:58.567-08:00</updated>
                        <title type='text'>n01senet</title>
                        <link rel='alternate' type='text/html' href='http://n01senet.blogspot.com/'/>
                        <entry>
                          <id>1</id>
                          <published>2008-02-13</published>
                          <title type='text'>clojure is the best lisp yet</title>
                          <author><name>Chouser</name></author>
                        </entry>
                        <entry>
                          <id>2</id>
                          <published>2008-02-07</published>
                          <title type='text'>experimenting with vnc</title>
                          <author><name>agriffis</name></author>
                        </entry>
                      </feed>
                      "))

;(pprint atom1)
(xml-> atom1 :entry text)