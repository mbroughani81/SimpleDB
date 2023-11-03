(ns edu.sharif.ce.simpledb.core
  (:require
   [edu.sharif.ce.simpledb.proto :as proto]
   [taoensso.timbre :as timbre]))

(defrecord SimpleDB []
  proto/DB
  (insert! [db x]
    (proto/insert! db x {:fn     nil
                         :async? false}))
  (insert! [db x {:keys [fn async?]}])
  (query [db q]
    (proto/query db q {:fn nil
                       :async? false}))
  (query [db q {:keys [fn async?]}])
  (flush! [db path]))

(defn start-db []
  (map->SimpleDB {}))

(comment
  (def db (start-db))

  db

  (proto/insert! db {:1 "1"} {1231231 :213})

  (proto/insert! db {:1 "1"}))
