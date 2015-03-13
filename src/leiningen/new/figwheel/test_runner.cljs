(ns {{name}}.test-runner
  (:require
   [cljs.test :refer-macros [run-tests]]
   [{{name}}.test.core]))


(enable-console-print!)

(defn runner []
  (if (cljs.test/successful?
       (run-tests
        '{{name}}.test.core))
    0
    1))
