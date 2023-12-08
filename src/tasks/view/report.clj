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

(def scripts
  "Обновляет страницу при возвращении фокуса."
  "var blurred = false
  window.addEventListener('blur', (e) => {
    blurred = true
  })
  window.addEventListener('focus', (e) => {
    if (blurred) {
      location.reload()
    }
  })")

(defn report
  ([page st sc content]
   (doctype
     (html
       (head
         (meta {:charset "utf-8"})
         (meta {:name "author" :content "Aleksandr Ermolaev"})
         (meta {:name "e-mail" :content "ave6990@ya.ru"})
         (meta {:name "version" :content "2023-12-08"})
         (title page)
         (style {:type "text/css"} st)
         (script {:type "text/javascript"} sc))
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
  (report "tasks" styles scripts
    (body
      (header)
      (main
        (table
          (thead
            (tr
              (th {:colspan 4}
                  "status")
              )
            (tr
              (th "id")
              (th "date_from")
              (th "date_to")
              (th "coplete_date"))
            (tr
              (th "master_task")
              (th {:colspan 3}
                  "description"))
            (tr
              (th "priority")
              (th {:colspan 3}
                  "comment"))
            (tr
              (th {:colspan 4}
                  "tags")))
          (tbody
            (string/join
              "\n"
              (map (fn [m]
                       (string/join
                        "\n"
                        (list
                          (tr
                            (td {:colspan 4}
                                (label
                                  #_(str (:status m) " % ")
                                  (progress
                                    {:max 100
                                     :value (:status m)})))
                            
                            )
                          (tr
                            (td (:id m))
                            (td (:date_from m))
                            (td (:date_to m))
                            (td (:complete_date m)))
                          (tr 
                            (td (:master_task m))
                            (td {:colspan 3}
                                (:description m)))
                          (tr
                            (td 
                              (label
                                (:priority m)
                                #_(progress
                                  {:max 100
                                   :value (- 100
                                             (:priority m))})))
                            (td {:colspan 3}
                                (if (nil? (:comment m))
                                    "-"
                                    (:comment m))))
                          (tr
                            (td {:colspan 4}
                                (if (nil? (:tags m))
                                    "-"
                                    (:tags m)))))))
                   coll))))))))


(comment

)
