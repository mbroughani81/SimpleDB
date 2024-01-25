(ns edu.sharif.ce.simpledb-test.scenario-tests.insert-test
  (:require
   [clojure.test :as t]
   [edu.sharif.ce.simpledb.core :as simpledb]
   [edu.sharif.ce.simpledb.proto :as simpledb-proto]
   [taoensso.timbre :as timbre]))

(t/deftest Insert-Test
  (let [db (simpledb/start-db true )]
    ;; inserting a simple map
    ))
