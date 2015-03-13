(ns ^:figwheel-always {{name}}.core
    (:require{{#om?}}[om.core :as om :include-macros true]
              [om.dom :as dom :include-macros true]{{/om?}}{{#reagent?}}
              [reagent.core :as reagent :refer [atom]]{{/reagent?}}))

(enable-console-print!)

(println "Edits to this text should show up in your developer console.")

;; define your app data so that it doesn't get over-written on reload

(defonce app-state (atom {:text "Hello world!"}))
{{#om?}}
(defn main []
  (om/root
   (fn [data owner]
     (reify om/IRender
       (render [_]
         (dom/h1 nil (:text data)))))
   app-state
   {:target (. js/document (getElementById "app"))}))
{{/om?}}{{#reagent?}}
(defn hello-world []
  [:h1 (:text @app-state)])

(defn main []
  (reagent/render-component [hello-world]
                            (. js/document (getElementById "app"))))
{{/reagent?}}
