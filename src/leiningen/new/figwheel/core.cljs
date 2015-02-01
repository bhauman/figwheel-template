(ns {{name}}.core
    (:require [figwheel.client :as fw] {{#om?}}
              [om.core :as om :include-macros true]
              [om.dom :as dom :include-macros true] {{/om?}} {{#reagent?}}
              [reagent.core :as reagent :refer [atom]] {{/reagent?}}
              ))

(enable-console-print!)

(println "Edits to this text should show up in your developer console.")

;; define your app data so that it doesn't get over-written on reload

(defonce app-state (atom {:text "Hello world!"}))
{{#om?}}

(om/root
  (fn [app owner]
    (reify om/IRender
      (render [_]
        (dom/h1 nil (:text app)))))
  app-state
  {:target (. js/document (getElementById "app"))})
{{/om?}}{{#reagent?}}
(defn hello-world []
  [:h1 (:text @app-state)])

(reagent/render-component [hello-world]
                          (. js/document (getElementById "app")))
{{/reagent?}}

(fw/start {
  :on-jsload (fn []
               ;; (stop-and-start-my app)
               )})
