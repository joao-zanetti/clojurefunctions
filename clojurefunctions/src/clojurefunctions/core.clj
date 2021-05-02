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



;; ATOM
(def counter (atom 0))
;(pmap #(swap! counter (fn [x] (+ % x))) [1 1 1 1])



;; THREAD
(defn do-something-in-a-thread []
  (println "Hellofromthe thread.")
  (Thread/sleep 3000)
  (println "Goodbye fromthe thread.")
  (swap! counter inc))

(def the-thread-a (Thread. do-something-in-a-thread))
(def the-thread-b (Thread. do-something-in-a-thread))
(def the-thread-c (Thread. do-something-in-a-thread))


(defn call-threads []
  (do (.start the-thread-a)
      (.start the-thread-b)
      (.start the-thread-c)))

(def inventory [{:title "Emma" :sold 51 :revenue 255}
                {:title "2001" :sold 17 :revenue 170}])

(defn sum-copies-sold [inv] (apply + (map :sold inv))) 
(defn sum-revenue [inv] (apply + (map :revenue inv)))


(defn call-promises []
  (let [copies-promise (promise)
        revenue-promise (promise)]
    (.start (Thread. #(deliver copies-promise (sum-copies-sold inventory))))
    (.start (Thread. #(deliver revenue-promise (sum-revenue inventory))))

    (.start ())
                       ;; Do someotherstuffin thisthread...
    (println "Thetotalnumberof bookssoldis" @copies-promise)
    (println "Thetotalrevenueis " @revenue-promise)))

(def f (future (Thread/sleep 10000) (println "done") 100))
  