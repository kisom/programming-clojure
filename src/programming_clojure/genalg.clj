;;; some genetic algorithm code I was originally messing with in python
;;; it seems a good way to relearn thinking in lisp and good for learning clojure

;;; warning: currently broken
;;;    (trying to remember how to think functionally)
(ns programming-clojure.ga)

(defn- same-size?
  "Returns true if both sequences are the same size."
  [coll1 coll2]
  (== (count coll1) (count coll2)))

(defn int?
  "Returns true if the number is an int."
  [n]
  (and
   (not (coll? n))))


(defn- restr
  [string]
  (println "  (restr" string ")")
  (if (string? string)
    (apply str (rest string))
    ""))

(defn- valid-target?
  "Ensures a target string is valid."
  [target]
  (not (and
   (== 0 (count target))
   (not (nil? target)))))


(defn- fitness
  "Calculate fitness level of two strings. target is the desired string, source is
   the string being evaluated."
  [ target source & k ]
  (println (format "target: %s\nsource: %s\n" target source))
  (if (valid-target? target)
    (do (let [x (int (first target))
          y (int (first source))
          k (if (empty? k) 2 (first k))]
      (reduce +
              (lazy-seq
               (cons (Math/abs (Math/pow (- x y) k))
                     (fitness (restr target) (restr source) k))))))
    0))

(defn- stevesFitness
  "How I could calculate fitness"
  [target source & k]
  (println (format "target: %s\nsource: %s\n" target source))
  (let [k (if (empty? k) 2 (first k))]
    (reduce +
            (map #(Math/abs (Math/pow (- (int %1) (int %2)) k))
                 target source))))
