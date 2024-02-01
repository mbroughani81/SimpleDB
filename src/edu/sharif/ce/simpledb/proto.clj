(ns edu.sharif.ce.simpledb.proto)

(defprotocol DB
  (insert!
    [db x]
    [db x {:keys [fn async?]}])
  (query
    [db q]
    [db q {:keys [fn async?]}])
  (piped-query
    [db q xf])
  (flush! [db path])
  (retrieve [db path]))
