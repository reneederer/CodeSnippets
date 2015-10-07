(ns codesnippets.execute
  (:require [clojure.java.io :as io])
           (:require [me.raynes.conch.low-level :as shell]))

(def srcFolder "/home/rene/dev/clojure/codesnippets/src/codesnippets/")


(defn compileAndRun
  [source]
  (print source)
  (spit  (str srcFolder "test.cob")  source)
  (let  [errors  (shell/stream-to-string
                   (shell/proc "cobc" "-x"  (str srcFolder "test.cob") "-o"  (str srcFolder "test")) :err)]
    (let [output  (if (io/as-file (str srcFolder "test"))
                   (shell/stream-to-string (shell/proc  (str srcFolder "test")) :out)
                   nil)]

    {:errors errors :output output} ))
  
  )




















