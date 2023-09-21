(ns edu.sharif.ce.simpledb-test.scenario-tests.insert-test
  (:require
   [clojure.test :as t]
   [edu.sharif.ce.simpledb.core :as simpledb]
   [edu.sharif.ce.simpledb.proto :as simpledb-proto]
   [taoensso.timbre :as timbre]))

(t/deftest simpledb-test
  (let [db  (simpledb/start-db)
        col (simpledb/make-collection)
        _   (simpledb-proto/add-collection! db col)]
    ;; inserting a simple map
    (timbre/info "---case #1---")
    (simpledb-proto/insert! db col {:id         1
                                    :name       "ali"
                                    :student-id "123456"})

    ;; inserting things other than map should throw
    ;; exception
    (timbre/info "---case #2---")
    (try
      (simpledb-proto/insert! db col '(1 2 3 4))
      (catch Exception e
        (timbre/info "exception => " e)))))
