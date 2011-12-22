					; chapter 3: unifying data with sequences
(defn non-blank?
  "A boolean test to determine if a line is blank."
  [line]
  (zero? (count (re-seq #"^\s*$" line))))

					; a not-svn? check is recommended but I don't use svn anywhere.
(defn clojure-source?
  "Checks file extension to determine if a file is a clojure source-file."
  [filename]
  (not (zero? (count (re-seq #"^.+\.clj$" filename)))))
