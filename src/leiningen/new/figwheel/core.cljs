(ns {{name}}.core
  (:require
    [figwheel.client :as fw]))

(enable-console-print!)

;; define your app data so that it doesn't get over-written on reload
;; (defonce app-data (atom {}))

(println "Edits to this text should show up in your developer console.")

(fw/start {
  :on-jsload (fn []
               ;; (stop-and-start-my app)
               )})
