(ns edu.sharif.ce.simpledb-test.scenario-tests.create-collection-test
  (:require
   [clojure.test :as t]
   [edu.sharif.ce.simpledb.core :as simpledb]
   [edu.sharif.ce.simpledb.proto :as simpledb-proto]
   [taoensso.timbre :as timbre]))

(t/deftest simpledb-test
  (let [db  (simpledb/start-db)
        col (simpledb/make-collection)]
    ;; cannot insert to a collection not created yet!
    ;; should throw an error
    (timbre/info "---case #1---")
    (try
      (simpledb-proto/insert! db col {:id         1
                                      :name       "ali"
                                      :student-id "123456"})
      (catch Exception e
        (timbre/info "exception => " e)))
    ;; creating the collection
    (timbre/info "---case #2---")
    (simpledb-proto/add-collection! db col)
    (simpledb-proto/insert! db col {:id         1
                                    :name       "ali"
                                    :student-id "123456"})))
