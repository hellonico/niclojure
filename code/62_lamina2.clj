(use 'lamina.core 'lamina.viz)

(def ch (channel))

(map* inc ch)
(map* dec ch)
(filter* odd? ch)

(enqueue ch 1 2 3 4 5)

; use graphviz to view the graph
(view-graph ch)