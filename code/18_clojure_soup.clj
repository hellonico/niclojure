; start jsoup
(use 'jsoup.soup)

: Get all Emoji names concatenated by single bars from 'emoji-cheat-sheet.com':

($ (get! "http://www.emoji-cheat-sheet.com/") 
   "li div:has(span.emoji)" (text) 
   (map #(clojure.string/replace % ":" "")) 
   (clojure.string/join "|")) 