(ns clojurefunctions.core
  (:require [clojurefunctions.specs :as sp]))

;;MULTIMETHOD
(defn dispatch-book-format [book]
  (cond
    (vector? book) :vector-book
    (number? book) :number-book
    (and (map? book) (contains? book :nome)) :map-book-with-name
    (map? book) :map-book-without-name
    :else :wrong-book))

(defmulti normalize-book dispatch-book-format)

(defmethod normalize-book :vector-book [book]
  (prn "normalize-book"))
(defmethod normalize-book :number-book [book]
  (prn "number-book"))
(defmethod normalize-book :map-book-with-name [book]
  (prn "map-book-with-name"))
(defmethod normalize-book :map-book-without-name [book]
  (prn "map-book-without-name"))
(defmethod normalize-book :wrong-book [book]
  (prn "wrong-book"))



;;RECORDS AND PROTOCOLS

(defrecord Person [fname lsname adress])

(defprotocol Award
  (present [this recipient]))

(defrecord Oscar [category]
  Award
  (present [this recipient]
           (prn (str "OSCAR " "Category: " (:category this) "  Recipient: " recipient))))

(defrecord Canes [category]
  Award
  (present [this recipient]
           (prn (str "CANES " "Category: " (:category this) "  Recipient: " recipient))))

;(let [oscar-ex (Oscar. "trhiller")  canes-ex (Canes. "documentary")] (present canes-ex "alo"))

(defprotocol New-protocol 
  (new-func [param]))

(extend-protocol New-protocol
  Oscar
  (new-func [this]
    (prn "OSCAR new function"))
  Canes
  (new-func [this]
    (prn "CANES new function")))




;;DESTRUCTURING 
(defn dest [lista-doida] (let [[[_ segundo-do-primeiro] segundo] lista-doida] 
                           (prn segundo-do-primeiro) (prn segundo)))
;(dest [["1.1" "1.2"] "2"])

(defn dest-map [mapa-doido] (let [{nome :nome email :email} mapa-doido] 
                              (prn nome) (prn email)))
;(dest-map {:nome "jose" :email "tnc@hotmail"})

;NESTED
(def austen {:name"JaneAusten" 
             :parents {:father "George" 
                       :mother "Cassandra"} 
             :dates {:born 1775 
                     :died 1817}})

;; (let [{{dad :father mom :mother} :parents} austen]))

(defn testo [{:keys [a ab]}] (prn a))
;(testo {:a 1 :ab "3"})
