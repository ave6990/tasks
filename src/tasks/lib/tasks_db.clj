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

(defn out
  [s]
  (spit
    "/media/sf_YandexDisk/Ermolaev/midb/tasks.html"
    (report/tasks-report
      (get-data s)))
  #_(sh
    "vivaldi"
    "/media/sf_YandexDisk/Ermolaev/midb/tasks.html"))

;; #tasks
(out "status < 100")

;; #add#task
(jdbc/insert!
  tsk
  :tasks
  {
    ;:master_task 66
    :priority 1
    :description "Проверить выгрузку 9/029777 ОГЗ"
    :date_to "2023-12-11"
    :comment "Исправлены ошибочные данные о результатах поверки"
    :date_from "2023-12-08"
    :status 0
    ;:complete_date "2023-12-07"
  })
(out "status < 100")

;; #update#task
(jdbc/update!
  tsk
  :tasks
  {
    ;:status 100
    ;:complete_date "2023-12-07"
    ;:priority 20
    ;:date_to "2023-12-11"
    ;:description "9/030038 ОГЗ поверить"
    ;:comment "поверить с другим аккумулятором"
    :master_task 66
    ;:date_from ""
  }
  ["id = ?" 61])
(out "status < 100")

;; #delete/task
(jdbc/delete!
  tsk
  :tasks
  ["id = ?" 56])
(out "status < 100")

;; #add#tags
(jdbc/insert!
  tsk
  :tags
  {
    :task_id 68
    :tag "arshin"
  })
(out "status < 100")

(comment

(require '[tasks.view.report :as report] :reload)
(out "status < 100")

(require '[clojure.repl :refer :all])

(dir clojure.string)

(* (- 2.13 (* 1.33 0.966)) 0.01 0.966)

(- 2.13 (* 1.33 0.966))

)
