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
      (string/join " "
        (list
           "select
              *,
              group_concat(tag, ' ') as tags
            from tasks
            left join
              tags
              on tasks.id = tags.task_id"
           (if-not-blank where)
           "group by id"
           (if-not-blank order-by)
           (if-not-blank limit)))))
  ([where order-by]
    (get-data where order-by ""))
  ([where]
    (get-data where "priority, coalesce(master_task, id)" "")))

;; #tasks
(spit
  "/media/sf_YandexDisk/Ermolaev/midb/tasks.html"
  (report/tasks-report
    (get-data "status < 100")))
(sh
  "vivaldi"
  "/media/sf_YandexDisk/Ermolaev/midb/tasks.html")

;; #add#task
(jdbc/insert!
  tsk
  :tasks
  {
    :master_task nil
    :priority 1
    :description "Выгрузить Уральская сталь 9/030006"
    :date_to "2023-12-05"
    :comment nil
    :date_from "2023-12-05"
    :status 0
  })

;; #update#task
(jdbc/update!
  tsk
  :tasks
  {
    :status 100
    ;:complete_date "2023-06-21"
    ;:priority 1
    ;:date_to ""
    ;:description
    ;:comment
    ;:master_task nil
    ;:date_from ""
  }
  ["id = ?" 100])

;; #delete/task
(jdbc/delete!
  tsk
  :tasks
  ["id = ?" 100])

(comment

(require '[tasks.view.report :as report] :reload)

(require '[clojure.repl :refer :all])

(dir clojure.string)

)
