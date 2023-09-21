(ns edu.sharif.ce.simpledb.proto)

(defprotocol DB
  (add-collection! [db col])
  (insert! [db col x])
  (query [db q])
  (flush! [db col path])
  (retrieve [db col path]))
