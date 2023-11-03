(ns edu.sharif.ce.simpledb.core
  (:require
   [edu.sharif.ce.simpledb.proto :as proto]
   [taoensso.timbre :as timbre]))

(defrecord SimpleDB []
  proto/DB
  (insert! [db x])
  (query [db q])
  (flush! [db path]))

(defn start-db []
  (map->SimpleDB {}))
