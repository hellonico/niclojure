; method that targets CTO
(defn fact
  [n]
  (if (zero? n)
      1
      ; see the *' ?  it is used for BigDecimeal Conversion
      (*' n (fact (dec n)))))

; Compile it with ctco
(ctco
  (defn fact
    [n]
    (if (zero? n)
        1
        (*' n (fact (dec n))))))

; and use it the normal way
(println (fact 457))

; the code generated will be like this:
(comment

(let [tramp (fn [thunk]
              (if (get (meta thunk) :thunk)
                (recur (thunk))
                thunk))]
  (let [apply-k (fn [k a]
                  (if (fn? k)
                    (k a)
                    a))]
  (defn fact
    ([n] (tramp (fact n nil)))
    ([n k]
       (if (zero? n)
         (fn [] (with-meta (apply-k k 1) {:thunk true}))
         (with-meta
           (fn []
             (fact (dec n)
                   (fn [s]
                     (fn []
                       (apply-k k (* n s))))))
           {:thunk true})))))
)