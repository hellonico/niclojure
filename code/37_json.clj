(use '[cheshire.core])

(generate-string {:foo "bar" :baz 5})

;; parse some json
(parse-string "{\"foo\":\"bar\"}")
;; => {"foo" "bar"}

;; parse some json and get keywords back
(parse-string "{\"foo\":\"bar\"}" true)
;; => {:foo "bar"}

;; parse some json and munge keywords with a custom function
(parse-string "{\"foo\":\"bar\"}" (fn [k] (keyword (.toUpperCase k))))
;;; => {:FOO "bar"}
