(ns edu.sharif.ce.simpledb-test.scenario-tests.insert-test
  (:require
   [clojure.test :as t]
   [edu.sharif.ce.simpledb.core :as simpledb]
   [edu.sharif.ce.simpledb.proto :as simpledb-proto]
   [taoensso.timbre :as timbre]))

(t/deftest Storing-Test-1
  (let [;; initializing an disk-persisting db
        db (simpledb/start-db false 10)]
    ;; --------------------------------- ;;
    ;; Inserting 9 new elements to db should not
    ;; force any data write to disk, because the
    ;; batch limit is not reached.
    (doseq [i (range 9)]
      (simpledb-proto/insert! db {:id i}))
    ;; --------------------------------- ;;
    ;; After inserting another element, all the
    ;; batched inserts will be performed.
    (simpledb-proto/insert! db {:id 9})))

(t/deftest Storing-Test-2
  (let [;; initializing an disk-persisting db
        db (simpledb/start-db false 10)]
    ;; --------------------------------- ;;
    ;; Inserting 5 new elements to db should not
    ;; force any data write to disk, because the
    ;; batch limit is not reached.
    (doseq [i (range 5)]
      (simpledb-proto/insert! db {:id i}))
    ;; --------------------------------- ;;
    ;; After querying the db, all the
    ;; batched inserts will be performed (even when the
    ;; batch-size is not reached.)
    (let [q        '[:find  ?element
                     :where
                     [?element :id 3]]
          res      (-> (simpledb-proto/query db q)
                       set)
          expected #{{:id 3}}]
      (t/is (= res expected)))))
