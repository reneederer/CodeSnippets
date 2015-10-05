(ns codesnippets.core
  (:require [clojure.java [jdbc :as sql]])
  (:require [clojure.string :as str])
  (:require [codesnippets.db :as db])
  (:import (javax.swing JFrame JButton JComboBox JPanel JTextArea JScrollPane JTree JOptionPane))
  (:import (javax.swing.tree DefaultMutableTreeNode))
  (:import (javax.swing.event TreeSelectionListener))
  (:import (java.awt BorderLayout Dimension))
  (:import (java.awt.event ActionListener)))



(defn show-frame []
  (let  [txaSourceCode (JTextArea.)
         pnlSearch (JPanel.)
         scrSourceCode (JScrollPane. txaSourceCode)
         pnlSourceCode (JPanel. (BorderLayout.))
         pnlResults (JPanel. (BorderLayout.))
         root (DefaultMutableTreeNode.)
         pnlMain (JPanel. (BorderLayout.))]

    (.setPreferredSize pnlResults (Dimension. 400 100))
    (.add pnlSourceCode scrSourceCode)
    (.add pnlMain pnlSourceCode BorderLayout/CENTER)
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
      (.add pnlMain)
      (.setDefaultCloseOperation JFrame/DISPOSE_ON_CLOSE)
      (.setExtendedState JFrame/MAXIMIZED_BOTH)
      (.setSize 400, 400)
      (.setVisible true))))


(defn -main []
  (show-frame))



