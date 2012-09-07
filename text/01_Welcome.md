Welcome to Clojure
-----------------

This book is about sharing my love for Clojure, why I use it, and why I will keep using it for a fair bit of time. You don't need to be a great IT geek, you just have to start using Clojure. Here we go.

### Leiningen 
[https://github.com/technomancy/leiningen](https://github.com/technomancy/leiningen)

To start using clojure, you actually do not install it. You start by installing Leiningen. Once you have it installed, you should be able to see the following:

@@@ ruby 01_leiningen.sh @@@

Now if you just want to start using some live clojure, you just go with:

@@@ ruby 02_leiningen.sh @@@

And there you are. Was simple no ? 

And here is your mother of them all hello world 

@@@ ruby 03_hello.clj @@@

Now you have to realize that all the libs available to the Java World through Maven repositories, and all the libs hosted on Clojars can be integrated in the project in no time by using the command:

*lein deps*

The libraries will come to your local project folder. Let's hack in no time.

### Clojure
[http://learn-clojure.com/clojure_tutorials.html](http://learn-clojure.com/clojure_tutorials.html)

I will not go too much into the first steps details, since this is not the aim of this book. There is a completely new online book written by [John](http://www.unexpected-vortices.com/clojure/brief-beginners-guide/index.html) that should get all the attention it deserves.

Do not forget to do a few [clojure koans](https://github.com/functional-koans/clojure-koans) to make sure the basic and the roots are ok.

### Jark
[http://icylisper.in/jark/started.html](http://icylisper.in/jark/started.html)

Next you probably going to get tired of the Java VM so very slow startup time. Here comes [nrepl](https://github.com/clojure/tools.nrepl) and [Jark](http://icylisper.in/jark/features.html)

Create a new leiningen project, and follow the jark install steps:

@@@ ruby 04_jark.clj @@@

and then start the repl with the new code from core.clj

@@@ ruby 05_jark.sh @@@

And now straight from the shell you can use and run remote clojure code

@@@ ruby 06_jark_sample.sh @@@

Now that is some sweet fast starting time, so stop buzzing around.

### Pomegrenate
[https://github.com/cemerick/pomegranate](https://github.com/cemerick/pomegranate)

This is the best way to add libraries at runtime. You still need the library itself to be available to the repl, by adding this to your __project.clj__ file:

<code>
	[com.cemerick/pomegranate "0.0.13"]
</code>

Then, here is how to import incanter in a running REPL session:

@@@ ruby 11_pomegranata.clj @@@


Libraries
---------

Now that we are ready for some hacking, here are sites, that refer interesting Clojure libraries:

* [clojure-libraries on appspot.com/](http://clojure-libraries.appspot.com/)
* [clojurewerkz](http://clojurewerkz.org/)
* [Clojure on github](http://clojure.github.com/)
* [http://programmers.stackexchange.com/questions/125107/what-are-the-essential-clojure-libraries-to-learn-beyond-the-basics-of-core](http://programmers.stackexchange.com/questions/125107/what-are-the-essential-clojure-libraries-to-learn-beyond-the-basics-of-core)


### Noir

[https://github.com/ibdknox/noir](https://github.com/ibdknox/noir) 
[http://www.webnoir.org/](http://www.webnoir.org/)

It's dark in here ! Noir is probably *the* simplest way to write a functional web application in clojure.

This is how it looks like in Clojure code

@@@ ruby 08_noir.clj @@@

and/or this is how you would have it started in a few seconds with the leiningen command we installed earlier on

@@@ ruby 09_noir.sh @@@

Now the guy from Noir has also started a very cute project named [Playground](http://app.kodowa.com/playground) where you can do live execution of your code with a nice UI.

![Alt text](../images/LightTable.png)


### Incanter

[Download](http://incanter.org/downloads/) and [Get started](https://github.com/liebke/incanter/wiki#getstarted)


From the Clojure REPL, load the Incanter libraries: 
<pre><code>user=> (use '(incanter core stats charts))</code></pre>

Try an example: sample 1,000 values from a standard-normal distribution and view a histogram: 
<pre><code>user=> (view (histogram (sample-normal 1000)))</code></pre>

![Alt text](../images/histogram.png)

### Quil

[https://github.com/quil/quil](https://github.com/quil/quil)

Quil is to [Processing](http://processing.org/) what Clojure is to Java, some fresh air.

This is how your quil-ed processing sketch now looks like:

@@@ ruby 10_quil.clj @@@

Note the decor set to false, that hides most of the uglyness of the Window borders.

And all the [examples](https://github.com/quil/quil/tree/master/examples/gen_art) you have ever dreamed from the Generative Art book have been implemented in Clojure/Quil.

### Overtone
[https://github.com/overtone/overtone/wiki/Getting-Started](https://github.com/overtone/overtone/wiki/Getting-Started)

Because you really need to live audio programming to be a real VJ these days.
This is how you install it:

@@@ ruby 12_overtone.clj @@@

Once the library is in your project, type

<code>
	(use 'overtone.live)
</code>

And now you can define an instrument
<code>
	(definst foo [] (saw 220))
</code>

And make some sound !

<code>

> (foo) ; Call the function returned by our synth
4      ; returns a synth ID number
> (kill 4) ; kill the synth with ID 4
> (kill foo) ; or kill all instances of synth foo

</code>

### Marginalia 
[https://github.com/fogus/marginalia](https://github.com/fogus/marginalia)

Marginalia is your best literate programming tool for clojure.  

Install it as a dependency in your project.clj file:

<code>
	:dev-dependencies [lein-marginalia "0.7.1"]
</code>

Then, just use it:
<code>
	lein marg
</code>

And dependending on the amount of comments you wrote in the code, you will get something similar to this:

![Alt text](../images/marginalia.png)

### Lacij 
[https://github.com/pallix/lacij](https://github.com/pallix/lacij)

A library that can quickly create SVG diagram with automatic layout, like this one:

![SVG](../images/radial.png)

<code>
(use 'lacij.graph.svg.graph)
</code>

Then, a graph can be drawn like this:

@@@ ruby 13_lacij.clj @@@

The advantage is that you can add and remove nodes dynamically, thus giving a dynamic view about live typologies.

### Seesaw
[https://github.com/daveray/seesaw](https://github.com/daveray/seesaw)

Make cool UIs in no time.

The best tutorial I have found so far is [here](https://gist.github.com/1441520)

And by trying the example, here is a style you can get after a few lines:

![seesaw](../images/seesaw.png)

### Cloduino
[https://github.com/nakkaya/clodiuno](https://github.com/nakkaya/clodiuno)



In the Clouds
--------

### Pallet

[http://palletops.com/](http://palletops.com/)

Pallet is the mother of them all of cloud infrastructure tool. See the [list of providers](http://www.jclouds.org/documentation/reference/supported-providers/) it supports! 
They are actually doing this through [jclouds](http://www.jclouds.org/)


### Heroku
[Debugging clojure on Heroku](https://devcenter.heroku.com/articles/debugging-clojure)

### VMFest
[https://github.com/tbatchelli/vmfest](https://github.com/tbatchelli/vmfest)

Easy VirtualBox wrapper for easy cloud management.

@@@ ruby 14_vmfest.clj @@@

Make sure you also look at the [playground](https://github.com/pallet/vmfest-playground) and have a look at the [tutorial](https://github.com/pallet/vmfest-playground/blob/master/src/play.clj)


	