# tileswap

try it out on [github pages](https://tarnung.github.io/tileswap/).

A [re-frame](https://github.com/Day8/re-frame) application.

to get an endless supply of cat-pictures i use http://thecatapi.com/
fill in your own api-key in api_key.cljs or set up another image source

## Development Mode

### Run application:

```
lein clean
lein figwheel dev
```

Figwheel will automatically push cljs changes to the browser.

Wait a bit, then browse to [http://localhost:3449](http://localhost:3449).

## Production Build


To compile clojurescript to javascript:

```
lein clean
lein cljsbuild once min
```
