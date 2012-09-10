(use 'clostache.parser)

(render "Hello, {{name}}!" {:name "Felix"})

(render "<ul>{{#names}}<li>{{.}}</li>{{/names}}</ul>" {:names ["Felix" "Jenny"]})

(render-resource "code/sample.mustache" {:name "Michael"})