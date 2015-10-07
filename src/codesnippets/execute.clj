(ns codesnippets.execute
  (:require [codesnippets.db :as db])
  (:require [clojure.java.io :as io])
  (:require [me.raynes.conch.low-level :as shell]))

(def srcFolder "/home/rene/dev/clojure/codesnippets/src/codesnippets/")


(defn compileAndRun
  [source lang]
  (let [compileCommand (flatten (map vals (db/get-compile-command lang)))
        srcFile (str srcFolder (first (filter #(.startsWith %1 "srcFile") compileCommand)))
        destFile (str srcFolder (first (filter #(.startsWith %1 "destFile") compileCommand)))]
    (let [command (map #(if (or (.startsWith %1 "srcFile") (.startsWith %1 "destFile")) (str srcFolder %1) %1) compileCommand)]
    (spit srcFile source)

    (let [errors (shell/stream-to-string
                           (apply shell/proc command) :err)]
      (let [output (if (.exists (io/as-file destFile)) (shell/stream-to-string (shell/proc destFile) :out) nil)]
                {:errors errors :output output})
      
      )))) 


















