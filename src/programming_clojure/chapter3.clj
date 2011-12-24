					; chapter 3: unifying data with sequences
(ns programming-clojure.chapter3
  (:use [clojure.java.io :only (reader)]))

(import (java.io File))

; average salary of a developer according to sloccount
(def average-salary 56286)

(defn not-blank?
  "A boolean test to determine if a line is not blank."
  [line]
  (zero? (count (re-seq #"^\s*$" line))))

(defn not-comment?
  "A boolean test to determine if a line is a clojure comment."
  [line]
  (zero? (count (re-seq #"^\s*;.+$" line))))

(defn clojure-source?
  "Checks file extension to determine if a file is a clojure source-file."
  [filename]
  (not (zero? (count (re-seq #"^.+\.clj$" filename)))))

(defn count-sloc
  "Count the SLOC in a file."
  [filename]
  (let [body (line-seq (reader filename))]
      (count
       (filter not-blank?
	       (filter not-comment? body)))))

(defn clojure-sloc
  "Count the SLOC of all the clojure source files in the current directory."
  ([] (clojure-sloc "."))
  ([source-path]
     (reduce +
	  (map count-sloc
	       (filter clojure-source?
		       (map #(.getAbsolutePath %)
			    (file-seq (File. source-path))))))))

(defn show-clojure-source-files
  [source-path]
  (filter clojure-source?
		  (map #(.getAbsolutePath %)
		       (file-seq (File. source-path)))))
  

(defn project-cost
  "Count the COCOMO cost of a project directory."
  ([] (project-cost "."))
  ([project-path]
     (let [ksloc (/ (clojure-sloc project-path) 1000.0)
	   person-months (* 2.4 (java.lang.Math/pow ksloc 1.05))
	   person-years  (/ person-months 12.0)
	   schedule-estimate (/ (* 2.5 (java.lang.Math/pow person-months 0.38)) 12)
	   number-of-developers (/ person-years schedule-estimate)
	   overhead 2.4]
       (/ (* average-salary number-of-developers) overhead))))

