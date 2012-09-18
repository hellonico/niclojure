(use 'lamina.core 'lamina.viz)

; defines a channel. A place that receives events
(def ch (channel))

; 
(map* inc ch)
(enqueue ch 1 2 3)

; use graphviz to view the graph
(view-graph ch)