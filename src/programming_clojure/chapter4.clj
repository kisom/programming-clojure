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
