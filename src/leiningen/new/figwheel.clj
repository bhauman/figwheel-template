(ns leiningen.new.figwheel
  (:require [leiningen.new.templates :refer [renderer name-to-path ->files]]
            [leiningen.core.main :as main]
            [clojure.string :as string]))

(def render (renderer "figwheel"))

(defn os? []
  (let [os-name
        (-> (System/getProperty "os.name" "generic")
            (.toLowerCase java.util.Locale/ENGLISH))
        has? #(>= (.indexOf %1 %2) 0)]
    (cond
      (or (has? os-name "mac")
          (has? os-name "darwin")) :macos
      (has? os-name "win") :windows
      (has? os-name "nux") :linux
      :else :other)))

;; Check if om or reagent are in the options
;; Copied from: https://github.com/plexus/chestnut/blob/master/src/leiningen/new/chestnut.clj

;; I copy this levenshtein impl everywhere
(defn- next-row
  [previous current other-seq]
  (reduce
    (fn [row [diagonal above other]]
      (let [update-val (if (= other current)
                          diagonal
                          (inc (min diagonal above (peek row))))]
        (conj row update-val)))
    [(inc (first previous))]
    (map vector previous (next previous) other-seq)))

(defn- levenshtein
  [sequence1 sequence2]
  (peek
    (reduce (fn [previous current] (next-row previous current sequence2))
            (map #(identity %2) (cons nil sequence2) (range))
            sequence1)))

(defn- similar [ky ky2]
  (let [dist (levenshtein (str ky) (str ky2))]
    (when (<= dist 2) dist)))

(def supported-frameworks #{"reagent" "rum" "react"})

(def framework-opts (set (map #(str "--" %) supported-frameworks)))

(def supported-attributes #{"no-bundle"})

(def attribute-opts (set (map #(str "+" %) supported-attributes)))

(defn similar-options [opt]
  (second (first (sort-by first
                  (filter first (map (juxt (partial similar opt) identity)
                                     (concat framework-opts attribute-opts)))))))

(defn parse-opts [opts]
  (reduce (fn [accum opt]
            (cond 
              (framework-opts opt) (assoc accum :framework (keyword (subs opt 2)))
              (attribute-opts opt) (update accum :attributes
                                           (fnil conj #{})
                                           (keyword (subs opt 1)))
              :else
              (let [suggestion (similar-options opt)]
                (throw
                 (ex-info (format "Unknown option '%s' %s"
                                  opt
                                  (str
                                   (when suggestion
                                     (format "\n    --> Perhaps you intended to use the '%s' option?" suggestion))))
                          {:opts opts
                           ::error true})))))
          {} opts))

#_ (parse-opts ["--om" "+no-bundle"])

(defn figwheel
  "Takes a name and options with the form --option and produces an interactive
   ClojureScript + Figwheel template.
   The valid options are:
     --react    which adds a minimal Ract application in core.cljs
     --reagent  which adds a minimal Reagent application in core.cljs
     --rum      which adds a minimal Rum application in core.cljs
     +no-bundle don't include npm bundle support
   Only one option can be specified at a time. If no option is specified,
   nothing but a print statment is added in core.cljs"
  [name & opts]
  (do
    (when (= name "figwheel")
      (main/abort
       (str "Cannot name a figwheel project \"figwheel\" the namespace will clash.\n"
            "Please choose a different name, maybe \"tryfig\"?")))
    (let [{:keys [framework attributes]} (parse-opts opts)
          bundle? (not (get attributes :no-bundle))
          data {:name      name
                :sanitized (name-to-path name)
                :react?    (= :react framework)
                :reagent?  (= :reagent framework)
                :rum?      (= :rum framework)
                :npx-command (if (= :windows (os?)) "npx.cmd" "npx")
                :bundle?   bundle?
                :reactdep? (boolean (#{:om :react :reagent} framework))}]
      (main/info (str "Generating fresh 'lein new' figwheel project.\n\n"
                      "Change into your '" name "' directory\n\n"
                      (when bundle?
                        "Install npm dependencies via 'npm install'\n")
                      "Then run 'lein figwheel'\n"
                      "Wait for it to finish compiling\n"
                      "A browser window should open to the demo application, if not\n"
                      "then open 'http://localhost:3449/index.html' in your browser"))
      (apply ->files data
             (cond-> [["README.md" (render "README.md" data)]
                      ["project.clj" (render "project.clj" data)]
                      ["dev/user.clj" (render "user.clj" data)]
                      ["src/{{sanitized}}/core.cljs" (render "core.cljs" data)]
                      ["resources/public/index.html" (render "index.html" data)]
                      ["resources/public/css/style.css" (render "style.css" data)]
                      [".gitignore" (render "gitignore" data)]]
               bundle?
               (concat [["package.json" (render "package.json" data)]
                        ["webpack.config.js" (render "webpack.config.js" data)]]))))))
