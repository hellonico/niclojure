## Rapid And Fun Web Development in Clojure

### Lord of the Ring: your best ally for clojure web development

[Ring](https://github.com/ring-clojure/ring) is a library, or more precisely a set of libraries inspired like Ruby's Rack.

Ring is a low level framework, meaning you import and use only what you want, but it also means you have to graps a somewhat good understanding of the different concepts.

#### Concepts
The core components of a Ring application are:

##### Handler

A ring handler is a Clojure function that receieve a request map in input and should return a response map. For a simple example:

    (defn what-is-my-ip [request]
      {:status 200
       :headers {"Content-Type" "text/plain"}
       :body (:remote-addr request)})

##### Request

A ring request, is simply a map. The different keys of that map would be:

* :server-port The port on which the request is being handled.
* :server-name The resolved server name, or the server IP address.
* :remote-addr The IP address of the client or the last proxy that sent the request.
* :uri The request URI (the full path after the domain name).
* :query-string The query string, if present.
* :scheme The transport protocol, either :http or :https.
* :request-method The HTTP request method, which is one of :get, :head, :options, :put, :post, or :delete.
* :content-type The MIME type of the request body, if known.
* :content-length The number of bytes in the request body, if known.
* :character-encoding The name of the character encoding used in the request body, if known.
* :headers A Clojure map of lowercase header name strings to corresponding header value strings.
* :body An InputStream for the request body, if present.

##### Response

A Ring response is a map with three parameters:

* :status The HTTP status code, such as 200, 302, 404 etc.
* :headers A Clojure map of HTTP header names to header values. 
* :body The content of the response body. It can be of type:
** String
** ISeq
** File
** InputStream

##### Middleware

A middleware is a high level function that takes a handler (at least) as parameter and returns a function that acts as a handler.
For example, Parameters, sessions, and file uploading are handled by middlewares in Ring.

#### Ring the world

For a HelloWorld state of the art example, we will add ring to our project with:

    [ring "1.1.8"]

And we are ready for a quick session at the REPL. As we have seen before, we will need a Ring handler to handle the request. 

@@@ ruby chapter04/src/chapter04/core.clj @@@

Now we need something to do all the HTTP layer. In this example we are using Jetty, which is the most reliable thin web server on the JVM. It is already included in the parent package *ring* so we do not need more dependencies. 

Here is how we start the server:

@@@ ruby chapter04/src/chapter04/jetty.clj @@@

Now we can head to [http://localhost:3000](http://localhost:3000) and Ring the world !

#### Ring the world 2: Using leiningen

Now bootstrapping your web application form the REPL can be slightly overcooked, and we do not want that for a great meal. We want something tastier.

In our project.clj file, we are going to add two savoury lines:

      :plugins [[lein-ring "0.8.3"]]
      :ring {:handler chapter04.core/handler}

What this does is obviously getting a sweet leiningen plugin named [lein-ring](https://github.com/weavejester/lein-ring), then specify which handler to use when calling it.

Now starting the server is a matter of calling the waiter with:

    lein ring server

And here is the result ! And look it took you so much time for this book to get into your hands !

![ring](../images/chap04/ring1.png)

Note also that there is nothing you need to do to refresh the webapp with new code, editing the code of the handler to immidiately see changes ! 

Agile wine we call it. 

#### Ring the world 3: Standalone server

By using the lein-ring command we have seen above, we can generate a standalone customer web server !
Look at this:

    lein ring uberjar

It will install a bunch of dependencies for you:

    Created /Users/Niko/projects/mascarpone/chapter04/target/chapter04-0.1.0-SNAPSHOT.jar
    Including chapter04-0.1.0-SNAPSHOT.jar
    Including java.classpath-0.2.0.jar
    Including ring-refresh-0.1.2.jar
    Including clj-stacktrace-0.2.5.jar
    Including servlet-api-2.5.jar
    Including ns-tracker-0.2.1.jar
    Including javax.servlet-2.5.0.v201103041518.jar
    Including ring-servlet-1.1.8.jar
    Including ring-server-0.2.8.jar
    Including clojure-1.5.0-RC3.jar
    Including clout-1.0.1.jar
    Including core.incubator-0.1.0.jar
    Including ring-1.1.8.jar
    Including tools.macro-0.1.0.jar
    Including commons-io-2.1.jar
    Including ring-devel-1.1.8.jar
    Including clj-time-0.3.7.jar
    Including hiccup-1.0.0.jar
    Including watchtower-0.1.1.jar
    Including ring-core-1.1.8.jar
    Including jetty-http-7.6.1.v20120215.jar
    Including jetty-util-7.6.1.v20120215.jar
    Including ring-jetty-adapter-1.1.8.jar
    Including commons-codec-1.6.jar
    Including joda-time-2.0.jar
    Including compojure-1.1.5.jar
    Including jetty-server-7.6.1.v20120215.jar
    Including jetty-io-7.6.1.v20120215.jar
    Including tools.namespace-0.1.3.jar
    Including commons-fileupload-1.2.1.jar
    Including jetty-continuation-7.6.1.v20120215.jar
    Created /Users/Niko/projects/mascarpone/chapter04/target/chapter04-0.1.0-SNAPSHOT-standalone.jar

Remember the *uberjar* command we saw a few chapters ago ? This is the ring version of it. Now anyone can run your server on his own machine, also very sweet for fast deployment.
We start it the usual way with:

    java -jar target/chapter04-0.1.0-SNAPSHOT-standalone.jar 

And you can find the ring handler in action again.

#### Ring the world 4: Deploy to traditional web servers

Yet another command to package your code into something that is known in the java world as a *web archive*, in other words a war file.

You call the waiter yet again with:

    lein ring uberwar
    
And he will tell you:

    Created /Users/Niko/projects/mascarpone/chapter04/target/chapter04-0.1.0-SNAPSHOT-standalone.war

Which means you are now ready to copy that file into the deployment directory and beneciate of clustering, and a few other rubies well implemented in traditionnal java web servers.

##### The boss of java webserver in two minutes of clojure
[Immutant](http://immutant.org/tutorials/installation/index.html)

#### When you need an oauth, you have friends
[friend oauth](https://github.com/ddellacosta/friend-oauth2)
[examples](https://github.com/ddellacosta/friend-oauth2-examples)




### Not everything is Noir. But it sure help to develop a web site with so few lines of code

[https://github.com/ibdknox/noir](https://github.com/ibdknox/noir) 
[http://www.webnoir.org/](http://www.webnoir.org/)

It's dark in here ! Noir is probably *the* simplest way to write a functional web application in clojure.

This is how it looks like in Clojure code

@@@ ruby 08_noir.clj @@@

and/or this is how you would have it started in a few seconds with the leiningen command we installed earlier on

@@@ ruby 09_noir.sh @@@

Now the guy from Noir has also started a very cute project named [Lighttable](http://www.kickstarter.com/projects/ibdknox/light-table). You can see the [Playground](http://app.kodowa.com/playground) where you can do live execution of your code with an awesome new type of UI for programming. 

![Alt text](../images/LightTable.png)

### Vaadin, or Google Web Toolkit without all the plumbery, just with the fun
[http://dev.vaadin.com/wiki/Articles/ClojureScripting](http://dev.vaadin.com/wiki/Articles/ClojureScripting)

This is not so much a library than a way to develop slick web application using Vaadin, but without the java boiler plate code. 

#### Sample Vaadin Application to get started
[https://github.com/hsenid-mobile/clj-vaadin](https://github.com/hsenid-mobile/clj-vaadin)
[Original Vaadin Sampler](http://demo.vaadin.com/sampler) 
(https://github.com/weavejester/lein-ring with Vaadin)[https://github.com/weavejester/lein-ring]

### When you see something named Compojure, know it's a cool webframework
[Compojure](https://github.com/weavejester/compojure) 
more [weavejester](https://github.com/weavejester)

### Has websocket development become so simple ? Thanks Aleph
[https://github.com/ztellman/aleph](https://github.com/ztellman/aleph)
Websockets

### Building RESTful applications has no more secret to you
[https://github.com/clojure-liberator/liberator](https://github.com/clojure-liberator/liberator)

### Some more on Web Testing 

#### VCR or your HTTP Playback 

[vcr-clj](https://github.com/fredericksgary/vcr-clj) is a clojure library in the spirit of the VCR Ruby library. It lets you record HTTP interactions as you run your tests, and use the recordings later to play back the interaction so you can run your tests in a repeatable way without needing the external components to be available/accessible.

@@@ ruby chapter03/src/vcr.clj @@@

#### Ring App Testing
[Kerodon](https://github.com/xeqi/kerodon) could be the easiest way to test your Ring library:

@@@ ruby chapter03/src/kerodon.clj @@@
