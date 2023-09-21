(ns edu.sharif.ce.simpledb.proto)

(defprotocol DB
  (add-collection! [db col])
  (insert! [db col x])
  (query [db col q])
  (flush! [db col path])
  (<-db-contents [db]))
