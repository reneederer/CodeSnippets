(ns codesnippets.core
  (:require [clojure.java [jdbc :as sql]])
  (:require [clojure.string :as str])
  (:require [codesnippets.db :as db])
  (:import (javax.swing JFrame JButton JComboBox JPanel JOptionPane JTextArea BoxLayout JScrollPane))
  (:import (java.awt.event ActionListener))
  (:import (java.awt BorderLayout FlowLayout)))



(defn show-frame []
  (let  [cmbProgLang (JComboBox.)
         txaSourceCode (JTextArea.)
         pnlSearch (JPanel. (FlowLayout.))
         scrSourceCode (JScrollPane. txaSourceCode)
         pnlSourceCode (JPanel. (BorderLayout.))
         pnlResultList (JPanel.)
         pnlResults (JPanel.)
         pnlMain (JPanel.)]
    (.add pnlResultList (JLabel. "test"))

    (.add pnlSourceCode scrSourceCode)
    (.add pnlSearch cmbProgLang)
    (.setLayout pnlResults (BoxLayout. pnlResults BoxLayout/X_AXIS))
    (.add pnlResults pnlSourceCode)
    (.add pnlResults pnlResultList)
    (.setLayout pnlMain (BoxLayout. pnlMain BoxLayout/Y_AXIS))
    (.add pnlMain pnlSearch)
    (.add pnlMain pnlResults)

    ;(.add txaSourceCode)
    ;(.add cmbProgLang)
    (doseq [v (db/get-prog-langs)] (.addItem cmbProgLang  (v :name)))
    (doseq [v (db/get-source)] (.append txaSourceCode (v :sourcecode )))
;    (.addActionListener button  
;                        (proxy  [ActionListener]  []
;                          (actionPerformed  [e]  (.setText button "Button Pressed")))
;                        )
    (doto (JFrame.)
      (.add pnlMain)
      (.setDefaultCloseOperation JFrame/DISPOSE_ON_CLOSE)
      (.setExtendedState JFrame/MAXIMIZED_BOTH)
      (.setSize 400, 400)
      (.setVisible true))))


(defn -main []
  (show-frame))




