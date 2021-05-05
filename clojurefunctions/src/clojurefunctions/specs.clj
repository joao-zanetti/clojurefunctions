(ns clojurefunctions.specs
  (:require [clojure.spec.alpha :as s]))

;(require '[clojure.spec.alpha :as s]) REPL
;(require '[clojurefunctions.specs :as sp]) REPL

;AND
(def gt-10-lt-100 (s/and number? #(> % 10) #(< % 100)))


(s/def ::title (s/and #(string? %) #(not (number? %))))

;OR
(def n-or-s (s/or :a-number number? :a-string string?))



;COLL
(def coll-of-n-or-s (s/coll-of n-or-s))



;CAT
(def s-n-s-n (s/cat :s1 string? :n1 number? :s2 string? :n2 number?))
;(s/valid? s-n-s-n ["Emma" 1815 "Jaws" 1974])



;REQ KEYS
(def book-s (s/keys :req-un [:clojurefunctions.core/title
                             :clojurefunctions.core/author
                             :clojurefunctions.core/copies]))
;(s/valid? book-s {:title "oi" :author "ac" :copies 30})

;; (s/def :clojurefunctions.core/title string?)
;; (s/def :clojurefunctions.core/author string?)
;; (s/def :clojurefunctions.core/copies int?)
(s/def :clojurefunctions.core/book (s/keys :req-un [:clojurefunctions.core/title
                                                    :clojurefunctions.core/author
                                                    :clojurefunctions.core/copies]))
;(s/valid? :clojurefunctions.core/book {:title "oi" :author "ac" :copies 30})

;SAME NAMESPACE
;; (s/def ::title string?)
;; (s/def ::author string?)
;; (s/def ::copies int?)
;; (s/def ::book (s/keys :req-un [::title ::author ::copies]))


;EXPLAIN
;JUST PRINT
;(s/explain n-gt-1044)

;CONFORM
;RETURN A KEY  :clojure.spec.alpha/invalid
;OR return the parameter complete
;(s/conform n-gt-1044)


;SPEC TEST DRIVE DEVELOPMENT
;(defn book-blurb [book] (str "Thebestsellingbook" (:titlebook) " by " (:authorbook)))

;; (s/fdef book-blurb
;;   :args (s/cat :book ::book)
;;   :ret (s/and string? (partial re-find #"Thebestselling")))
;PAG 190




