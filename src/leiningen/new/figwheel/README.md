# {{name}}

FIXME: Write a one-line description of your library/project.

## Overview

FIXME: Write a paragraph about the library/project and highlight its goals.

## Setup

To get an interactive development environment run:

    lein figwheel

and open your browser at [localhost:3449](http://localhost:3449/).
This will auto compile and send all changes to the browser without the
need to reload. After the compilation process is complete, you will
get a Browser Connected REPL. An easy way to try it is:

    (js/alert "Am I connected?")

and you should see an alert in the browser window.

To clean all compiled files:

    lein clean

The Clojurescript compiler can be slow sometimes. To help with that,
first-time Clojurescript developers, add the following to your bash
.profile:

    LEIN_FAST_TRAMPOLINE=y
    export LEIN_FAST_TRAMPOLINE
    alias cljsbuild="lein trampoline cljsbuild $@"

To avoid compiling ClojureScript for each build, AOT Clojurescript locally in your project with the following:

    lein trampoline run -m clojure.main
    user=> (compile 'cljs.closure)
    user=> (compile 'cljs.core)

For more info, read [Waitin'](http://swannodette.github.io/2014/12/22/waitin/).

## License

Copyright © 2014 FIXME

Distributed under the Eclipse Public License either version 1.0 or (at your option) any later version.
