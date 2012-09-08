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

### clj-xpath
[https://github.com/kyleburton/clj-xpath](https://github.com/kyleburton/clj-xpath)

Now you been looking on how to process those xml files as fast as possible, here is a super way to do it, clj-xpath.

@@@ ruby 17_clj_xpath.clj @@@

### clojure-soup
[https://github.com/mfornos/clojure-soup](https://github.com/mfornos/clojure-soup)

This is a wrapper to provide awesome parsing of html files, whether local or remote. Once you have added the library, check out the following example:

@@@ ruby 18_clojure_soup.clj @@@

### Enlive
[https://github.com/cgrand/enlive](https://github.com/cgrand/enlive)

HTML parser, and Templating framework at the same time, Enlive does a super job of making HTML fun. (yes you read me.)

This is an example taken from a [slick tutorial](https://github.com/swannodette/enlive-tutorial/) by David Nolen.

@@@ ruby 16_enlive.clj @@@

You declare templates in a regular html files, thus your designer can do his Dreamweaver work the way he/she usually does it. 
Then as a developper you just come and stick content at the location that has been decided. Slick uh ?

### Vaadin
[http://dev.vaadin.com/wiki/Articles/ClojureScripting](http://dev.vaadin.com/wiki/Articles/ClojureScripting)

This is not so much a library than a way to develop slick web application using Vaadin, but without the java boiler plate code. 

### clj-opennlp
[https://github.com/dakrone/clojure-opennlp](https://github.com/dakrone/clojure-opennlp)
Natural Language Processing in Clojure.

[Tokenizer](https://github.com/eandrejko/clj-tokenizer) for text analysis.

### clj-http
[https://github.com/dakrone/clj-http](https://github.com/dakrone/clj-http)

### Cascalog
[https://github.com/nathanmarz/cascalog/wiki](https://github.com/nathanmarz/cascalog/wiki)

### Aleph
[https://github.com/ztellman/aleph](https://github.com/ztellman/aleph)

### Lamina
[https://github.com/ztellman/lamina](https://github.com/ztellman/lamina)
Event workflow for clojure

### Conduit
[http://www.intensivesystems.net/tutorials/stream_proc.html](http://www.intensivesystems.net/tutorials/stream_proc.html)
Stream processing in Clojure
