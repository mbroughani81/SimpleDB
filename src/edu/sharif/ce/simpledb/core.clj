(ns edu.sharif.ce.simpledb.core
  (:require
   [edu.sharif.ce.simpledb.proto :as proto]))

(defn start-db [])

(defn make-collection [])

(atom {})

(defrecord SimpleDB []
  proto/DB
  (add-collection! [db col])
  (insert! [db col x])
  (query [db col q])
  (flush! [db col path])
  (<-db-contents [db]))
