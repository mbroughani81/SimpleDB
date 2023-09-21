(ns edu.sharif.ce.simpledb.core
  (:require
   [edu.sharif.ce.simpledb.proto :as proto]
   [taoensso.timbre :as timbre]))

(defrecord SimpleDB []
  proto/DB
  (add-collection! [db col])
  (insert! [db col x])
  (query [db q])
  (flush! [db col path]))

(defn start-db []
  (map->SimpleDB {}))

(defn make-collection [])
