(ns {{name}}.core
  (:require {{#om?}}[om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]{{/om?}}{{#reagent?}}[reagent.core :as reagent :refer [atom]]{{/reagent?}}{{#rum?}}[rum.core :as rum]{{/rum?}}))

(enable-console-print!)

(println "This text is printed from src/{{name}}/core.cljs. Go ahead and edit it and see reloading in action.")

;; define your app data so that it doesn't get over-written on reload

(defonce app-state (atom {:text "Hello world!"}))
{{#om?}}

(om/root
  (fn [data owner]
    (reify om/IRender
      (render [_]
        (dom/div nil
                 (dom/h1 nil (:text data))
                 (dom/h3 nil "Edit this and watch it change!")))))
  app-state
  {:target (. js/document (getElementById "app"))})
{{/om?}}{{#reagent?}}
(defn hello-world []
  [:h1 (:text @app-state)])

(reagent/render-component [hello-world]
                          (. js/document (getElementById "app")))
{{/reagent?}}
{{#rum?}}

(rum/defc hello-world []
    [:h1 (:text @app-state)])

(rum/mount (hello-world)
           (. js/document (getElementById "app")))
{{/rum?}}

(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
)
