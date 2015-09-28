(ns codesnippets.core
  (:require [clojure.java [jdbc :as sql]])
  (:require [clojure.string :as str]))

(import '(javax.swing JFrame JButton JComboBox JPanel JOptionPane JTextArea))
(import '(java.awt.event ActionListener))

(def txaSourceCode (JTextArea. 10 10))

(def db {:classname "com.mysql.jdbc.Driver"
         :subprotocol "mysql"
         :subname "//localhost/codesnippets"
         :user "root"
         :password "1234"})

(defn show-frame []
  (let  [cmbProgLang (JComboBox.)
         
         pnlMain (doto (JPanel.)
                     (.add txaSourceCode)
                     (.add cmbProgLang)
                     )
         ]
    (doseq [v (get-prog-langs)] (.addItem cmbProgLang  (v :name)))
    (doseq [v (get-source)] (.append txaSourceCode (v :sourcecode )))
;    (.addActionListener button  
;                        (proxy  [ActionListener]  []
;                          (actionPerformed  [e]  (.setText button "Button Pressed")))
;                        )
    (doto (JFrame.)
      (.add pnlMain)
      (.setDefaultCloseOperation JFrame/DISPOSE_ON_CLOSE)
      (.setSize 400, 400)
      (.setVisible true))))

(defn get-prog-langs
  "Queries the database and gets the programming languages"
  []
  (sql/query db ["select name from programming_language"]))

(defn get-source
  "Queries the database and returns the sourcecode"
  []
  (sql/query db ["select sourcecode from codesnippet"])
  )


(defn -main []
  (show-frame))




