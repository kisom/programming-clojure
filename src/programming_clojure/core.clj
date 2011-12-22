(ns programming-clojure.core
  (:use programming-clojure.chapter3))

(def steveo-dir "/Users/kyle/src/steveo")
(def this-dir "/Users/kyle/Code/programming-clojure")

(println "steveo's source files: " (show-clojure-source-files steveo-dir))
(println "source files in this project: " (show-clojure-source-files this-dir))

(println "steveo's projects' SLOC:" (clojure-sloc steveo-dir))
(println "this project's SLOC:" (clojure-sloc))


