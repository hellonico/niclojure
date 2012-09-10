(require '[clj-rss.core :as rss])

(rss/channel-xml {:title "Foo" :link "http://foo/bar" :description "some channel"}
                 {:title "Foo"}
                 {:title "post" :author "author@foo.bar"}
                 {:description "bar"})