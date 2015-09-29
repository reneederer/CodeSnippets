(ns codesnippets.core
  (:require [clojure.java [jdbc :as sql]])
  (:require [clojure.string :as str])
  (:require [codesnippets.db :as db]))

(import '(javax.swing JFrame JButton JComboBox JPanel JOptionPane JTextArea))
(import '(java.awt.event ActionListener))

(def txaSourceCode (JTextArea. 10 10))


(defn show-frame []
  (let  [cmbProgLang (JComboBox.)
         
         pnlMain (doto (JPanel.)
                     (.add txaSourceCode)
                     (.add cmbProgLang)
                     )
         ]
    (doseq [v (db/get-prog-langs)] (.addItem cmbProgLang  (v :name)))
    (doseq [v (db/get-source)] (.append txaSourceCode (v :sourcecode )))
;    (.addActionListener button  
;                        (proxy  [ActionListener]  []
;                          (actionPerformed  [e]  (.setText button "Button Pressed")))
;                        )
    (doto (JFrame.)
      (.add pnlMain)
      (.setDefaultCloseOperation JFrame/DISPOSE_ON_CLOSE)
      (.setSize 400, 400)
      (.setVisible true))))


(defn -main []
  (show-frame))




