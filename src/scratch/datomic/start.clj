(ns scratch.datomic.start
  (:use [datomic.api :only [q db] :as d]))

;; adapted from http://www.datomic.com/company/resources/getting-started

;;; (doc d/q)

;; -------------------------
;; datomic.api/q
;; ([query & inputs])
;;   Executes a query against inputs.
;;
;;    Inputs are data sources e.g. a database value retrieved from
;;    Connection.db, a list of lists, and/or rules. If only one data
;;    source is provided, no :in section is required, else the :in
;;    section describes the inputs.
;;
;;    query can be a map, list, or string:
;;
;;    The query map form is {:find vars :in sources :where clauses}
;;    where vars, sources and clauses are lists.
;;
;;    The query list form is [:find ?var1 ?var2 ...
;;                            :in $src1 $src2 ...
;;                            :where clause1 clause2 ...]
;;    The query list form is converted into the map form internally.
;;
;;    The query string form is a string which, when read, results
;;    in a query list form or query map form.
;;
;;    Query parse results are cached.
;;
;;    Returns a collection of result tuples, as list, with the
;;    first item in each list corresponding to ?var1, etc.
;;=> nil

(def uri "datomic:mem://hello")
;;=> #'user/uri

(d/create-database uri)
;;=> true

(def conn (d/connect uri))
;;=> #'user/conn

conn
;;=> #<LocalConnection datomic.peer.LocalConnection@79a86488>

(d/transact conn [["db/add" (d/tempid "db.part/user") "db/doc" "hello world"]])
;;=> #<common$completed_future$reify__217@1cac622a: {:t 1000}>

(def results (d/q '[:find ?entity :where [?entity :db/doc "hello world"]] (db conn)))
;;=> #'user/results

results
;;=> #<HashSet [[17592186045417]]>

(for [x results] (d/entity (db conn) (first x)))
;;=> ({:db/id 17592186045417})

(for [x results] (:db/doc (d/entity (db conn) (first x))))
;;=> ("hello world")

(defn run-query
  [query conn]
  (let [results (d/q query (d/db conn))]
        (println "Found" (count results) "results")
        (doseq [x results] (println x))))
;;=> #'user/run-query

(def data (read-string (slurp "samples/seattle/seattle-schema.dtm")))
;;=> #'user/data

@(d/transact conn data)
;; #<common$completed_future$reify__217@22e1469c: {:t 1000}>
;;=> {:t 1000}

(def data (read-string (slurp "samples/seattle/seattle-data0.dtm")))
;;=> #'user/data

@(d/transact conn data)
;;=> {:t 1021}

(run-query '[:find ?c :where [?c :community/name]] conn)
;;=> Found 150 results
;;=> [17592186045520]
;;=> [17592186045518]
;;=> [17592186045516]
;;=> ...
;;=> [17592186045566]
;;=> nil
