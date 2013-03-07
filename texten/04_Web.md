## Rapid And Fun Web Development in Clojure

###### プラ（Plat） ポワソン:メインのお魚料理

### Lord of the Ring: your best ally for Clojure web development

[Ring](https://github.com/ring-clojure/ring) is a library, or more precisely a set of libraries inspired like Ruby's Rack.

Ring is a low level framework, meaning you import and use only what you want, but it also means you have to graps a somewhat good understanding of the different concepts.

#### Concepts
The core components of a Ring application are:

##### Handler

A ring handler is a Clojure function that receive a request map in input and should return a response map. For a simple example:

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

#### Easy HTTP routes with Compojure

##### Routing to Celebrity

So now we have the basics of our app, but we may want to do something slightly more sexy and not render the same page each time.

To achieve this we are going to use [Compojure](https://github.com/weavejester/compojure) and go through a very [sample app](https://github.com/weavejester/compojure-example).

Compojure is already included in Ring , as we can see in the dependency list, but to get proper fine control over your application, here is how to rock your project.clj:

    [compojure "1.1.5"]
    [hiccup "1.0.2"]

We will also include hiccup as we have seen in Chapter 01. The rest of the project definition should have no mystery for you now:

@@@ ruby chapter04_01/project.clj @@@

Our Application will have two files, one that defines the routing and another one that defines our views.

Let's go through the routing part:

@@@ ruby chapter04_01/src/compojure/example/routes.clj @@@

The routing uses a line-based DSL, with each line splitted in 4 sections:

    (GET "/" [] (index-page))

GET : First comes the HTTP method that applies to that route, here we use GET. All the other ones are available, and a special ANY matches everything

"/" : The matching route itself. In the second example, we have "/user/:id", so we can retrieve parameters later from the URL.

[] : Here is an array that allows to retrieve information from the request map.

(index-page) : From chapter 01 we have seen that hiccup generated calls are simple Clojure functions. For the record, here is the content of that function:

@@@ ruby chatper04_01/src/compojure/example/views.clj @@@

Now we are ready, we can fire our ring development environment with the method we have seen before and start hacking:

    lein ring server

Now, you realize you have all you need to also develop a simple but robust JSON based web service. We leave this out as an exercice, but try to include the library we have seen before, *cheshire* and return a JSON map with the correct content-type in the response.

##### Notes on Clojure destructuring feature

Now is the perfect to recall that Clojure comes with something named *destructuring*, which in other words means accessing only the data you want from a data sequence.

You remember our second parameters, [], in the routing DSL. Let's get back to it.

    (GET "/" [] (index-page))

Let's say I want to retrieve the IP address of the visitor. We can use the second parameter as a desctructuring form, which means creating a subset of the map of the request itself. (You do remember the request is a simple map from earlier in the Chapter do you ?) Slightly updating the above sample, we will get:

    (GET "/yourip" {ip :remote-addr} (str "Your IP is:" ip))

What have we done here ? We have retrieved the :remote-addr parameter of the original request map. But .. wait. What was the original request map again ? Here we go:

@@@ ruby chapter04_01/resources/public/request-sample.json @@@

If you have started your ring server, you can also see it with by accessing:

    http://localhost:3000/request-sample.json

Next, onto some security stuff.

#### Friends of the world. You have my oauth

[Friend](https://github.com/cemerick/friend) is Rack's warden, or Java's Spring Security ported to simple-ness and Clojure so we can make more friends, relate to more people and bring peace to the world.

[friend oauth](https://github.com/ddellacosta/friend-oauth2)
[examples](https://github.com/ddellacosta/friend-oauth2-examples)

#### JSON of the world

We all know we cannot really build a proper service these days without a kind of JSON API. No need to worry, our waiter is not going to keep you waiting.
There was a time where calling a remote service was a pain, but today we are going to see how [ring-json](https://github.com/ring-clojure/ring-json) will make you finish work before you even started.

To add it to your project, we go with:

    [ring/ring-json "0.2.0"]

This little piece of code shows us how to turn Clojure data structures into proper JSON without doing anything:

@@@ ruby chapter04_03/src/chapter04_03/core.clj @@@

We start by importing a new Ring middleware into the namespace, and wrap the Ring handler with a method called *wrap-json-response*. That's it.

Accessing the server at [http://localhost:3000](http://localhost:3000) will return properly formatted json code.

In our second example, we will post json data to our service, and directly retrieve Clojure data structures.

The code on the server side do not even get complicated:

@@@ ruby chapter04_03/src/chapter04_03/core2.clj @@@

And using Curl on the command line, we can send our new service a request with:

    curl -v \
        -H "Accept: application/json" \
        -H "Content-type: application/json" \
        -X POST \
        -d '{"user":"Clojure"}'  \
        http://localhost:3000

The following JSON: 

    {"user":"Clojure"}

Will be converted automatically with the middleware wrap-json-body to the structure:

    {"user" "Clojure"}

Merci Monsieur !

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

##### JBoss AS 7

A few years ago I could not go anywhere without seeing a JBoss server at the forefront of the architecture. There are a lot of friends working on cool projects there, (Salut Thomas!) so I thought I would do a quick favor by showing how to setup a war file into JBoss in a few steps.

You can download the server from the [projects page](http://www.jboss.org/projects) and the [direct download page](http://www.jboss.org/jbossas/downloads/).

Once downloaded, we can see the following set of files:

![jboss1](../images/chap04/jboss1.png)

This is not a JBook bokk. Sorry a JBoss book, so for detailed explanation on A..Z please refer to their documentation. 

We will need a user for the JBoss admin console, and the script to perform this is in the bin folder, and is named *add_user.sh*.

After a bit of old fashioned script interaction:

    [Niko@Modrzyks-MacBook-Pro][17:58][~/Downloads/jboss-as-7.1.1.Final/] % ./bin/add-user.sh  

    What type of user do you wish to add? 
     a) Management User (mgmt-users.properties) 
     b) Application User (application-users.properties)
    (a): 

    Enter the details of the new user to add.
    Realm (ManagementRealm) : 
    Username : clojure
    Password : 
    Re-enter Password : 
    About to add user 'clojure' for realm 'ManagementRealm'
    Is this correct yes/no? yes
    Added user 'clojure' to file '/Users/Niko/Downloads/jboss-as-7.1.1.Final/standalone/configuration/mgmt-users.properties'
    Added user 'clojure' to file '/Users/Niko/Downloads/jboss-as-7.1.1.Final/domain/configuration/mgmt-users.properties'

We are almost ready to hit the admin console through the browser. But, first let's start the server:

    ./bin/standalone.sh

And now we can head to:

    http://127.0.0.1:9990/console/App.html#server-overview

It will greets you with a desperate need for authentication...

![jboss2](../images/chap04/jboss2.png)

That comes just in time, because we have just registered a user a few seconds ago didn't we ? 
Let's use the same user and login.

![jboss3](../images/chap04/jboss3.png)

And following the few shots below we can upload our war file to jboss.

![jboss4](../images/chap04/jboss4.png)
![jboss5](../images/chap04/jboss5.png)

And lastly enable the application context:

![jboss6](../images/chap04/jboss6.png)

Our application is located in a default context taken from the name of the war file, so we would go to:

    http://localhost:8080/chapter04-0.1.0-SNAPSHOT-standalone/

To see our wonderful time application ! The time has changed. And wine has probably come.

What that means in simple terms is that a full on Java shop can now be a full Clojure shop without anyone complaining much about new powerful and simple language being pushed to production. There is simply no bad aspect of this deployment, ring and roll.

##### Apache Tomcat 7

The previous recipe was a bit long to explain because JBoss has a vast support for custom deployment solutions and a nice bunch of features for production quality services.

Now this is going to be way shorter. Apache Tomcat has been enjoying continuous support and engineering for JVM based server deployment.
A long long time ago, when the grapes were young, we introduced Tomcat 3 for a production quality application and the memory usage for superb enough that it actually improved the number of connections we could handle compared to other paid servers at the time. I cherished that time so much. We save money and we did better.

You can [download Tomcat](http://tomcat.apache.org/download-70.cgi) and once you have unzipped the archive, we can start it with:

    ./bin/startup.sh

![tomcat](../images/chap04/tomcat1.png)

We can copy the same war file we generated before to the webapp folder of the Tomcat install, and we can see the result just as before:

    http://localhost:8080/chapter04-0.1.0-SNAPSHOT-standalone/

We are still right on time !

We are going to reuse Tomcat later, so let's stay focused. 

### The boss of java webserver in two minutes of clojure
[Immutant](http://immutant.org/tutorials/installation/index.html)
Deployment section ?

### Not everything is Noir. But it sure help to develop a web site with so few lines of code


[https://github.com/ibdknox/noir](https://github.com/ibdknox/noir) 
[http://www.webnoir.org/](http://www.webnoir.org/)

It's dark in here ! Noir is probably *the* simplest way to write a functional web application in clojure.

This is how it looks like in Clojure code

@@@ ruby 08_noir.clj @@@

and/or this is how you would have it started in a few seconds with the leiningen command we installed earlier on

@@@ ruby 09_noir.sh @@@

### Vaadin, or Google Web Toolkit without all the plumbery, just with the fun
[http://dev.vaadin.com/wiki/Articles/ClojureScripting](http://dev.vaadin.com/wiki/Articles/ClojureScripting)

This is not so much a library than a way to develop slick web application using Vaadin, but without the java boiler plate code. 

#### Sample Vaadin Application to get started
[https://github.com/hsenid-mobile/clj-vaadin](https://github.com/hsenid-mobile/clj-vaadin)
[Original Vaadin Sampler](http://demo.vaadin.com/sampler) 
(https://github.com/weavejester/lein-ring with Vaadin)[https://github.com/weavejester/lein-ring]

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
