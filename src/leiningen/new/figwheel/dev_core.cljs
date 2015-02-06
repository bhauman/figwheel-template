(ns {{name}}.dev
    (:require
     [{{name}}.core]
     [figwheel.client :as fw]))

(fw/start {
  :on-jsload (fn []
               ;; (stop-and-start-my app)
               )})
