(ns my.service
  (:require [clojurewerkz.quartzite.scheduler :as qs])
  (:use [clojurewerkz.quartzite.jobs :only [defjob]]))

(defn -main
  [& m]
  (qs/initialize)
  (qs/start))

(defjob NoOpJob
  [ctx]
  (comment "Does nothing"))