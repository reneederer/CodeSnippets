(ns codesnippets.db
    (:require [clojure.java [jdbc :as sql]]))

(def db  {:classname "com.mysql.jdbc.Driver"
                  :subprotocol "mysql"
                   :subname "//localhost/codesnippets"
                  :user "root"
                  :password "1234"})

 (defn get-prog-langs
       "Queries the database and gets the programming languages"
       []
       (sql/query db  ["select name from programming_language"]))
 
(defn get-source
     "Queries the database and returns the sourcecode"
     []
     (sql/query db  ["select sourcecode from codesnippet"]))
 


