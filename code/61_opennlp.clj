;(use 'clojure.pprint) ; just for this documentation

(use 'opennlp.nlp)
(use 'opennlp.treebank) ; treebank chunking, parsing and linking lives here

(def get-sentences (make-sentence-detector "code/en-sent.bin"))
(def tokenize (make-tokenizer "code/en-token.bin"))
(def detokenize (make-detokenizer "code/english-detokenizer.xml"))
(def pos-tag (make-pos-tagger "code/en-pos-maxent.bin"))
(def name-find (make-name-finder "code/namefind/en-ner-person.bin"))
(def chunker (make-treebank-chunker "code/en-chunker.bin"))

(pprint (chunker (pos-tag (tokenize "The override system is meant to deactivate the accelerator when the brake pedal is pressed."))))

(use 'opennlp.tools.filters)
; only nouns
(pprint (nouns (pos-tag (tokenize "Mr. Smith gave a car to his son on Friday."))))
; only verbs
(pprint (verbs (pos-tag (tokenize "Mr. Smith gave a car to his son on Friday."))))