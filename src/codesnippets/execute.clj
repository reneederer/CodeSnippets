(ns codesnippets.execute
           (:require [me.raynes.conch.low-level :as shell]))

(def srcFolder "~/dev/clojure/codesnippets/src/codesnippets/")

(defn compileAndRun
  [source]
  (spit  (srcFolder "test.cob")  source)
  (let [errors (shell/stream-to-string
                 (shell/proc "cobc" "-x"  (str srcFolder "test.cob") "-o"  (str srcFolder "test")) :err)
        output (shell/stream-to-string
                 (shell/proc  (str srcFolder "test")) :out)]
    {:errors errors :output output}))
