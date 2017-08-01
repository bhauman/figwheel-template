(ns {{name}}.core
    (:require {{#react?}}[react]
              [react-dom]
              [sablono.core :as sab :include-macros true]{{/react?}}{{#om?}}[om.core :as om :include-macros true]
              [om.dom :as dom :include-macros true]{{/om?}}{{#reagent?}}[reagent.core :as reagent :refer [atom]]{{/reagent?}}{{#rum?}}[rum.core :as rum]{{/rum?}}))

(enable-console-print!)

(println "This text is printed from src/{{name}}/core.cljs. Go ahead and edit it and see reloading in action.")

;; define your app data so that it doesn't get over-written on reload

(defonce app-state (atom {:text "Hello world!"}))

{{#react?}}
(defn hello-world [state]
  (sab/html [:div
             [:h1 (:text @state)]
             [:h3 "Edit this and watch it change!"]]))

(js/ReactDOM.render
 (hello-world app-state)
 (. js/document (getElementById "app")))
{{/react?}}{{#om?}}
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
  [:div
   [:h1 (:text @app-state)]
   [:h3 "Edit this and watch it change!"]])

(reagent/render-component [hello-world]
                          (. js/document (getElementById "app")))
{{/reagent?}}{{#rum?}}
(rum/defc hello-world []
  [:div
   [:h1 (:text @app-state)]
   [:h3 "Edit this and watch it change!"]])

(rum/mount (hello-world)
           (. js/document (getElementById "app")))
{{/rum?}}

(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
)
