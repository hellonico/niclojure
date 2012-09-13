(defn- as-megabytes
"Given a sequence of byte amounts, return megabyte amounts
as string, with an M suffix."
[memory]
(map #(str (int (/ % 1024 1024)) "M") memory))
 
(defn- as-percentage
"Given a pair of values, return the percentage as a string."
[[a b]]
(str (int (* 100 (/ a b))) "%"))
 
(defn- memory-bean
"Return the MemoryMXBean."
[]
(java.lang.management.ManagementFactory/getMemoryMXBean))
 
(defn- heap-usage
"Given a MemoryMXBean, return the heap memory usage."
[^java.lang.management.MemoryMXBean bean]
(.getHeapMemoryUsage bean))
 
(defn- heap-used-max
"Given heap memory usage, return a pair of used/max values."
[^java.lang.management.MemoryUsage usage]
[(.getUsed usage) (.getMax usage)])
 
(defn memory-usage
"Return percentage, used, max heap as strings."
[]
(let [used-max (-> (memory-bean) (heap-usage) (heap-used-max))]
(cons (as-percentage used-max)
(as-megabytes used-max))))
 
(defn- operating-system-bean
"Return the OperatingSystemMXBean."
[]
(java.lang.management.ManagementFactory/getOperatingSystemMXBean))
 
(defn- cpus
"Given an OSMXBean, return the number of processors."
[^java.lang.management.OperatingSystemMXBean bean]
(.getAvailableProcessors bean))
 
(defn- load-average
"Given an OSMXBean, return the load average for the last minute."
[^java.lang.management.OperatingSystemMXBean bean]
(.getSystemLoadAverage bean))
 
(defn- cpu-percentage
"Given the number of CPUs and the load-average, return the
percentage utilization as a string."
[[cpus load-average]]
(str (int (* 100 (/ load-average cpus))) "%"))
 
(defn cpu-usage
"Return utilization (as a string) and number of CPUs and load average."
[]
(let [bean (operating-system-bean)
data ((juxt cpus load-average) bean)]
(cons (cpu-percentage data)
data)))

(println (cpu-usage))
(println (memory-usage))