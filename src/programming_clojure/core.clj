(ns programming-clojure.core
  (:gen-class)
  (:use programming-clojure.chapter3))

; globals
(def steveo-dir "/Users/kyle/src/steveo")
(def this-dir "/Users/kyle/Code/programming-clojure")

(defn -main [& args]
  (println "steveo's source files: " (show-clojure-source-files steveo-dir))
  (println "source files in this project: " (show-clojure-source-files this-dir))
  (println "steveo's projects' SLOC:" (clojure-sloc steveo-dir))
  (println "this project's SLOC:" (clojure-sloc))
  (println "cost of steveo's project:" (format "%.2f" (project-cost steveo-dir)))
  (println "cost of this project:" (format "$%.2f" (project-cost))))

