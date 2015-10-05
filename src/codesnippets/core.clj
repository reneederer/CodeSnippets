(ns codesnippets.core
  (:require [clojure.java [jdbc :as sql]])
  (:require [clojure.string :as str])
  (:require [codesnippets.db :as db])
  (:require [me.raynes.conch.low-level :as shell])
  (:import (javax.swing JFrame JButton JComboBox JPanel JTextArea JScrollPane JTree JOptionPane BoxLayout))
  (:import (javax.swing.tree DefaultMutableTreeNode))
  (:import (javax.swing.event TreeSelectionListener))
  (:import (java.awt BorderLayout Dimension Color))
  (:import (java.awt.event ActionListener KeyListener KeyEvent)))

(def srcFolder "/home/rene/dev/clojure/codesnippets/src/codesnippets/")

(defn compileAndRun
  [source]
(spit  (str srcFolder "test.cob")  source)
  (let [errors (shell/stream-to-string
                 (shell/proc "cobc" "-x"  (str srcFolder "test.cob") "-o"  (str srcFolder "test")) :err)
        output (shell/stream-to-string
              (shell/proc  (str srcFolder "test")) :out)]
   {:errors errors :output output}))


(defn show-frame []
  (let  [txaSourceCode (JTextArea.)
         txaCompileErrors (JTextArea.)
         txaOutput (JTextArea.)
         pnlSearch (JPanel.)
         scrSourceCode (JScrollPane. txaSourceCode)
         pnlSourceCode (JPanel. (BorderLayout.))
         scrCompileErrors (JScrollPane. txaCompileErrors)
         pnlCompileErrors (JPanel. (BorderLayout.))
         scrOutput (JScrollPane. txaOutput)
         pnlOutput (JPanel. (BorderLayout.))

         pnlResults (JPanel. (BorderLayout.))
         pnlExecution (JPanel.)
         root (DefaultMutableTreeNode.)
         pnlCenter (JPanel. (BorderLayout.))
         pnlMain (JPanel. (BorderLayout.))]

    (.setLayout pnlExecution (BoxLayout. pnlExecution BoxLayout/Y_AXIS))
    (.addKeyListener txaSourceCode
                     (proxy  [KeyListener] []
                       (keyPressed  [e]
                         (when (= (.getKeyCode e) KeyEvent/VK_F5)

                           (.setText txaCompileErrors "Working...")
                           (.setText txaOutput "Working...")
                           (let [execution (compileAndRun (.getText txaSourceCode))]
                             (.setText txaCompileErrors (:errors execution))
                             (if (str/blank? (:errors execution))
                                          (.setText txaOutput (:output execution))
                                          (.setText txaOutput "")
                                          )
                             )))
                       (keyReleased  [e])
                       (keyTyped  [e])))
    (.add pnlOutput scrOutput)
    (.add pnlCompileErrors scrCompileErrors)
    (.add pnlCenter pnlSourceCode BorderLayout/CENTER)
    (.add pnlExecution pnlCompileErrors)
    (.add pnlExecution pnlOutput)
    (.add pnlCenter pnlExecution BorderLayout/LINE_END)
    (.setPreferredSize pnlExecution (Dimension. 400 100))
    (.setBackground pnlExecution Color/RED)
    (.setBackground pnlCenter Color/GREEN)
    (.setPreferredSize pnlResults (Dimension. 400 100))
    (.add pnlSourceCode scrSourceCode)
    (.add pnlMain pnlCenter BorderLayout/CENTER)
    (.add pnlMain pnlSearch BorderLayout/PAGE_START)
    (.add pnlMain pnlResults BorderLayout/LINE_END)

    (doseq [v (db/get-prog-langs)] 
      (let [currentNode (DefaultMutableTreeNode. (v :name))]
        (.add root currentNode)  
        (doseq [w (db/get-snippet-names (v :name))]
          (do
          (.add currentNode (DefaultMutableTreeNode. (w :title)))))))




    (let [tree (JTree. root)]
    (.add pnlResults tree)
    (.addTreeSelectionListener tree
      (proxy [TreeSelectionListener] []
        (valueChanged [e] 
          (let [currentNode (.getLastSelectedPathComponent (.getSource e))]
            (.setText txaSourceCode (:sourcecode (first (db/get-source (.getParent currentNode) currentNode)))))))))

    (doto (JFrame.)
      (.add pnlMain) (.setDefaultCloseOperation JFrame/DISPOSE_ON_CLOSE) (.setExtendedState JFrame/MAXIMIZED_BOTH) (.setSize 400, 400) (.setVisible true)))) (defn -main [] (show-frame))



