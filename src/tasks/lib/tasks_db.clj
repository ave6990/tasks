(ns tasks.lib.tasks-db
  (:require 
    [clojure.java.jdbc :as jdbc]
    [clojure.string :as string]
    [clojure.pprint :refer [pprint]]
    [clojure.java.shell :refer [sh]]
    [tasks.view.report :as report]))

(def tsk
  {:classname "org.sqlite.jdbc"
   :subprotocol "sqlite"
   :subname "data/tasks.db"}) 

(defmacro if-not-blank
  [sym]
  `(if (not (string/blank? ~sym))
       (str ~(string/replace
               sym
               "-" 
               " ")
            " "
            ~sym)
       ""))

(defn get-data
  ""
  ([where order-by limit]
    (jdbc/query
      tsk
      (string/join "\n"
        (list
           "select
              *,
              group_concat(tag, ' ') as tags
            from view_tasks
            left join
              tags
              on view_tasks.id = tags.task_id"
           (if-not-blank where)
           "group by id"
           (if-not-blank order-by)
           (if-not-blank limit)))))
  ([where order-by]
    (get-data where order-by ""))
  ([where]
    (get-data where "priority, coalesce(master_task, id)" "")))

(defn complete
  ""
  ([id date s]
   (jdbc/update!
    tsk
    :tasks
    {:status 100
     :complete_date date
     :comment s}
    ["id = ?" id]))
  ([id date]
   (complete id date nil)))

(defn add-tags
  [id tags]
  (map (fn [s]
           (jdbc/insert!
             tsk
             :tags
             {:task_id id
              :tag s}))
       tags))

(defn out
  [s]
  (spit
    "/media/sf_YandexDisk/Ermolaev/midb/tasks.html"
    (report/tasks-report
      (get-data s)))
  #_(sh
    "vivaldi"
    "/media/sf_YandexDisk/Ermolaev/midb/tasks.html"))

(defn last-id
  []
  (:id
    (first
      (jdbc/query
        tsk
        "select id from tasks order by id desc limit 1;"))))

;; #tasks
(out "status < 100 and status >= 0")

;; #add#task
(jdbc/insert!
  tsk
  :tasks
  {
    :master_task nil
    :priority 1
    :description "Поверка 29.12.2023."
    :date_to "2024-01-09"
    :comment nil
    :date_from "2023-12-29"
    :status 0
    ;:complete_date "2023-12-07"
  })
(add-tags
  (last-id) 
  (list "verification"))
(out "status < 100 and status >= 0")

;; #update#task
(jdbc/update!
  tsk
  :tasks
  {
    :status -10
    ;:complete_date "2023-12-07"
    ;:priority 20
    ;:date_to "2023-12-11"
    ;:description "9/030038 ОГЗ поверить"
    ;:comment "поверить с другим аккумулятором"
    ;:master_task 66
    ;:date_from ""
  }
  ["id = ?" 62])
(out "status < 100 and status >= 0")

;; #complete/task
(complete 76 "2023-12-21")
(out "status < 100 and status >= 0")

;; #delete/task
(jdbc/delete!
  tsk
  :tasks
  ["id = ?" 77])
(out "status < 100 and status >= 0")

(comment

(require '[tasks.view.report :as report] :reload)
(out "status < 100
      and status >= 0
      --and tags like '%project%'")

(require '[clojure.repl :refer :all])

(dir java.util)

(* (- 2.13 (* 1.33 0.966)) 0.01 0.966)

(- 2.13 (* 1.33 0.966))

)
