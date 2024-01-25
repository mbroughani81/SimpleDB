(ns edu.sharif.ce.simpledb.core
  (:require
   [edu.sharif.ce.simpledb.proto :as proto]
   [taoensso.timbre :as timbre]))

(defrecord SimpleDB [batch-size in-memory?]
  proto/DB
  (insert! [db x]
    (proto/insert! db x {:fn     nil
                         :async? false}))
  (insert! [db x {:keys [fn async?]}])
  (query [db q]
    (proto/query db q {:fn     nil
                       :async? false}))
  (query [db q {:keys [fn async?]}])
  (flush! [db path]))

(defn start-db
  ([in-memory? batch-size]
   (when (and in-memory?
              (some? batch-size))
     (throw (ex-info "When in-memory is true, batch size must be nil."
                     {:batch-size batch-size
                      :in-memory? in-memory?})))
   (map->SimpleDB {:batch-size batch-size
                   :in-memory? in-memory?}))
  ([]
   (start-db true nil)))

(comment
  (def db (start-db))

  db

  (proto/insert! db {:1 "1"} {1231231 :213})

  (proto/insert! db {:1 "1"}))
