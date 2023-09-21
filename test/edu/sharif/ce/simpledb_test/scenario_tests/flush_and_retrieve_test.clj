(ns edu.sharif.ce.simpledb-test.scenario-tests.flush-and-retrieve-test
  (:require
   [clojure.test :as t]
   [edu.sharif.ce.simpledb.core :as simpledb]
   [edu.sharif.ce.simpledb.proto :as simpledb-proto]
   [taoensso.timbre :as timbre]))

(defn insert-init-data [db col-students col-teachers]
  (let [students (atom '())]
    (doseq [id (range 10)]
      (let [stu-a {:student-id id
                   :name       (str "stu-" id)
                   :gender     (if (odd? id) "male" "female")
                   :class      "A"}
            stu-b {:student-id id
                   :name       (str "stu-" (+ 10 id))
                   :gender     (if (odd? id) "male" "female")
                   :class      "B"}
            stu-c {:student-id id
                   :name       (str "stu-" (+ 20 id))
                   :gender     (if (odd? id) "male" "female")
                   :class      "C"}]
        (simpledb-proto/insert! db col-students stu-a)
        (simpledb-proto/insert! db col-students stu-b)
        (simpledb-proto/insert! db col-students stu-c)
        (swap! students #(conj % stu-a))
        (swap! students #(conj % stu-b))
        (swap! students #(conj % stu-c))))
    (simpledb-proto/insert! db col-teachers {:teacher-id 0
                                             :name       "Teacher-0"
                                             :classes    ["A" "B"]})
    (simpledb-proto/insert! db col-teachers {:teacher-id 1
                                             :name       "Teacher-1"
                                             :classes    ["B" "C"]})
    (-> @students)))

(t/deftest simpledb-test
  (let [db           (simpledb/start-db)
        col-students (simpledb/make-collection)
        col-teachers (simpledb/make-collection)
        _            (simpledb-proto/add-collection! db col-students)
        _            (simpledb-proto/add-collection! db col-teachers)
        students     (insert-init-data db col-students col-teachers)
        path         "some-path-in-project"
        _            (simpledb-proto/flush! db col-students path)
        new-db       (simpledb/start-db)
        q            '[:find ?student
                       :where
                       [?student :class _]]]
    (timbre/info "---case #1---")
    ;; at first, it should not have any collection.
    ;; so it throws error
    (try
      (simpledb-proto/query new-db q)
      (catch Exception e
        (timbre/info "exception => " e)))

    (timbre/info "---case #2---")
    ;; after creating the collection, using the flushed file,
    ;; we can query the db.
    (let [case-2-expected (set students)]
      (simpledb-proto/retrieve db
                               (simpledb/make-collection)
                               path)
      (let [res (simpledb-proto/query new-db q)]
        (t/is (= case-2-expected res))))))

(comment
  (t/run-tests)

  @class-A)
