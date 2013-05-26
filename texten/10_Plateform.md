## Plateform

Now this is it. This is the last chapter of this book ! We have gone a long way into the landscape of Clojure, and all the different personalities the language can take.

This last chapter is about expanding the scope of our favorite language. So far we have seen it running mostly on what we have seen was the Java Virtual Machine.
But there are probably times when you want to still use Clojure, but you do not want to, or cannot use the JVM *and* when you want to reuse some Clojure code in a completely different environment.

We will see how we can run Clojure code on top of the Ruby virtual machine with Rouge, then a trip to Microsoft.NET environment and see how we can integrate Clojure seamlessly with Microsoft VMs, and finally the new and exciting Clojurescript, which brings Clojure to any javascript runtime, making Clojure on par with other new cool kids like coffeescript.

Let's enjoy every bits of it !

### I see! Ruby + Clojure = Rouge !

Rouge should maybe have been presented in the first chapter of this book. Seriously, it is quite very impressive. I also had some questions and team responded and fix problems in less than an hour. 

So why Rouge ? Well, Rouge means red, and there can't be too much of a good thing, especially with good red wine.

[Rouge](http://rouge.io/), Clojure on the Ruby VM, provides two great advantages:

* Quick boot time (currently around 0.1s).
* Ruby's gems are fun to use, tend to be modern with decent APIs.
* Clojure is awesome.

#### First baby steps: Clojure walks on rubies

You can try a Rouge REPL online at [Try Rouge](http://try.rouge.io/), or install the gem to get the local REPL:

    gem install rouge-lang
    rouge

And that's it, we have a Clojure REPL ready to go.

So what can we do with it ? 

Our first example shows some basic Clojure integration code:

@@@ ruby chapter10/rouge/first.rg @@@

Sweeet !!! It feels like pure Clojure, and it's super fast. 

But wait, there's more. 

#### Teenager: Clojure gets some gems

##### Mechanize the web

The ruby world has a fantastic gem called Mechanize. 

The Mechanize library is used for automating interaction with websites. Mechanize automatically stores and sends cookies, follows redirects, and can follow links and submit forms. Form fields can be populated and submitted. Mechanize also keeps track of the sites that you have visited as a history.

Now that we have rouge, we can now use the mechanize library directly from Clojure. Have a look.

Let's install the gem with:

    gem install mechanize

And now we can refer to it straight from our code:

@@@ ruby chapter10/rouge/mechanize-rg/test.rg @@@

And finally, *You* can give it a try with:

    rouge test.rg

We can see the code integration works more or less the same as the java integration, we are using the dot notation to reference methods coming from the virtual machine world.

Sweet ! 

##### Events in a world of rubies

Supposing now we are installing [eventmachine](http://rubyeventmachine.com/), the fast, simple event-processing library for Ruby programs...

    gem install eventmachine

We write quickly some wrappers in our namespace this way:

@@@ ruby chapter10/rouge/em-rg/em-rg.rg @@@

And now we can refer to those wrappers using the magical, but usual *require* directive in our namespace definition:

    (:require [em-rg :as em])

And ... Voila !

@@@ ruby chapter10/rouge/em-rg/test.rg @@@

Ruby timers controlled from Ruby. The output of the above will be:

    2013-05-24 18:35:02 +0900 Execution begins.
    2013-05-24 18:35:03 +0900 First timer.
    2013-05-24 18:35:04 +0900 Bye bye.

But go for it ! Try it yourself, and enjoy the starting speed of the Ruby VM for some very ingenious Clojure scripting.

### ClojureCLR (clojure on .NET)

[Clojure CLR](https://github.com/richhickey/clojure-clr) is a native port of Clojure to the CLR, Microsoft's Common Language Runtime, or .NET .

ClojureCLR is programmed in C# (and Clojure itself) and makes use of Microsoft's
Dynamic Language Runtime (DLR).

#### Getting Started

As exposed on the website, the goals for Clojure's CLR are multiple:

 -- Implement a feature-complete Clojure on top of CLR/DLR.
 -- Stay as close as possible to the JVM implementation.
 -- Have some fun. 

To start having fun, you would of course need [.NET](http://www.microsoft.com/ja-jp/download/details.aspx?id=17851) to be running some wineries on your local machine.
If just anything, I am trying to lock down my windows environment to VirtualBox.

[Getting the binaries](https://github.com/clojure/clojure-clr/wiki/Getting-binaries) is mostly downloading the binary toyour machine, running unzip, and starting a Clojure REPL using the Clojure.Main executable.

Here is the content of the zip file:

![../images/chap10/clr1.png]

Here is a REPL running on the CLR:

![../images/chap10/clr2.png]



Now that I’ve shown you how to make a call into .NET from the REPL let’s have some CLR fun.

#### First steps in the .NET world

This is using the following [blog entry](http://www.myclojureadventure.com/2011/10/getting-started-with-clojure-clr.html), getting started with Clojure CLR.

Now that we know we have the REPL working it is time to try out a few calls into the .NET world.  The first call we will make is to Console.WriteLine.  Since System.Console is loaded automatically by the REPL we can call it like this: 

    (System.Console/WriteLine “I just called a .NET method!”)

![../images/chap10/net1.png]

One way to load a .NET assembly makes use of the Assembly/LoadWithPartialName method.  Here’s what it looks like:

    (System.Reflection.Assembly/LoadWithPartialName "System.Windows.Forms")

The results of this call will give you the same output as the more verbose Load call did.  Either way you choose will work fine.  If loading a particular version of a an assembly is crucial then I would go with the Load call.  Otherwise I would stick with the LoadWithPartialName method. 

Now that we have loaded System.Windows.Forms I’m going to include it into our namespace so I can use the MessageBox class so I can make calls into it a little cleaner.  I can do that by typing the following in the REPL

    (import (System.Windows.Forms MessageBox))

The call above tells the REPL we want to bring the System.Windows.Forms.MessageBox into our user namespace.  This will allow us to use MessageBox/Show instead of System.Windows.Forms.MessageBox/Show.   When entered the line of code below I saw the dialog box that follows it. Pretty straight forward.

    (MessageBox/Show “Hi from clojure-clr!” “Clojure-CLR Dialog”)

![../images/chap10/net2.png]

Straight forward ? Now let's look in the mirror and ...

#### .NET integration: Calling Clojure from .NET

This is taken mostly from the following [answer on StackOverflow](http://stackoverflow.com/questions/4380054/calling-clojure-from-net) because it shows an easy way to understand how to do things in the Windows world and Clojure.

Let's say we have the following Clojure code:

    (ns hello
      (:require [clojure.core])
      (:gen-class
       :methods [#^{:static true} [output [int int] int]]))
    (defn output [a b]
      (+ a b))
    (defn -output [a b]
      (output a b))
    (defn -main []
      (println (str "(+ 5 10): " (output 5 10))))

We can compile it by calling the following command in a [Command window](http://code.google.com/p/conemu-maximus5/).

    Clojure.Compile.exe hello

This will create several files:

* hello.clj.dll, 
* hello.clj.pdb, 
* hello.exe, and 
* hello.pdb  

You can execute hello.exe and it should run the -main function.

    C:\Users\niko\Desktop\Debug 4.0>hello.exe
    (+ 5 10): 15

We will now call the above code from a simple C# console application. I then added the following references: Clojure.dll, hello.clj.dll, and hello.exe

Here is the code of the console app in C#

    using System;
    namespace ConsoleApplication1
    {
        class Program
        {
            static void Main(string[] args)
            {
                hello h = new hello();
                System.Console.WriteLine(h.output(5, 9));
                System.Console.ReadLine();
            }
        }
    }

As you can see, you should be able to create and use the hello class, it resides in the hello.exe assembly. I am not why the function "output" is not static, I assume it's a bug in the CLR compiler. I also had to use the 1.2.0 version of ClojureCLR as the latest was throwing assembly not found exceptions.

In order to execute the application, make sure to set the clojure.load.path environment variable to where your Clojure binaries reside.

Et voila ! No more excuses for using any other scripting language. You already have the best grapes !

#### Lein CLR: Leinigen takes Clojure and .Net even closer

Now we haven't read and followed all this book to just do things the hard core way. Of course there is a Leiningen plugin for it.

[lein-clr](https://github.com/kumarshantanu/lein-clr) is a Leiningen plugin to automate build tasks for ClojureCLR projects. And the good news, you can use it for both .NET and [Mono](http://www.mono-project.com/Main_Page).

We install it as a regular Leiningen 2 plugin with:

    :plugins [[lein-clr "0.2.0"]]

And then we can create the barebone of our CLR project with:

    lein new lein-clr clr-test

From now on, we need to define where our CLR is installed with a command like:

    set CLJCLR14_40=\Users\niko\Desktop\Debug 4.0
    

Then we are ready to test our code:

    lein clr test
    

And the usual clojure project output shows on the console:

    Testing foo.core-test
     
    FAIL in (a-test) (:)
    FIXME, I fail.
    expected: (= 0 1)
      actual: (not (= 0 1))
    
    Ran 1 tests containing 1 assertions.
    1 failures, 0 errors.
    {:type :summary, :test 1, :pass 0, :fail 1, :error 0}

The Leiningen CLR plugin covers the usual task for a Clojure project:

    lein clr [-v] clean
    lein clr [-v] compile
    lein clr [-v] repl
    lein clr [-v] run [-m ns-having-main] [arg1 [arg2] ...]
    lein clr [-v] test [test-ns1 [test-ns2] ...]

That's most of it for our very short presentation of Clojure CLR, the rest is up to you to deploy your favorite Clojure application on Windows/.NET now. 

Happy Windows Sweet Wine. 

### ClojureScript (clojure compiling to Javascript)

[ClojureScript](https://github.com/clojure/clojurescript) is Clojure's answer to Javascript, probably one of the most dynamic runtime nowadays.
ClojureScript is a new compiler for Clojure that targets JavaScript. It is designed to emit JavaScript code which is compatible with the advanced compilation mode of the Google Closure optimizing compiler.

[This page](http://himera.herokuapp.com/synonym.html) presents in two columns the differences between Clojurescript and Javascript. Please take the time to go and read through it. You should be finish a single glass of wine anyway.

There is a ready to use [online REPL](http://www.clojurescript.net/) at:

    http://www.clojurescript.net/

If you want to know more about the syntax a few books have started to appear on the subject. As usual, we are less interested in the syntax itself, and more about the ecosystem around Clojurescript.

#### Our first Clojure Script project, is actually in Javascript.

To get up to speed quickly, we are going to look at how to extend a simple ring application, remember our Web chapter ? (or maybe now's a good time to quickly go through it), extend to have Clojure script integrated.

Let's have a look at the *project.clj* file:

@@@ ruby chapter10/cljs-simple/project.clj @@@

We note we have, a new Leiningen plugin, lein-cljsbuild that we integrate in our project with:

    [lein-cljsbuild "0.3.2"]

We also have a new section for clojure script:

    :cljsbuild {
      :builds [{:source-paths ["src-cljs"]
              :compiler {:output-to "resources/public/js/main.js"
                         :optimizations :whitespace
                         :pretty-print true}}]}

Which reads easily enough, we just want to compile all the clojurescript code found in folder *src-cljs* to a file named *main.js* with a few indications for formatting.

Now in the cljs-simple project, we will use a command to compile the clojure script code in the background for us:

    lein cljsbuild auto

While, the regular Clojure code from the *src-clj* is nothing new, let's have a look at the content of src-cljs/example/hello.clj:

@@@ ruby chapter10/cljs-simple/src-cljs/example/hello.clj @@@

Quite basic, it will be compiled into a regular javascript file that we have specified in *project.clj* 

Now, we can start the ring server with (remember?) :

    lein ring server-headless

And head to the local URL: [http://localhost:3000](http://localhost:3000).

The result is nicely expected:

![cljs1](../images/chap10/cljs1.png)

Now changing the content of *hello.cljs* a little with:

    (js/alert "おはよう ClojureScript!")

In the terminal with the *lein cljsbuild auto* command still running in the background, we can see the update is compiled directly on save:

![cljs3](../images/chap10/cljs3.png)

And the result shows on a reload of the page in the browser:

![cljs2](../images/chap10/cljs2.png)

Play around with a few commands, and see how it goes yourself !

#### Live Clojure script in the browser ! REPL, steroids and desert wine

Now a slightly more fun and advanced example. The project is located in *chapter10/cljs-advanced* and is taken from the lein-cljsbuild sample.

##### Sharing, Shared: Code is fully shared between server and client.

First thing we notice when we look at the *project.clj* file is quite large. Don't be afraid, this is mostly to cover numerous scenario, in a regular application we mostly don't need to write all this.

After noticing the first thing, we can start noticing the second, which is, we have a shared section, with the following two lines:

    :crossovers [example.crossover]
    :crossover-jar true

With those two lines we define a namespace, containing standard Clojure code that will be used for both the client side (compiled to javascript) and the server side (eventually compiled to java).

All this compilation is all done in the background, so while it is better to know about it, there's no limitation if you don't really see all together yet.

So running the server with the usual:

    lein ring server-headless

And heading to the server page, we get:

![cljs4](../images/chap10/cljs4.png)

But wait !! If we follow this through, we realize the code showing in the popup is not in the *src-cljs* folder. It's defined in the standard clojure source path at *src-clj*.

@@@ ruby chapter10/cljs-advanced/src-clj/example/crossover/shared.clj @@@

That's it ! That's where the code is coming from. But how? 

If we look at the source of the HTML page we loaded, we see:

    <script src="/js/main-debug.js" type="text/javascript"></script>
    <script type="text/javascript">//<![CDATA[
    example.hello.say_hello()
    //]]></script>

This has been produced by the server side, the ring handler, so if we go through the regular view path for ring, we find in *views.clj*

    (defn- run-clojurescript [path init]
     (list
      (include-js path)
      (javascript-tag init)))
     
    (defn index-page []
     (html5
      [:head
        [:title (shared/make-example-text)] ]
      [:body
        [:h1 (shared/make-example-text)]
         (run-clojurescript
          "/js/main-debug.js"
          "example.hello.say_hello()")]))

Ok. I see this now. So we just need to check the *hello.cljs* file:

    (ns example.hello
      (:require
        [example.crossover.shared :as shared]))
     
    (defn ^:export say-hello []
       (js/alert (shared/make-example-text)))

And now it all comes swiftly together. So let's see this graphically:

![Flow](../images/chap10/ClojureScript.png)

Note that the shared code section is pretty reactive too. If we change the text in *shared.clj* to, say:

    (defn make-example-text []
      (macros/reverse-eval
        ("code" "shared " "from the " "おはよう " str)))

The change when we reload the browser is shown almost instantly:

![cljs5](../images/chap10/cljs5.png)

Sweet ! That's our first lesson in the advance course. Still ready for more ? It gets better and better.

##### Connecting a clojure script REPL to our browser: Live coding

With the same setup we had in the previous section, namely 2 leiningen commands running.
One for compiling clojurescript in the background:

    lein cljsbuild auto

And another one for the server:

    lein ring server-headless

We now direct our browser to a different page:

    http://localhost:3000/repl-demo

The most interesting part of the server side code, is in the views.clj file again, the function *repl-demo-page* 

    (run-clojurescript
        "/js/main-debug.js"
        "example.repl.connect()")

So we know that this clojurescript code comes from example/repl.cljs:

@@@ ruby chapter10/cljs-advanced/src-cljs/example/repl.clj @@@

Oh ! It opens a connection to a REPL on load. Sweet. So let's *listen* to this client with:
    
    lein trampoline cljsbuild repl-listen 

And .. we are connected ! Client and Server talking the same language through the REPL. And as written on the page, we can "Try some fun REPL commands!". Do and type those commands in the clojure script REPL you have just open and see the results for yourself:

    (js/alert "Hello!")

![cljs10](../images/chap10/cljs10.png)

    (load-namespace 'goog.date.Date)
    (js/alert (goog.date.Date.))

![cljs11](../images/chap10/cljs11.png)

    (.log js/console (reduce + [1 2 3 4 5]))

![cljs12](../images/chap10/cljs12.png)

    (load-namespace 'goog.dom)
    (goog.dom.setTextContent (goog.dom.getElement "fun") "おはようございます!")

![cljs13](../images/chap10/cljs13.png)

Turns to:

![cljs14](../images/chap10/cljs14.png)

Oh wow. That was actually pleasing even just to write in this book.

##### Testing and REPLing with Firefox and PhantomJS

Our latest advanced topic will be to run a browser so as to validate code, and basically is a hint towards testing. 

We will be using a bit of [PhantomJS](http://phantomjs.org/download.html), a headless WebKit scriptable with a JavaScript API. It has fast and native support for various web standards: DOM handling, CSS selector, JSON, Canvas, and SVG.
Basically, PhantomJS does everything your regular browser does, without a way for you to see it.

After you have played a bit with PhantomJS, let's put pieces together.

The repl-launch command of leiningen's cljsuibld, runs a REPL and launch a custom command to connect to it. So with the following command:

    lein trampoline \
    cljsbuild repl-launch \
    phantom http://localhost:3000/repl-demo

We will start a REPL on the command prompt and tell phantomJS to connect through it.
Meaning we achieve the same as the previous section, but in headless mode.

This is actually defined in the *project.clj* file of our project with:

         ; This is similar to "firefox" except it uses PhantomJS.
         ; $ lein trampoline cljsbuild repl-launch phantom <URL>
         "phantom" ["phantomjs"
                  "phantom/repl.js"
                  :stdout ".repl-phantom-out"
                  :stderr ".repl-phantom-err"]

So, if the clojure script REPL we write:

    ClojureScript:cljs.user> (js/console.log "ありがとう")

The result can be seen in the console logs of phantomjs in the *.repl-phantom-out* file ! Have a look:

    Loading URL: http://localhost:3000/repl-demo
    Loaded successfully.
    App console: ありがとう 

It works all the same with the commands we used before, so in the Clojure script console, the following:

     (.log js/console (reduce + [1 2 3 4 5]))

Will output normally in the file above:

    App console: 15

##### Clojure Script first steps: already running

With the bits we have seen, I am sure you are getting bubbling ideas of all the great projects you can start.

To summarize, we have seen how to

* integrate and run clojure script code in a ring-based project
* do live coding from the REPL to the browser
* interact with clojure, clojurescript code with a headless browser, phantomJS.

We have gathered some quite advanced techniques here. With great techniques, comes great powers, comes great wine. Another glass of wine should not hurt.

#### Your whole website in Clojure

This section will follow up where *lein cljsbuild* stops. We will go over and see a few bricks of the Clojurescript landscape to make your development time even faster and pleasant.

We will see Clojure Home Page, or the base framework to have your whole website in Clojure.
Then onwards with some templating powers with Enlive in clojure script.

Lastly, since we love Google's AngularJS, we will also see how to write AngularJS code in clojure script.

Still hungry ? Bon Appetit !

##### Clojure Home Page

To follow up with a new kid on the block in the school of web frameworks, here comes [Clojure Home Page](https://github.com/runexec/chp)

ClojureHomePage is a Compojure based web framework that allows you to write the backend and frontend with Clojure.

You can:

* Run Clojure inside a HTML file with the <clj></clj> tags
* Have multiple method handlers under a single route (get, post, put, delete, and head)
* Easily get common web headers (ex. ($ user-agent))
* Easily get web headers (ex. ($$ cache-control))
* Easily get environmental variables (ex. (env java.vm.name))
* Generate HTML with a drop-in replacement for common Hiccup forms
* Generate JavaScript / ECMAScript with ClojureScript
* Generate CSS with Garden
* Manipulate databases with KormaSQL

I have cloned a basic sample from the *clojure-home-page* repository. Navigating to it, we remember we start with the now familiar two commands:

    lein ring server-headless
    lein cljsbuild auto

In this checkout, we have a new generic ring rouges named *chp-route* :

    (chp-route "/" 
               (or (chp-parse (str root-path "index.chtml"))
                   "error"))

Which basically does the magic for us, and allows for embedding Clojure code and templating with clojure directly into the file.

The template itself, declared with the extension .chtml, will read easily at this stage of the book:

@@@ ruby chapter10/clojure-home-page/chp-root/index.chtml @@@

And it really is Clojure everywhere. With some clojure script in the chtml file and some more clojurescript coming from file:

@@@ ruby chapter10/clojure-home-page/resources/cljs/main.cljs @@@

Et voila.

![chp](../images/chap10/chp1.png)

Clojure Home Page wraps [hiccup](https://github.com/runexec/chp#clojure-and-html-generation) for templating, in those chtml files, and includes the [Garden](https://github.com/noprompt/garden)  library to have fun even when writing CSS code. 

##### Fetch: A ClojureScript library that makes client/server interaction painless.

Remember the noir web library from our web chapter some time ago ?

[Fetch](https://github.com/ibdknox/fetch) introduces *Remotes*, and let you make calls to a noir server without having to think about XHR. On the client-side you simply have code that looks like this:

    (ns playground.client.test
     (:require [fetch.remotes :as remotes])
     (:require-macros [fetch.macros :as fm]))
    
    (fm/remote (adder 2 5 6) [result]
     (js/alert result))
    
    (fm/remote (get-user 2) [{:keys [username age]}]
     (js/alert (str "Name: " username ", Age: " age)))
     
     ;; for a much nicer experience, use letrem
    (fm/letrem [a (adder 3 4)
              b (adder 5 6)]
      (js/alert (str "a: " a " b: " b)))

Note that the results we get are real Clojure datastructures and so we use them just as we would in normal Clojure code. No JSON here.

The noir side of things is just as simple. All you do is declare a remote using defremote.

    (use 'noir.fetch.remotes)
     
    (defremote adder [& nums]
               (apply + nums))
     
    (defremote get-user [id]
               {:username "Chris"
                :age 24})
    
    (server/start 8080)

Easy ? We have included an example using both friendly and fetch, under *friendly-fetch-example*.

The most interesting file is sliced in two parts. The server side:

    (ns friendly.views.welcome
      (:require [friendly.views.common :as common]
                [cemerick.friend :as friend])
      (:use [noir.fetch.remotes]
            [noir.core :only [defpage]]))
     
    (defremote get-user []
      (:username (friend/current-authentication)))
     
    (defremote login [auth]
      (friend/authorize #{:friendly.server/user}
                        (:username (friend/current-authentication))))
     
    (defremote logout [] nil)
     
    (defremote another []
      (friend/authorize #{:friendly.server/user} "This action required logging in!"))

And the client side, making calls from Clojurescript to Clojure and back:

@@@ ruby chapter10/friendly-fetch-example/src-cljs/friendly/client.cljs @@@

We also get a nice example of how to use the google dom library and update the DOM directly with:

    (dom/setTextContent (dom/getElement "currentuser") "")

##### Shoreleave

We would not be presenting the Clojurescript landscape properly without presenting Shoreleave.

[Shoreleave](https://github.com/ohpauleez/shoreleave) is a smarter client-side in ClojureScript. Shoreleave is a collection of integrated libraries that focuses on:

    Security
    Idiomatic interfaces
    Common client-side strategies
    HTML5 capabilities
    ClojureScript's advantages

More concisely (and reductively), Shoreleave is a set of web-app libraries that make it simpler to get a ClojureScript-based client-side connected to a Ring/Compojure-based Clojure backend.

We have included a [barebone shoreleave](https://github.com/ddellacosta/barebones-shoreleave) playground in the chapter10 folder.

The main ring handler file, contains three interesting sections. 

First, The shoreleave middle included and defined in the namespace with:

    [shoreleave.middleware.rpc :refer [defremote wrap-rpc]]

Second, we add it to our route definition:

    (def app
      (-> app-routes
      wrap-rpc
      ...
      handler/site))

Last, we define a remotely available endpoint with:

     ;; https://github.com/shoreleave/shoreleave-remote-ring
     (defremote ping [pingback]
       (str "You have hit the API with: " pingback))

This remote endpoint can then be used from the client side:

@@@ ruby chapter10/barebones-shoreleave/src/barebones_shoreleaves/client/main.cljs @@@

![shoreleave](../images/chap10/shoreleave.png)

Sweet ? But wait, there's more !

Shoreleave's remotes package includes XHR, Pooled-XHR, JSONP, and HTTP-RPC capabilities.

CSRF protection is built in if your Clojure server is using the ring-anti-forgery middleware. See shoreleave-baseline for anti-forgery details.

The HTTP-RPC allows for exposing a server-side namespace as a client-side API, via a single server-side call, remote-ns.

And finally Shoreleave includes a [pub/sub abstraction](https://github.com/ohpauleez/shoreleave#a-pubsub-abstraction-and-implementations) (and implementations)

Shoreleave's pub/sub system enables you to completely decouple parts of your app and declaratively bind them together. New features and functionalities can be built by composing pre-existing services/publishables.

We have included a [barebone shoreleave](https://github.com/ddellacosta/barebones-shoreleave) playground in the chapter10 folder.

A more extensive shoreleave example project is included in the sample codes for chapter10 inside the shoreleave folder.


##### Whole website in Clojure. Where to go from here ?

So what have we seen in this section ? 

* Clojure Home Page, a base line for creating your web application with Clojure on the front end, with Clojurescript and on the backend with Clojure Ring Handlers
* Fetch, a remoting library to explain the advantage of having remoting expose as Clojure functions on both server side and client side.
* Shoreleave, pushes the limit further by providing a very clean remoting interface and a full implementation of a publisher/subscripber framework.

To keep on playing in the Clojure script land, you should also have a look at the following two libraries:

###### Enfocus: Enlive inspired templating for Clojure Script

If we remember again we have seen Enlive templating. And now we will present [enfocus](https://github.com/ckirkendall/enfocus), Enlive inspired templating in Clojurescript.

The [enfocus-site](http://ckirkendall.github.io/enfocus-site/)'s [source code](https://github.com/ckirkendall/enfocus-demo-site.git) itself is worth every second of reading.

###### Domina

[Domina](https://github.com/levand/domina) is a jQuery inspired DOM manipulation library for ClojureScript. It provides a functional, idiomatic Clojure interface to the DOM manipulation facilities provided by the Google Closure library.
Also, Domina contains a robust event handling API that wraps the Google Closure event handling code, while exposing it in a idiomatic functional way. 

In any case, Enjoy your voyage in the Clojure script land !

###### ClojureScript & Canvas: A Simple Breakout Implementation

This is nothing more than a way to squeeze a link to work on the HTML5 canvas with Clojure script.

Have a look at the original [post](http://nakkaya.com/2012/01/31/clojurescript-canvas-a-simple-breakout-implementation/)

And at the awesome screenshot:

![canvas](../images/chap10/canvas.png)

More to come in a future book !

#### Bonus points: Clojurescript with Google's AngularJS

[Clang](https://github.com/pangloss/clang) is ClojureScript well integrated with [Google's AngularJS framework](http://angularjs.org/).

##### What is it?

Clang includes an unmodified current release of AngularJS. It allows you to use ClojureScript data structures throughout your angular app and simplifies writing your controllers and directives, etc according to Angular's best practices. Clang integrates ClojureScript into all of Angular's built-in directives.

##### How is it? 

Clang defines a new $parse provider which is injected throughout Angular and used wherever Angular reads any properties from the scope. It also replaces the Angular $interpolate provider to enable the same thing in {{interpolated}} blocks in your app.

Those two changes enable all of Angular's built in directives to work with ClojureScript except for the ng-repeat which assumes Javascript arrays. Clang's clang-repeat fills that gap.

##### Show me

Here are a couple of bits of code clipped from the sample index.html

This bit calls the remaining function from the scope and applies the built-in count function to the todos vector:

      <span>{{(remaining)}} of {{(count todos)}} remaining</span>
      [ <a ng-click="(archive)">archive</a> ]

The relevant controller definitions:

    (def.controller m TodoCtrl [$scope]
     (scope! todos [{:text "learn angular" :done "yes"}
                 {:text "learn cljs" :done "yes"}
                 {:text "build an app" :done "no"}])
     (defn.scope remaining []
      (->>
       (scope! todos)
       (map :done)
       (remove #{"yes"})
       count)))

Here's a slightly silly but kind of awesome example of building a table:

      <table>
        <tr clang-repeat="group in (drop 1 (partition 3 nums))">
          <td clang-repeat="x in (map (juxt identity odd?) group)">
            {{(first x)}} is {{(if (last x) "odd" "even")}}
          </td>
        </tr>
      </table>

The relevant controller definitions:

    (def.controller m TodoCtrl [$scope]
     (scope! nums (range 1 10)))

###### Try it yourself !

In the clang folder, we use the cljsbuild command to compile the code:

    lein cljsbuild auto dev

And then open the resulting compiled code with:

    open resources/public/index.html

And see real time client side javascript "a-la-angular-js" on your own brower:

![clang](../images/chap10/clang.png)    

### Finishing the chapter10

So this last chapter was a pretty intense presentation of the whole Clojure landscape pushed to some new worlds.

We have gone through this very diverse list of knowledge:

* run Clojure on the Ruby Virtual Machine and how to call ruby gems from Clojure
* run Clojure on .Net, Microsoft's virtual machine, and how to call .NET code from Clojure and the reverse.
* Presented and work through a long list of examples for Clojurescript, and how it is redefining in a very Fresh way how to do web programming.

Hope you enjoyed, finish the last glass of wine and review some working samples to have a nice last impression on this long chapter.


