# clj-reddit-pipeline

Demo app for Clojure, core.async and transducers.
Streams new items from the Reddit API and pushes them via
the WebSocket protocol to the browser.

You need [leiningen](http://leiningen.org/#install).

```bash
lein deps # fetches dependencies
lein run
# wait for a few seconds
# open index.html in a browser
```

