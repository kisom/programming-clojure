(ns programming-clojure.chapter5)
;;; chapter 5: state
;; the takeaway from this chapter:
;; "state is the value of an identity at a point in time."

;;; the start of a chat program to demonstrate more complex STM
(defrecord Message [sender text])
(def messages (ref ()))
(defn add-message
  "Add a message to the chat message queue."
  [msg]
  (dosync (alter messages conj msg)))

;; add a test message onto the queue
(add-message (Message. "J Random Hacker" "lovely spam!"))
(println "messages:" messages)

(defn commute-add-message
  "Add a message to the chat message queue. Uses commute; order not guaranteed."
  [msg]
  (dosync (commute messages conj msg)))
(commute-add-message (Message. "John Q Public" "Zord!"))
(println "number of messages in queue:" (dosync (count @messages)))

;; validated messages
(def ^{ :doc "Message validation function for message records." }
  validate-message-list
  (partial every? #(and (:sender %) (:text %))))
(def messages (ref () :validator validate-message-list))
(println "after queue reset: " (dosync (count @messages)))
(add-message (Message. "J Random Hacker" "lovely spam!"))
(commute-add-message (Message. "John Q Public" "Zord!"))
(println "queue size:" (dosync (count @messages)))

;; prove validation works
;; (println "testing expected failure...")
;; (add-message "Invalid message.")
;; it works

;; add backup agent
(def backup-agent (agent "data/messages.dat"))
(defn add-message-with-backup
  "Adds a new message (using commute) and dumps queue to file."
  [msg]
  (dosync
   (let [snapshot (commute messages conj msg)]
     (send-off backup-agent (fn [filename]
                              (spit filename snapshot)
                              filename))
    snapshot)))
(add-message-with-backup (Message. "foo" "bar baz quux"))
(if (nil? (agent-errors backup-agent))
  (println "great success!")
  (println (agent-errors backup-agent)))

;;; action at a distance: local bindings
(defn ^:dynamic slow-double
  "Simulation of an expensive computation."
  [n]
  (Thread/sleep 100)
  (* n 2))

(defn calls-slow-double
  "Sample client function that uses slow-double."
  [] (map slow-double [1 2 1 2 1 2]))

(println "slow version of slow-double")
(time (dorun (calls-slow-double)))      ; dorun required to bypass lazy evaluation

;; while slow-double is a good candidate for memoisation, the client
;; function is already using the slow version. rebind thread-locally...
(defn memo-slow
  "Override client's definition of slow-double with a memoised variant."
  [] (println "thread-local rebinding of slow-double")
  (time (dorun
            (binding [slow-double (memoize slow-double)]
              (calls-slow-double)))))
(memo-slow)
;; ostensibly, this was a good use of thread-locally rebinding a symbol;
;; in practise, the effects of this may be quite unpredictable and this
;; stands a good chance of destroying functional purity and therefore
;; should only be used after some introspection into the problem.
