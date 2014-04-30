(ns {{name}}.core
  (:require
    [figwheel.client :as fw :include-macros true]))

(enable-console-print!)

;; define your app data so that it doesn't get over written on reload
;; (fw/defonce app-data (atom {}))

(println "Edits to this text should show up in your developer console.")

(fw/watch-and-reload
 :jsload-callback (fn []
                    ;; (stop-and-start-my app)
                    ))
