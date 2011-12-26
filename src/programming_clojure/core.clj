(ns programming-clojure.core
;  (:gen-class)
  (:use programming-clojure.chapter3)
  (:use programming-clojure.chapter4))

; globals
(def steveo-dir "/Users/kyle/src/steveo")
(def this-dir "/Users/kyle/Code/programming-clojure")

(defn ch3_main []
  (println "steveo's source files: " (show-clojure-source-files steveo-dir))
  (println "source files in this project: " (show-clojure-source-files this-dir))
  (println "steveo's projects' SLOC:" (clojure-sloc steveo-dir))
  (println "this project's SLOC:" (clojure-sloc))
  (println "cost of steveo's project:" (format "%.2f" (project-cost steveo-dir)))
  (println "cost of this project:" (format "$%.2f" (project-cost))))


(defn ch4_main []
  (println "timing simple fibo:")
  (time (simple-fibo 10))
  (println "timing tail fibo:")
  (time (tail-fibo 10))
  (println "timing recur fibo:")
  (time (recur-fibo 10))
  (println "timing explicit lazy fibo:")
  (time (nth (lazy-fibo) 10))
  (println "timing implicit lazy fibo:")
  (time (nth (mapi-fibo) 10)))

(defn -main [& args]
  (ch3_main)
  (ch4_main))

