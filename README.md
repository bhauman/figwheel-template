# figwheel-template

A Leinigen template to get started with [Figwheel](https://github.com/bhauman/lein-figwheel).

## Usage

Make sure you have the [latest version of leiningen installed](https://github.com/technomancy/leiningen#installation).


    lein new figwheel hello-world

### Options

    --reagent   Adds a bare bones Reagent app.
    --rum       Adds a bare bones Rum app.
    --om 		Adds a bare bones Om app, including Sablono.


Include the options using `--` to separate them from Leiningens
options, like so

    lein new figwheel hello-world -- --om

## Usage

To get an interactive development environment run 

    lein figwheel

from the project root directory.

Wait ... until the project finishes compiling and then ...
open your browser at [localhost:3449/index.html](http://localhost:3449/index.html).

Figwheel will now be running and will auto compile and send all
changes to the browser without the need to reload.

After the compilation process is complete, and you have loaded the
compiled project in your browser you will get a ClojureScript REPL
prompt that is connected to the browser.

An easy way to verify this is:

    (js/alert "Am I connected?")

and you should see an alert in the browser window.

To clean all compiled files:

    lein clean

To create a production build run:

    lein do clean, cljsbuild once min

And open your browser in `resources/public/index.html`. You will not
get live reloading, nor a REPL. 


## From the Clojure REPL

Alternatively you can lauch Figwheel from the REPL.

If you run `lein repl` a Clojure REPL will launch.

From this REPL prompt you can run

```
user> (fig-start)
```

and this will launch figwheel but will not launch the ClojureScript
REPL.

You can launch the Figwheel ClojureScript REPL like so:

```
user> (cljs-repl)
```

Please see `dev/user.clj` for more information.

Note: you should always run `lein figwheel` to verify that you have
setup everything correctly.


## License

Copyright © 2014 FIXME

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
