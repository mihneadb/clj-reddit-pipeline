(defproject clj-reddit-pipeline "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/data.json "0.2.5"]
                 [org.clojure/core.async "0.1.346.0-17112a-alpha"]
                 [http-kit "2.1.18"]
                 [mynx "2.0.0"]]
  :main ^:skip-aot clj-reddit-pipeline.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
