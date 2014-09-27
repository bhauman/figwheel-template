(ns leiningen.new.figwheel
  (:require [leiningen.new.templates :refer [renderer name-to-path ->files]]
            [leiningen.core.main :as main]))

(def render (renderer "figwheel"))

(defn figwheel
  "FIXME: write documentation"
  [name]
  (let [data {:name name
              :sanitized (name-to-path name)}]
    (main/info "Generating fresh 'lein new' figwheel project.")
    (->files data
             ["project.clj" (render "project.clj" data)]
             ["src/{{sanitized}}/core.cljs" (render "core.cljs" data)]
             ["dev/user.cljs" (render "user.cljs" data)]
             ["resources/public/index.html" (render "index.html" data)]
             ["resources/public/css/style.css" (render "style.css" data)])))
