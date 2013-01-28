## Clojure recipes of the day: Finish your work in a few lines

###### Avant amuse: Something you eat before the amuse.

In Chapter 02, we will go through a number of libraries and techniques.
Since this is a menu, there is no specific order for you to read. Jump back and forth, come back when you need to be sure of something, and on top of it, try the short bits of code.  

To run any of the samples in this chapter, go to the directory _chapter02_ of the downloaded code, start a REPL with:
	
	lein repl

then load a file with a command of the like:

	(load-file "src/incanter.clj")

### I'll have the usual

####  Give some colors to your REPL

I am not sure if you noticed, but a menu always get better when you put some colors in it. There is a little gem there to do that in the name of colorize. 

	https://github.com/ibdknox/colorize

Include it in your project.clj with:

	[colorize "0.1.1"]

Here are some ways to use it:

@@@ ruby chapter02/src/colorize.clj @@@

And some graphic results:

![Clojure](../images/chap02/colorize.png)

####  Asynchronous http server and ... client. 

In the same time, you can turn yourself asynchronous with http-kit. 

	https://github.com/shenfeng/http-kit

This is what needs to be added to your project.clj file:

	[me.shenfeng/http-kit "2.0-SNAPSHOT"]

This would be how you set up a server that responds to web requests:

@@@ ruby chapter02/src/http_kit_01.clj @@@

The server will be started, and will delay answer to the client. No dependency so kind of very lightweight and wicked.
It also incorporate a kinda cool http client, again with no dependencies. Give a try.

@@@ ruby chapter02/src/http_kit_03.clj @@@

And it even includes a way to handle web sockets. Start the server with:

@@@ ruby chapter02/src/http_kit_02.clj @@@

And then head on line to a generic websocket testing site:

[http://www.websocket.org/echo.html](http://www.websocket.org/echo.html)

![Websockets](../images/websockets.png)

Power of websockets right at hand !

####  (Too?) Easy statistics with Incanter.

Download a full Incanter distribution from [http://incanter.org/downloads/] and Get started [https://github.com/liebke/incanter/wiki#getstarted](https://github.com/liebke/incanter/wiki#getstarted)

The download above includes a bunch of stuff you may not need. A faster way to get started is to go the usual route with Leiningen.

	[incanter "1.3.0"]

Then you can play around:

@@@ ruby chapter02/src/incanter.clj @@@

The image below has been generated from the script above ! 

![Incanter](../images/histogram.png)

Whats your next diagram ? 

Incanter has become a full package to perform efficient stats just like the *R* language. It can also export to PDF and Excel files, generates images, and perform stats in full time. 

There are also plenty of examples on the author website, relevant to Clojure in general so definitely worth having a look.

####  Where the doc ? It's literate. 

[https://github.com/fogus/marginalia](https://github.com/fogus/marginalia)

Marginalia is your best literate programming tool for Clojure. Which means, if you write the necessary comments within your code, marginalia will generate the best documentation for your project. Then you just have to publish it online. 

Install it as a dependency in your *~/.lein/profiles.clj* file:


	{:user {:plugins [
                  [lein-marginalia "0.7.1"] ; <- add this
                  [lein-pprint "1.1.1"]]}}

Then, just use it:

	lein marg

And depending on the amount of comments you wrote in the code, you will get something similar to this:

![Alt text](../images/marginalia.png)

It will show your dependencies, and comments inline with the code.

####  Easy and beautiful graphs

[https://github.com/pallix/lacij](https://github.com/pallix/lacij)

Now you have all that data computed in Clojure, and you are in need of a great one liner library to turn this into a graph. Lacij is a library that can quickly create SVG diagram with automatic layout, like this one:

![SVG](../images/circle.svg)

Then, a graph can be drawn like this:

@@@ ruby chapter02/src/lacij.clj @@@

The advantage is that you can add and remove nodes dynamically, thus giving a dynamic view about live typologies.

Try some other examples, to generate great graphs. Also, see how it is easy to open a Desktop window and embed the content of the graph within it:

![Lacij](../images/lacijswing.png)

####  Easy User Interface Applications with Seesaw

Make cool Application UIs in no time. Like really no time.

[https://github.com/daveray/seesaw](https://github.com/daveray/seesaw)

Everything is about the web these days. But there are some customers that do not even want to hear about using their web browser for application. You can talk them into using a Web browser for a week, or you can make a cool UI in a day. Here comes Seesaw. 

The best tutorial I have found so far is [here](https://gist.github.com/1441520)

Seesaw was born out of the frustration on how to make useful UIs in whatever language on the JVM, or for that purpose any other environment. Here you can get user input, feedback, implement a controller as you wish.

This is the simplest you can ever make with Seesaw:

@@@ ruby chapter02/src/seesaw.clj @@@

And by trying the examples on their website, here is a style you can get after a few lines:

@@@ ruby chapter02/src/seesaw_02.clj @@@

![seesaw](../images/seesaw.png)

Impressed? 

####  Xpath queries

[https://github.com/kyleburton/clj-xpath](https://github.com/kyleburton/clj-xpath)

Now you been looking on how to process those large xml files as fast as possible, here is a super way to do it, clj-xpath.

You start by adding the dependencies for lein:

	[org.clojars.kyleburton/clj-xpath "1.4.0"]

The following code retrieves all the developpers, dependencies for a given online Maven pom.xml file:

@@@ ruby chapter02/src/clj_xpath.clj @@@

	There are two forms of many of the functions, with one set ending with a star ‘*’. The non-suffixed forms will perform their operation on a single result node, raising an exception if there is not exactly one result from applying the xpath expression to the XML document. The ‘*’ suffixed forms return all of the matched nodes for further processing.

####  html parsing with delicious jsoup

While we are doing a bit of XML, let's dive a bit into Jsoup is one of the most famous library in the java world to slurp some html across the web.

[https://github.com/mfornos/clojure-soup](https://github.com/mfornos/clojure-soup)

This is a wrapper to provide awesome parsing of html files, whether local or remote. Once you have added the library, check out the following example:

The following example shows you how to retrieve some emoticons on the web:

@@@ ruby chapter02/src/clojure_soup.clj @@@

####  Templating the clojure way with Enlive

This is one of my favorite feature in Clojure. Enlive. Enlive presents a different approach from your usual templating solution. 

* Code and markup are completely separate.
* You get to use CSS like syntax to manipulate HTML.
* Template inheritance is not some fancy trick, it is just function composition.

HTML parser, and templating framework at the same time, Enlive does a super job of integrating HTML fun. (yes you read me.)

This is an example taken from the [best enlive tutorial](https://github.com/swannodette/enlive-tutorial/).

This is how you use enlive to for website scraping. Fetch the content of a URL then select the html tag you need:

@@@ ruby chapter02/enlive.clj @@@

Reversly, you declare templates in regular html files, and apply some logic in Clojure. See how:

@@@ ruby chapter02/enlive_02.clj @@@

Your great designer can do his Dreamweaver work the way he or she usually does it and you can plug in your own logic without stepping on each other's foot. Sweet. Slick.


####  Easy Spreadsheet with docjure

[https://github.com/ative/docjure](https://github.com/ative/docjure)

The best way to have fun with spreadsheet in clojure. Relies of course on the [Apache POI](http://poi.apache.org/) library but with some clojure sauce so it can actually be eaten.

This is how you write a simple spreadsheet.

@@@ ruby chapter02/src/docjure.clj @@@

For more examples, I suggest you look at the ["horrible" documentationn](http://poi.apache.org/spreadsheet/how-to.html#sxssf) :)

This horrible soup is disgusting, but it will help you help some big financial company from bankruptcy one day.

####  Spam master with postal

[https://github.com/drewr/postal](https://github.com/drewr/postal)

Now you are on your way to reform, the whole Japanese Post faster than politician will do. Here you will get the power of postal.

You may have known how to send an automated mail before, but taste this by yourself and look at the awesome way of doing this in Clojure. For this example to work, do not forget to set a valid gmail user and the password.

@@@ ruby chapter02/src/postal.clj @@@

This is of course including attachments handling and full Japanese encoding support. yey

####  How's the DNS going ? 

If you need to do some look up or reverse lookup for IPs or hostname. here are a few lines for you:

@@@ ruby src/dns.clj @@@

No need to leave the REPL, type this in anytime. This also does not depend on any external library.


####  Don't touch my clostache: more on templating 

[https://github.com/fhd/clostache](https://github.com/fhd/clostache)

We have seen Enlive earlier on, but sometimes you just need some easy file based, or even in-line based templating.
This is where clostache comes:

	[de.ubercode.clostache/clostache "1.3.1"] 

Once this is in your project, you can perform some very simple but sexy templating magic.

@@@ ruby src/clostache.clj @@@

For the story, the { and } are called moustache, make sure they do not get into your food tasting today. 


####  When you need some CSV magic to generate Apple VCard.

Now that we have mustache, we will see a longer example showing how to convert a csv files containing people addresses, to a vcard vcf file format.

[https://github.com/davidsantiago/clojure-csv](https://github.com/davidsantiago/clojure-csv)

Let's start by adding this to our project file:

	[clojure-csv "2.0.0-alpha2"]

Then see what we can do with it.

@@@ ruby chapter02/src/csv.clj @@@

This is greatly inspired by a web based address book application available on [github](https://github.com/KushalP/addressbook/blob/master/src/addressbook/format.clj).

####  When google reader is not enough, some RSS magic.

[https://github.com/yogthos/clj-rss](https://github.com/yogthos/clj-rss)

	[clj-rss "0.1.2"]

Could hardly be easier. We return a set of entries with :title, :link, :description and the RSS library does the rest for us. See in live here:

@@@ ruby chapter02/src/rss.clj @@@

This is taken from [here](http://yogthos.net/blog/17).

####  You need to grrrowl to make great notifications
[https://github.com/franks42/clj-growlnotify](https://github.com/franks42/clj-growlnotify)

Growl is a famous notification application, that you can use to let you know something happened through a visual effect on the screen.
You can get it from this place for Apple OSX:

	http://growl.info/

Or for Windows users:

	http://www.growlforwindows.com/gfw/

You can build it from source, using the information available on the following link:

	http://growl.info/documentation/developer/growl-source-install.php

And it will be free, or you can actually help the developpers and contribute a few yens for them and purchased the compiled version. Once Growl is in your system, check it is properly installed with the following code on the command line:

	growlnotify -m hello

Which should display the following on the screen:

![Growl](../images/growl2.png)

Now, let's use it from our Clojure code. Install it in your project with:

	[clj-growlnotify "0.1.1"]

Then:

@@@ ruby chapter02/src/growl.clj @@@

When you run the script above, you will get a nice message like the one below:

!["Growl"](../images/growl2.png)

####  All about JSON with Cheshire.
[https://github.com/dakrone/cheshire](https://github.com/dakrone/cheshire)

Or how to do proper json fun in the Clojure style. Cheshire is there for good. 
	
	Cheshire is fast JSON encoding, based off of clj-json and clojure-json, with additional features like Date/UUID/Set/Symbol encoding and SMILE support.

Add it to your project with:

	[cheshire "4.0.2"]

Now we can turn pure Clojure structure into JSON, and parse string in one go. Very useful for exchanging data with other system.

@@@ ruby chapter02/src/cheshire.clj @@@

Note how you can get keywords back, or do some extra mapping on dates. No more JSON problems.♫

####  Compute digest for just about everything.

[https://github.com/tebeka/clj-digest](https://github.com/tebeka/clj-digest)

Digesting ! Digest is the easiest way to have a signature of a document, and to make sure that 

What we have for you in this recipie is your everyday tool to compute digest on just about everything. It comes from clojars with:

	[digest "1.3.0"]

And voila. A few lines to compute digest from files, strings bytes etc ...

@@@ ruby chapter02/src/digests.clj @@@


####  Terminal and user interfaction, with lanterna.
[https://github.com/sjl/clojure-lanterna/](https://github.com/sjl/clojure-lanterna/)

Fun with the terminal. 

	[clojure-lanterna "0.9.2"]

This simple script:

@@@ ruby chapter02/src/lanterna.clj @@@

will spawn a terminal, display some text, and finally wait for some input key.

![Lanterna](../images/lanterna.png)

It is also easy to wait for keys, and other terminal things fun.
Have a look at the extensive [documentation](http://sjl.bitbucket.org/clojure-lanterna/screens/) to get you excited.

####  What time is it ? It's clj time

Every good language should have access to some clean way to play with dates. Here is one with _clj-time_:

[https://github.com/seancorfield/clj-time](https://github.com/seancorfield/clj-time)

This is mostly a wrapper around jodatime to do dates times and everything time related the clojure way.

Add this to your project.clj

	[clj-time "0.4.5-SNAPSHOT"]

@@@ ruby chapter02/src/time.clj @@@

Will make your life with timezones definitely better :) You will always be on the right time. 

####  SSH shortcuts

Sometimes you just wished you had that piece of data avaiable on a remote computer straigth here in your REPL.
This is where clj-ssh comes in. 

[https://github.com/hugoduncan/clj-ssh](https://github.com/hugoduncan/clj-ssh)

	[clj-ssh "0.4.3"]

A wrapper to automate ssh commands, using the default ssh agent on the local machine.
There is probably more to read if you are on windows. 

@@@ ruby chapter02/src/ssh.clj @@@

####  Master of them all HTTP client
[https://github.com/dakrone/clj-http](https://github.com/dakrone/clj-http)

clj-http is your clojure wrapper around the java [apache http client](http://hc.apache.org/). That http client has been around for quite a few years, and propose a very robust api. Now with clojure, we can even have it short.

	[clj-http "0.3.6"]

The following example show how to send a post request to ask for some image processing to be done in the cloud through the [blitline API](http://www.blitline.com/docs/quickstart).
The function applied is a blur, and the rest is just passing arond JSON parameters.

@@@ ruby chapter02/src/http.clj @@@

####  How's your serial port doing  ?

[https://github.com/samaaron/serial-port/blob/master/src/serial_port.clj](https://github.com/samaaron/serial-port/blob/master/src/serial_port.clj)

This recipie is about easy access to the USB port of your machine. This is going deep down the roots of the computer and may not work. Some other libraries like calx and penumbra that we will see later may have this embedded already. 

@@@ ruby chapter02/src/serial.clj @@@

Since this is going at the bit level and provide little bit abstraction, this is mostly for reference. 
But hey, all you need to start your own driver for your custom USB device is here.

####  No memory, but I know how to stream extra large XML files

This is based on a special XML parsing library, called xml-picker-seq that has been specially written to handle extra big xml files. 
[https://github.com/marktriggs/xml-picker-seq](https://github.com/marktriggs/xml-picker-seq)

This is what to use when you have to deal with pretty big xml files. This library supports over 1Gb heavy files, more XML than you should eat on average.
Here is how to grab it:

	[xml-picker-seq "0.0.2"]

@@@ ruby chapter02/src/large_xml.clj @@@

[https://github.com/dakrone/clojure-opennlp](https://github.com/dakrone/clojure-opennlp)
Natural Language Processing in Clojure.

This is has to be one of the most technical subject available to be used just as is in this book.  OpenNLP allows you to parse text into something meaninful so you can reuse it in your application.

	[clojure-opennlp "0.2.0"]

This would be some simple examples on how to parse text with OpenNLP:

@@@ ruby chapter02/src/opennlp.clj @@@

Note that this is only the most basic use of the OpenNLP library.
You can see a vast list of direct examples in the [README](https://github.com/dakrone/clojure-opennlp/blob/master/README.markdown) and a full section has been written on how to [train](https://github.com/dakrone/clojure-opennlp/blob/master/TRAINING.markdown) new models to use in your application.

Here is a more extensive way to tokenize a web page entirely

@@@ ruby chapter02/src/opennlp2.clj @@@


####  Full Clojure stream ahead ! Use conduit.

You may need to go and look at the following stream processing page to learn more about stream processing:
[http://www.intensivesystems.net/tutorials/stream_proc.html](http://www.intensivesystems.net/tutorials/stream_proc.html)

This is one of my favorite quick to explain but sweet to use recipie in Clojure.

At the core of conduit are ways to get things in, and then pull things out, applying different transformation along defined steps in the conduit.

	[net.intensivesystems/conduit "0.9.0"]

In idea, this is also similar to Apache Camel, where you get messages from different messaging systems (RabbitMQ, IRC etc ..) and then apply your processing. 

@@@ ruby chapter02/src/conduit.clj @@@

####  Event workflow and stream processing in Clojure

[https://github.com/ztellman/lamina](https://github.com/ztellman/lamina)
Event workflow for clojure, stream processing in Clojure.

Lamina defines the very awesome concept of queues to receive events or values and then process them as they are coming. Lamina makes it very easily to play and visualize what is happening troughout the different queues waiting for values.

Import it in your project with:

	[lamina "0.5.0-beta9"]

You would need [Graphiz](http://www.graphviz.org/Download..php) installed on your machine to display how the channels are handling data. On OSX, here is the way to do it simply with brew:

	brew install graphviz

@@@ ruby chapter02/src/lamina.clj @@@

![Lamina](../images/chapter02/lamina1.png)

@@@ ruby chapter02/src/lamina2.clj @@@

![Lamina](../images/chapter02/lamina2.png)

#### Everyday cryptography with Alice, but where is bob ? 
[Cryptography for Clojure](https://github.com/laczoka/clj-crypto)

This library is based on the efficient bounty castle library for Java. Basically, everything has been made so it's easy to generate key pairs and integrate proven security in your application.

	[laczoka/clj-crypto "1.0.2-SNAPSHOT"]

@@@ ruby chapter02/src/alice.clj @@@

#### Unit of measures calculator in Clojure

Here comes a very cute but powerful library to convert between units.

The long version on how to use this can be found here: 
[Unit of Measure Calculator](https://github.com/martintrojer/frinj) and [samples](https://github.com/martintrojer/frinj/blob/master/src/frinj/examples.clj)

But let's got through a quick example of liquids and rooms.

	[frinj "0.1.3"]

This example shows how to convert the size of your room, in the required amount of water needed to fill the room completely.

@@@ ruby chapter02/src/frinj.clj @@@

Take the time to go through the other samples, the style of writing and the taste of the example is just fabulous.

#### When you are in need of querying Json
[JsonPath](https://github.com/gga/json-path)

We have seen how to query xml over and over again, but these days, it's really more about query JSON data structure. Here comes, json-path. 
The code has not been updated for a bit of time, but it works like a charm. 

	[json-path "0.2.0"]

The following example takes us through querying regular json-like structure, and then goes along with an example after parsing with Cheshire:

@@@ ruby chapter02/src/jsonpath.clj @@@

####  Clojure on your nerves ? Use Netz for your neural network things

	Netz is a Clojure implementation of a multilayer perceptron (MLP), a type of feedforward artificial neural network. Netz provides functions for training and running MLPs. Training is accomplished via gradient descent batch Rprop or standard backpropagation.
	Netz implements Rprop as described by Riedmiller in Rprop - Description and Implementation Details. 

[Netz, Clojure Neural Network Library](https://github.com/nickewing/netz)

Here is a very short example, showing how to train and run a Neural Network:

@@@ ruby chapter02/src/netz.clj @@@

In the example, we see we train the network with some hidden values. The options you are most likely to find interesting are:

	:hidden-neurons - A vector containing the number of neurons in each hidden layer. Set to [2 2] for two hidden layers with two neurons each, or [] for no hidden layers. Setting this option is recommended. Default: One hidden layer with the same number of hidden neurons as inputs.

and to change the learning algorithm:
	
	:learning-algorithm - The algorithm to use while training. Choose either :rprop for the Rprop algorithm or :bprop for standard back propagation. Default: :rprop.

Also, each training variant has some specific options.

As a reminder modern Neural Networks can be applied to a variety of useful tasks such as:

* Function approximation, or regression analysis, including time series prediction and modeling.
* Classification, including pattern and sequence recognition, novelty detection and sequential decision making.
* Data processing, including filtering, clustering, blind signal separation and compression.

#### Shake, or every shell program is now a clojure function

This is an easy and crazy one. What about every single command of your shell could be accessible to your REPL ? 

Here comes [Shake](http://sunng.info/blog/2012/09/shake-every-program-can-be-a-clojure-function/)

Shake is very useful to keep your original script with some structure and integrate them with Clojure logic and programming.

Here's how to add it to your project.clj file:

	[shake "0.2.1"]

Then enjoy some instant gratification:

@@@ ruby src/shake.clj @@@

#### Timely, or your super easy scheduling in Clojure
[Scheduler](https://github.com/Factual/timely)

Timely takes the cron approach to easily schedule tasks for your script or for your application. It is very lightweight, has no dependency and can be integrated before the waiter comes in.

Include, version 0.0.3, but it works like a charm:

	[factual/timely "0.0.3"]

@@@ ruby chapter02/src/timely.clj @@@

#### Generate, string clean HTML in a clojure way without hiccups

This is one of the most famous Clojure library and is in used in about half of the menus and samples you will find around the web. 

	[hiccup](https://github.com/weavejester/hiccup)

	Hiccup is a library for representing HTML in Clojure. It uses vectors to represent elements, and maps to represent an element's attributes.

	[hiccup "1.0.2"]

To explain what hiccup can do could not be easier, you just create some data structure with tags and some CSS patterns to make it easy to generate HTML.

See for yourself:

@@@ ruby chapter02/src/hiccup.clj @@@

In a later chapter, we will also see how to integrate hiccup with some web framework, but for now, you can generate quite a bit with it already.

#### Ahead of time scheduler at
[Ahead of time scheduler](https://github.com/overtone/at-at)
