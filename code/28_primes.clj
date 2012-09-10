(import [javax.swing JFrame JLabel JTextArea JScrollPane] )
(import [java.awt Dimension BorderLayout])

(defn find-primes[i]
	[1 3 7])

(defn show-primes 
"Find all primes up to i inclusive and present them in a GUI"
 [i]
 (let [fr (JFrame. "Prime Numbers")
lbl (JLabel. (str "Here are all the prime numbers for " i ":"))
ta (JTextArea.)
sp (JScrollPane. ta)
pane (.getContentPane fr)]
(def f (future (find-primes i))) ; asynchronously find the primes while we set up the GUI
(.setPreferredSize lbl (Dimension. 410 20))
(.setPreferredSize sp (Dimension. 410 190))
(.setLineWrap ta true)
(.setSize fr 410 210)
(.add pane lbl BorderLayout/PAGE_START)
(.add pane sp BorderLayout/CENTER)
(.pack fr)
(.setVisible fr true)
(dotimes [n (count @f)] (.setText ta (str (.getText ta) (@f n) "\n")))))

(show-primes 2)