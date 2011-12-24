(ns programming-clojure.chapter4)

					; recursion with fibonacci
					; basis: f-0 <-- 0, f-1 <-- 1
					; induction: for n > 1, f-n = f-(n-1) + f-(n-2)

(defn simple-fibo			;bad simple recursion
  "A simple fibonacci recursion example that will blow the stack."
  [n]
  (cond
   (= n 0) 0
   (= n 1) 1
   :else (+ (simple-fibo (- n 1))
	    (simple-fibo (- n 2)))))

(defn tail-fibo [n]
  (letfn [(fib [current next n]
            (if (zero? n) current
                (fib next (+ current next) (dec n))))]
    (fib 0N 1N n)))

(defn recur-fibo [n]
  (letfn [(fib [current next n]
            (if (zero? n) current
                (recur next (+ current next) (dec n))))]
    (fib 0N 1N n)))

(defn lazy-fibo
  ([] (concat [0 1] (lazy-fibo 0N 1N)))
  ([a b] (let [n (+ a b)]
           (lazy-seq (cons n (lazy-fibo b n))))))
