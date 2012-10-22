(require '[clojure.core.reducers :as r])

; simple usage of reducers
(reduce + (r/filter even? (r/map inc [1 1 1 2])))

; collect result in a set without intermediate collection
(into #{} (r/filter even? (r/map inc [1 1 1 2])))