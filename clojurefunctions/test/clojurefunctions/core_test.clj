(ns clojurefunctions.core-test
  (:require [clojure.test :refer :all]
            [clojurefunctions.core :refer :all]))



(deftest dispatch-book-format-test
  (testing "testing function that is used for the multimethod"
    (is (= (dispatch-book-format "") :wrong-book))
    (is (= (dispatch-book-format {"" ""}) :map-book-without-name))
    (is (= (dispatch-book-format {:nome ""}) :map-book-with-name))
    (is (= (dispatch-book-format 1) :number-book))
    (is (= (dispatch-book-format '()) :wrong-book))
    (is (= (dispatch-book-format []) :vector-book))))
