(ns tasks.view.report
  (:require [tasks.lib.gen-html :refer :all]
            [clojure.string :as string]))

(def styles
"html {
  color: #393939;
  font: 10pt sans-serif;
}
table {
  border: 1px solid lightgray;
  border-collapse: collapse;
  margin: 4pt 0;
  width: 100%;
}
th, td {
  padding: 0 3pt;
  border: 1px solid lightgray;
}
progress {
  width: 100%;
}")

(defn report
  ([page st sc content]
   (doctype
     (html
       (head
         (meta {:charset "utf-8"})
         (meta {:name "author" :content "Aleksandr Ermolaev"})
         (meta {:name "e-mail" :content "ave6990@ya.ru"})
         (meta {:name "version" :content "2023-09-21"})
         (title page)
         (style {:type "text/css"} st)
         (script #_{:type "text/javascript"} #_sc))
       content)))
  ([page st content]
   (report page st "" content))
  ([page content]
   (report page "" "" content)))

(defn gen-th
  [coll]
  (string/join
    "\n"
    (map (fn [s]
             (th s))
         coll)))

(defn tasks-report
  ""
  [coll]
  (report "tasks" styles
    (body
      (header)
      (main
        (table
          (thead
            (tr
              (th {:rowspan 4}
                  "id")
              (th {:rowspan 4}
                  "m_id")
              (th {:rowspan 4}
                  "priority")
              (th {:colspan 3}
                  "status"))
            (tr
              (th "date_from")
              (th "date_to")
              (th "coplete_date"))
            (tr
              (th {:colspan 3}
                  "description"))
            (tr
              (th {:colspan 3}
                  "comment")))
          (tbody
            (string/join
              "\n"
              (map (fn [m]
                       (string/join
                        "\n"
                        (list
                          (tr
                            (td {:rowspan 4}
                                (:id m))
                            (td {:rowspan 4}
                                (:master_task m))
                            (td {:rowspan 4}
                                (:priority m))
                            (td {:colspan 3}
                                (progress
                                  {:value (:status m)})))
                          (tr
                            (td (:date_from m))
                            (td (:date_to m))
                            (td (:complete_date m)))
                          (tr 
                            (td {:colspan 3}
                                (:description m)))
                          (tr
                            (td {:colspan 3}
                                (if (nil? (:comment m))
                                    "-"
                                    (:comment m)))))))
                   coll))))))))

(comment

)
