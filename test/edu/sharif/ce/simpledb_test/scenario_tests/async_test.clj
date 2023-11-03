(ns edu.sharif.ce.simpledb-test.scenario-tests.async-test
  (:require
   [clojure.test :as t]
   [edu.sharif.ce.simpledb.core :as simpledb]
   [edu.sharif.ce.simpledb.proto :as simpledb-proto]
   [taoensso.timbre :as timbre]))

(t/deftest Test-Async
  (let [db (simpledb/start-db)
        x  {:student-name "ali"
            :id           1}]
    (timbre/info "---case #1---")
    (timbre/info "HERE 1")
    (simpledb-proto/insert! db x {:async? true
                                  :fn     (fn []
                                            (Thread/sleep 5000)
                                            (timbre/info "Entity inserted successfully."))})
    (timbre/info "This message should be printed right after printing \"HERE 1\" ")

    (timbre/info "---case #2---")
    (timbre/info "HERE 2")
    (simpledb-proto/query db
                          '[:find  ?student
                            :where
                            [?student :student-name "ali"]]
                          {:async? true
                           :fn     (fn [x]
                                     (Thread/sleep 5000)
                                     (timbre/info "retrieved data => " x))})
    (timbre/info "This message should be printed right after printing \"HERE 2\" ")))
