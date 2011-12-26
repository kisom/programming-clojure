(ns programming-clojure.chapter4)

;;; recursion with fibonacci
;; basis: f-0 <-- 0, f-1 <-- 1
;; induction: for n > 1, f-n = f-(n-1) + f-(n-2)

(defn simple-fibo
  "A simple fibonacci recursion example that is slow and will blow the stack."
  [n]
  (cond
   (= n 0) 0
   (= n 1) 1
   :else (+ (simple-fibo (- n 1))
	    (simple-fibo (- n 2)))))

(defn tail-fibo
  "Fibonacci recusion example using tail recursion."
  [n]
  (letfn [(fib [current next n]
            (if (zero? n) current
                (fib next (+ current next) (dec n))))]
    (fib 0N 1N n)))

(defn recur-fibo
  "Explicit self-recursion with recur."
  [n]
  (letfn [(fib [current next n]
            (if (zero? n) current
                (recur next (+ current next) (dec n))))]
    (fib 0N 1N n)))

(defn lazy-fibo
  "Lazy fibonacci sequence using explicit lazy-seq."
  ([] (concat [0 1] (lazy-fibo 0N 1N)))
  ([a b] (let [n (+ a b)]
           (lazy-seq (cons n (lazy-fibo b n))))))

(defn mapi-fibo
  "Lazy sequence implicitly via map/iterate."
  []
  (map first                                     ; map and iterate create lazy
       (iterate (fn [[a b]] [b (+ a b)])         ; sequences by default
                [0N 1N])))


;;; coin toss and lazier-than-lazy code

(def ^{ :doc "The example sequence used in the book to verify results; 
should result in two pairs of heads." } 
     sample-toss [ :h :t :t :h :h :h ]) ; sample-toss is the example sequence
                                        ; used in the book, used to verify results.
                                        ; should result in two runs of pairs of heads

(defn coin-toss
  "Generate a sequence of coin-tosses."
  [n]
  (into [] (repeatedly n #(if (= 1 (rand-int 2)) :h :t))))

(defn recur-count-pairs
  "Recursively find pairs of heads in a run of coin tosses."
  [coll]
  (loop [cnt 0 coll coll]
    (if (empty? coll) cnt
        (recur (if (= :h (first coll) (second coll))
                 (inc cnt) cnt)
               (rest coll)))))

(defn by-pairs
  "Break sequence into pairs suitable for detecting pairs."
  [coll]
  (let [take-pair (fn [c] (when (next c) (take 2 c)))]
    (lazy-seq
     (when-let [pair (seq (take-pair coll))]
       (cons pair (by-pairs (rest coll)))))))

(def ^{ :doc "Count items matching a filter." } count-if
  (comp count filter))

(defn count-runs [n pred coll]
  (count-if #(every? pred %)
             (partition n 1 coll)))