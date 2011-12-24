(ns programming-clojure.test.core
  (:use [programming-clojure.core])
  (:use [programming-clojure.chapter3])
  (:use [clojure.test]))

(deftest blank-tests
  (is (not-blank? "foo"))
  (is (not (not-blank? "")))
  (is (not (not-blank? "   "))))

(deftest comment-tests
  (is (not-comment? "(foo) ; comment"))
  (is (not-comment? "(foo)"))
  (is (not (not-comment? "; (foo) does foo"))))

(deftest clojuresource-tests
  (is (clojure-source? "core.clj"))
  (is (clojure-source? "tests.clj"))
  (is (not (clojure-source? "foo")))
  (is (not (clojure-source? "foo.c")))
  (is (not (clojure-source? (str (gensym)))))
  (is (not (clojure-source? "foo.cljnot"))))


