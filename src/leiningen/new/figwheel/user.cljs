(ns user
 (:require
    [figwheel.client :as fw]))


(enable-console-print!)

(println "Edits to this text should show up in your developer console.")

(fw/watch-and-reload
 :jsload-callback (fn []
                    ;; (stop-and-start-my app)
                    ))

