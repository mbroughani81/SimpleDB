(ns edu.sharif.ce.simpledb-test.scenario-tests.query-test
  (:require
   [clojure.test :as t]
   [edu.sharif.ce.simpledb.core :as simpledb]
   [edu.sharif.ce.simpledb.proto :as simpledb-proto]
   [taoensso.timbre :as timbre]))

(def class-A (atom '()))
(def class-B (atom '()))
(def class-C (atom '()))

(defn insert-init-data [db col-students col-teachers]
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
      (swap! class-A #(conj % stu-a))
      (swap! class-B #(conj % stu-b))
      (swap! class-C #(conj % stu-c))))
  (simpledb-proto/insert! db col-teachers {:teacher-id 0
                                           :name       "Teacher-0"
                                           :classes    ["A" "B"]})
  (simpledb-proto/insert! db col-teachers {:teacher-id 1
                                           :name       "Teacher-1"
                                           :classes    ["B" "C"]}))

(t/deftest simpledb-test
  (let [db           (simpledb/start-db)
        col-students (simpledb/make-collection)
        col-teachers (simpledb/make-collection)
        _            (simpledb-proto/add-collection! db col-students)
        _            (simpledb-proto/add-collection! db col-teachers)
        _            (insert-init-data db col-students col-teachers)
        _            (timbre/info "ok => " @class-A)]
    ;; query with incorrect format should be rejected.
    (timbre/info "---case #1---")
    (try
      (let [q   {:invalid-query-format "?"}
            res (simpledb-proto/query db q)])
      (catch Exception e
        (timbre/info "exception => " e)))

    (timbre/info "---case #2---") ;; student of class A
    (let [q               '[:find  ?student
                            :where
                            [?student :class "A"]]
          res             (-> (simpledb-proto/query db q)
                              set)
          case-2-expected (set @class-A)]
      (t/is (= case-2-expected res)))
    (timbre/info "---case #3---") ;; students of teacher 0
    (let [q               '[:find  ?student
                            :where
                            [?student :class ?c]
                            [?teacher :name "Teacher-0"]
                            [?teacher :has-class ?c]
                            :def
                            (:has-class {?teacher ?c}
                                        (contains? (-> teacher?
                                                       :classes)
                                                   ?c))]
          res             (-> (simpledb-proto/query db q)
                              set)
          case-3-expected (-> @class-A
                              (concat @class-B)
                              set)]
      (t/is (= case-3-expected res)))
    (timbre/info "---case #4---") ;; common students of teacher 0 and 1
    (let [q               '[:find  ?student
                            :where
                            [?student :class ?c]
                            [?teacher-0 :name "Teacher-0"]
                            [?teacher-1 :name "Teacher-1"]
                            [?teacher-0 :has-class ?c]
                            [?teacher-1 :has-class ?c]
                            :def
                            (:has-class {?teacher ?c}
                                        (contains? (-> teacher?
                                                       :classes)
                                                   ?c))]
          res             (-> (simpledb-proto/query db q)
                              set)
          case-4-expected (-> @class-B
                              set)]
      (t/is (= case-4-expected res)))))

(comment
  (t/run-tests)

  @class-A)
