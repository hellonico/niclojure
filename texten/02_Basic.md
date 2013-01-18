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

This would be how you set up a server that reponds to web requests:

@@@ ruby chapter02/src/http_kit_01.clj @@@

The server will be started, and will delay answer to the client. No dependency so kind of very lightweight and wicked.
It also incorporate a kinda cool http client, again with no dependencies. Give a try.

@@@ ruby chapter02/src/http_kit_03.clj @@@

And it even includes a way to handle web sockets. Start the server with:

@@@ ruby chapter02/src/http_kit_02.clj @@@

And then head online to a generic websocket testing site:

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

Marginalia is your best literate programming tool for clojure. Which means, if you write the necessary comments within your code, marginalia will generate the best documentation for your project. Then you just have to publish it online. 

Install it as a dependency in your *~/.lein/profiles.clj* file:


	{:user {:plugins [
                  [lein-marginalia "0.7.1"] ; <- add this
                  [lein-pprint "1.1.1"]]}}

Then, just use it:

	lein marg

And dependending on the amount of comments you wrote in the code, you will get something similar to this:

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

Everything is about the web these days. But there are some customers that do not even want to hear about using their web browser for application. You can talk them into using a Webbrowser for a week, or you can make a cool UI in a day. Here comes Seesaw. 

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

	There are two forms of many of the functions, with one set ending with a star ‘*’. The non-suffixed forms will perform their operation on a single result node, raising an exception if there isn’t exactly one result from applying the xpath expression to the XML document. The ‘*’ suffixed forms return all of the matched nodes for further processing.

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
* Template inheritance isn’t some fancy trick, it’s just function composition.

HTML parser, and Templating framework at the same time, Enlive does a super job of integrating HTML fun. (yes you read me.)

This is an example taken from the [best enlive tutorial](https://github.com/swannodette/enlive-tutorial/).

This is how you use enlive to for website scraping. Fetch the content of a URL then select the html tag you need:

@@@ ruby chapter02/enlive.clj @@@

Reversly, you declare templates in regular html files, and apply some logic in Clojure. See how:

@@@ ruby chapter02/enlive_02.clj @@@

Your great designer can do his Dreamweaver work the way he/she usually does it and you can plug in your own logic without stepping on each other's foot. Sweet. Slick.


