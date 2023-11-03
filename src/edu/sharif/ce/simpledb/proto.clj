(ns edu.sharif.ce.simpledb.proto)

(defprotocol DB
  (insert!
    [db x]
    [db x {:keys [fn async?]}])
  (query
    [db q]
    [db q {:keys [fn async?]}])
  (flush! [db path])
  (retrieve [db path]))
