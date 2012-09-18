Libraries
---------

### colorize 
[https://github.com/ibdknox/colorize](https://github.com/ibdknox/colorize)

<code>
[colorize "0.1.0"]
</code>

Colorize is a super simple ansi colorizing library. Try it for yourself:

@@@ ruby 54_colorize.clj @@@


### http-kit
[https://github.com/shenfeng/http-kit](https://github.com/shenfeng/http-kit)

In the same time, you can turn yourself asynchrnous with http-kit. 

<code>
[me.shenfeng/http-kit "1.2.0"]
</code>

@@@ ruby 36_http_kit.clj @@@

The server will be started, and will delay answer to the client. No dependency so kind of very lightweight and wicked.
It also incorporate a kinda cool http client, again with no dependencies. Give a try.

![Async](../images/async.png)

### Incanter

[Download](http://incanter.org/downloads/) and [Get started](https://github.com/liebke/incanter/wiki#getstarted)

The download above includes a bunch of stuff you may not need. A faster way to get started is to go the usual route with Leiningen
<code>
[incanter "1.3.0"]
</code>

Then you can play around:

@@@ ruby 38_incanter.clj @@@

The image below has been generated from the script above ! 

![Alt text](../images/histogram.png)

Whats your next diagram ? 

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

### Docjure
[https://github.com/ative/docjure](https://github.com/ative/docjure)

The best way to have fun with spreadsheet in clojure. Relies on the [Apache POI](http://poi.apache.org/) library but with some clojure sauce so it can actually be eaten.

This is how you write a simple spreadsheet.

@@@ ruby 21_docjure.clj @@@

For more examples, I suggest you look at the ["horrible" documentation](http://poi.apache.org/spreadsheet/how-to.html#sxssf) :)

### Postal
[https://github.com/drewr/postal](https://github.com/drewr/postal)

I am pretty sure you knew how to send a mail before, but look at the awesome way of doing this in Clojure.

@@@ ruby 22_postal.clj @@@

This is including attachment and Japanese encoding. yey

### Lobos
[http://budu.github.com/lobos/](http://budu.github.com/lobos/)

Here is comes Lobos, or how to manage your database directly from a Clojure REPL.

@@@ ruby 20_lobos.clj @@@

You define your database connection, and just go and create, drop delete, what every you need.
This is also very useful for testing.

### Korma
[http://sqlkorma.com/docs](http://sqlkorma.com/docs)

Korma makes actually enjoyable to write SQL queries.

<code>
[korma "0.3.0-beta7"]
</code>

Have a look at the following code:

@@@ ruby 19_korma.clj @@@

Portable, compatible queries against a SQL database, with the feel of a no-sql one. When you have to insert or recover data, what else would you use ? 

### clj-dns
[https://github.com/brweber2/clj-dns](https://github.com/brweber2/clj-dns)

> DNS Querying in Clojure. 

<code>
[com.brweber2/clj-dns "0.0.2"]
</code>

How easy it is to do lookup and reverse lookup. Need anything else ? 

@@@ ruby 29_dns.clj @@@


### Clostache
[https://github.com/fhd/clostache](https://github.com/fhd/clostache)

<code>
[de.ubercode.clostache/clostache "1.3.0"]
</code>

Some very simple templating magic.

@@@ ruby 33_clostache.clj @@@

### clojure-csv
[https://github.com/davidsantiago/clojure-csv](https://github.com/davidsantiago/clojure-csv)

<code>
[clojure-csv/clojure-csv "2.0.0-alpha1"]
</code>

Now that we have mustache, I actually have added a longer example showing how to convert a csv files containing people addresses, to a vcard vcf file format.
Took a few minutes to write the code, not a single exception, and all using multiple cores to convert the map.

Sweeet.

@@@ ruby 32_csv.clj @@@

This is greatly inspired by a web based address book application available on [github](https://github.com/KushalP/addressbook/blob/master/src/addressbook/format.clj).

### clj-rss
[https://github.com/yogthos/clj-rss](https://github.com/yogthos/clj-rss)

<code>
[clj-rss "0.1.2"]
</code>

@@@ ruby 35_rs.clj @@@

This is taken from [here](http://yogthos.net/blog/17).

### clj-growlnotify
[https://github.com/franks42/clj-growlnotify](https://github.com/franks42/clj-growlnotify)

<code>
[clj-growlnotify "0.1.1"]
</code>

@@@ ruby 34_growl.clj @@@

When you run the script above, if you have the growl notification system ([osx](http://growl.info/), [windows](http://www.growlforwindows.com/gfw/)) installed on your machine, you will get a nice message like the one below:

!["Growl"](../images/Growl.png)

### cheshire
[https://github.com/dakrone/cheshire](https://github.com/dakrone/cheshire)

Or how to do proper json fun in the Clojure style. Cheshire is there for good.

<code>
[cheshire "4.0.2"]
</code>

@@@ ruby 37_json.clj @@@

Note how you can get keywords back, or do some extra mapping on dates. No more JSON problems.♫

### clj-digest
[https://github.com/tebeka/clj-digest](https://github.com/tebeka/clj-digest)

This is your everyday tool to compute digest on just about everything. It comes from clojars:
<code>
[digest "1.3.0"]
</code>

And voila. A few lines to compute digest from files, strings bytes etc ...

### clojure-lanterna
[https://github.com/sjl/clojure-lanterna/](https://github.com/sjl/clojure-lanterna/)

Fun with the terminal. 
<code>
[clojure-lanterna "0.9.2"]
</code>

This simple script:
@@@ ruby 40_lanterna.clj @@@

will spawn a terminal, and display some text:

![Lanterna](../images/lanterna.png)

It is also easy to wait for keys, and other terminal things fun.
Have a look at the extensive [documentation](http://sjl.bitbucket.org/clojure-lanterna/screens/) to get you excited.

### clj-time
[https://github.com/seancorfield/clj-time](https://github.com/seancorfield/clj-time)

Wrapper around jodatime to do dates times and everything time related the clojure way.

Add this to your project.clj
<code>
[clj-time "0.4.3"]
</code>

@@@ ruby 41_time.clj @@@

Will make your life with timezones definitely better :)

### clj-ssh
[https://github.com/hugoduncan/clj-ssh](https://github.com/hugoduncan/clj-ssh)

<code>
[clj-ssh "0.4.0"]
</code>

A wrapper to automate ssh commands, using the default ssh agent on the local machine.
There is probably more to read if you are on windows. 

@@@ ruby 45_ssh.clj @@@

### clj-opennlp
[https://github.com/dakrone/clojure-opennlp](https://github.com/dakrone/clojure-opennlp)
Natural Language Processing in Clojure.

[Tokenizer](https://github.com/eandrejko/clj-tokenizer) for text analysis.

### clj-http
[https://github.com/dakrone/clj-http](https://github.com/dakrone/clj-http)

clj-http is your usual wrapper around the [apache http client](http://hc.apache.org/). That http client has been around for quite a few years, and propose a very robust api. Now with clojure, we can even have it short.

<code>
[clj-http "0.1.3"]
</code>

The following example show how to send a post request to ask for some image processing to be done in the cloud through the [bitline API](http://www.blitline.com/docs/quickstart).

@@@ ruby 59_bitline.clj @@@


### Cascalog
[https://github.com/nathanmarz/cascalog/wiki](https://github.com/nathanmarz/cascalog/wiki)
Hadoop Query from Clojure

### Lamina
[https://github.com/ztellman/lamina](https://github.com/ztellman/lamina)
Event workflow for clojure

### Conduit
[http://www.intensivesystems.net/tutorials/stream_proc.html](http://www.intensivesystems.net/tutorials/stream_proc.html)
Stream processing in Clojure

### Clojure-tco
[https://github.com/cjfrisz/clojure-tco](https://github.com/cjfrisz/clojure-tco)
 (Tail Call Optimization)

To be checked because it did not work so well :)

### serial
[https://github.com/samaaron/serial-port/blob/master/src/serial_port.clj](https://github.com/samaaron/serial-port/blob/master/src/serial_port.clj)

<code>
[serial-port "1.1.0"]	
</code>

Very useful to access the USB port of your machine when the JVM actually has some limitation. This is going deep down the roots of the computer and may not work. Some other libraries like calx and penumbra may have this embedded already. So this is mostly for reference. Now is the time to start your own driver :)

@@@ ruby 51_serial.clj @@@

### xml-picker-seq
[https://github.com/marktriggs/xml-picker-seq](https://github.com/marktriggs/xml-picker-seq)

What to do when you have to deal with pretty big xml files. This library supports up to 1Gb heavy files, more than you should need on average.

<code>
[xml-picker-seq "0.0.2"]	
</code>

@@@ ruby 52_xml_seq.clj @@@