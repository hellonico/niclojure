(use '(incanter core stats charts))

; Try an example: sample 1,000 values from a standard-normal distribution and view a histogram: 
(view (histogram (sample-normal 1000)))

; this is saving the file as png
; wow. so fast.
(save (histogram (sample-normal 1000)) "test.png")

; Below only works if you have latex installed
; (def eq (str "f(x)=\\frac{1}{\\sqrt{2\\pi \\sigma^2}}" "e^{\\frac{-(x - \\mu)^2}{2 \\sigma^2}}"))
; (view (latex eq))
; (save (latex eq) filename)