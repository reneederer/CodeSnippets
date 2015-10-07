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

(defn get-snippet-names
  "Gets the snippet titles for the specified programming language"
  [lang]
  (sql/query db [(str "select title from codesnippet
                      join programming_language
                      on codesnippet.programming_language_id = programming_language.id
                      where programming_language.name = '" lang "'")]))

(defn get-source
  "Queries the database and returns the sourcecode"
  [lang title]
  (sql/query db  [(str "select sourcecode from codesnippet
                       join programming_language
                         on codesnippet.programming_language_id = programming_language.id
                       where programming_language.name = '" lang "'
                       and codesnippet.title = '" title "'")]))


(defn get-compile-command
  "Queries the database and returns the compile command for the specified language"
  [lang]
  (sql/query db [(str "select argument.value from programming_language
                  join compiler
                  on programming_language.compiler_id = compiler.id
                  join argument
                  on compiler.id = argument.compiler_id
                  where programming_language.name = '" lang "'
                  order by argument.argument_nr")]))













