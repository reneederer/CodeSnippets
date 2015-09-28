(ns codesnippets.core-test
  (:require [clojure.test :refer :all])
  (:require [codesnippets.core :refer :all]))
(deftest a-test
  (testing "FIXME, I fail."
    (is (= 1 1))))

(deftest b-test
  (testing "does get-hallo return Hallo?"
    (is (= "hlo" (getHallo)))))

(require 'codesnippets.core-test :reload-all)
