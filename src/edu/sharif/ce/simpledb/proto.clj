(ns edu.sharif.ce.simpledb.proto)

(defprotocol DB
  (insert! [db x])
  (query [db q])
  (flush! [db path])
  (retrieve [db path]))
