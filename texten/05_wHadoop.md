### Convenient HBase from Clojure

#### Getting Ready 

Before we delve right into some serious Hadoop-ness, let's have a quick look at how to access hbase from Clojure.

This quick hbase recipe will show us how to define schemas, store some values, and do some minor processing with the received data.

Right now at one of the project we are working on we are processing a large amount of fine grained events from concurrent users, and processing this vast amount of data to different time scale viewing.

You can install hbase with your favorite package manager, or [download it](http://www.apache.org/dyn/closer.cgi/hbase/)

On OSX, it goes simply with:

    brew install hbase

Once on your machine, let's kick in a master node with the following command:

    hbase master start

To make sure, all is working fine so far, we can check the web UI at the following URL:

    http://localhost:60010/master-status

![hbase UI](../images/chap05/hbase.png)

#### Clojuring hbase

We will use the [clojure-hbase](https://github.com/davidsantiago/clojure-hbase) to do manipulation of schemas and store some values.

To add it to our project, let's add the following dependency:

    [org.clojure/clojure "1.4.0"]
    [clojure-hbase "0.92.1"]

Note that it seems the project does not support Clojure 1.5.1 yet. 

Once we have this, let's define a very basic schema:

@@@ ruby chapter05_hadoop/hbase/src/hbase/setup.clj @@@

One table and one family only, but that will be enough for our easy playing around.
Note also, that the table needs to be disabled when adding families to the table schema.

Next on our list, is to do some basic CRUD operations:

@@@ ruby chapter05_hadoop/hbase/src/hbase/core.clj @@@

Since, hbase talks only byte arrays, we are introducing methods

* vectorize
* keywordize

to see the content of result values slightly more easily.

The next example, shows how to see maps results directly:

@@@ ruby chapter05_hadoop/hbase/src/hbase/core2.clj @@@

To retrieve a set of values from hbase, we use a scanner. The arguments for your filters are in this list:

    (def ^{:private true} scan-argnums
      {:column       1    ;; :column [:family-name :qualifier]
       :columns      1    ;; :columns [:family-name [:qual1 :qual2...]...]
       :family       1    ;; :family :family-name
       :families     1    ;; :families [:family1 :family2 ...]
       :filter       1    ;; :filter <a filter you've made>
       :all-versions 0    ;; :all-versions
       :max-versions 1    ;; :max-versions <int>
       :time-range   1    ;; :time-range [start end]
       :time-stamp   1    ;; :time-stamp time
       :start-row    1    ;; :start-row row
       :stop-row     1    ;; :stop-row row
       :use-existing 1})  ;; :use-existing <some Get you've made>

And now to put it in brief action, see how to use the scanner to retrieve rows:

@@@ ruby chapter05_hadoop/hbase/src/hbase/scan.clj @@@

That would be it for some crud operations in hbase. Using Clojure is a great way to actually go inside rapidly and see the data that is stored directly in hbase. 
You will get use to the tooling pretty rapidly. 

Now on the road for some querying in our next section.

### Hadoop queries from Clojure with Cascalog

Querying with Cascalog should made your adventure in Hadoop land quite a ride. To be honest, the notation scared me a little bit at first, just like a taste of some very deep and old wine. Not sure whether this is vinegar or pure greatness at first.
It took a bit of time to run the proper query and to write the proper code, but once you get the balance, the robe is a pleasure to look at.

Your actual best bet to go through Cascalog is the excellent [Hadopp with Cascalog tutorial for the Impatient](https://github.com/Quantisan/Impatient/wiki).

[Cascalog](https://github.com/nathanmarz/cascalog) is indeed *Data processing on Hadoop* without the hassle. 

Our first part in this small section will go over the early steps of the above Impatient steps, creating jobs and running them on Hbase. The second part will go back to our previous little example, and show how to run queries directly on hbase.
Third part will open suggestions as to what to do with all this.

#### Impatient, you don't need to be

As said above, we will only go through the first few steps of the Cascalog Tutorial. Cascalog will perform the map reduce operations for you in a shorter and more efficient way, far reducing the complexity of creating jobs for hadoop.

#### First simple hadoop and cascalog job

Our goal is to run a task that will read in and process a text file.

Once you get a grasp at it, and even before, it's great to have the cascalog api at hand. 

It is located at:
  
    http://nathanmarz.github.io/cascalog/cascalog.api.html

It will help going through the single sample below:  

    (?<- 
      (hfs-delimited out)
      [?doc ?line]
         ((hfs-delimited in :skip-header? true) ?doc ?line)))

* ?<- is a Clojure macro that defines a query and run it at the same time. Its syntax is: (?<- out-tap out-vars & predicates)

A tap is somewhat like a pipeline that feeds information little by little.

* hfs-delimited, is a tap. A tap can run for both in and out, and in this example we will use it twice, once to get data in, and to get data out. In this small sample, the input space separated file has has two fields per line, once is the ?doc, the other one will be the ?line, as seen in:

@@@ ruby chapter05_hadoop/part1/data/rain.txt @@@

* Next step is to pipe the output of the predicates: [?doc ?line]
* And put it as comma separated to out, with (hfs-delimited out)

Now is the full source code to prepare to run it:

@@@ ruby chapter05_hadoop/part1/src/impatient/core.clj @@@

We can test our job using three different methods. 

One is to simply run it in the REPL with:

    (impatient.core/-main "data/rain.txt" "output/rain")

This will output a file in output/rain with the following content:

    doc01   A rain shadow is a dry area on the lee back side of a mountainous area.
    doc02   This sinking, dry air produces a rain shadow, or area in the lee of a mountain with less rain and cloudcover.
    ...

We can also test it through the run command of Leiningen with:

    lein run -m impatient.core data/rain.txt output/rain

Again, we do not need to *-m* directive if the main namespace is defined in project.clj with:

    :main impatient.core

Last method, is to manually run the job on Hadoop. This comes in two steps, one to create the jar to deploy:

    lein uberjar 

And the second step to run the job with the hadoop command:

    hadoop jar target/impatient.jar data/rain.txt output/rain

The output should and will be the same each time, and located in the output/rain folder.

Enjoy a nice taste of grape before going through the next sample, we just learned how to run a hadopp job in a few little steps.

#### Second simple hadoop and cascalog job

This second example takes the hfs-sample one step further with selecting fields and type casting objects when using hfs-delimited.

The source code is at:

@@@ ruby chapter05_hadoop/part1/src/impatient/stocks.clj @@@

We have just enhanced our hfs-delimited tap with some optional parameters that you can use for real life processing, here stock prices.

The command to run the stocks analysis is about the same as before:

    lein run -m impatient.stocks data/stocks.txt output/stocks

#### Back to our first example, tap data from and into hbase

Now we ca now how to tap into files, let's go back to our earlier example with hbase.

Our first example builds on the earlier hbase data insertion deletion code we went through.

We will first add the hbase tap with:

    [hbase.cascalog "0.2-SNAPSHOT"]

And now we are able to go through some data stored in hbase. 

@@@ ruby chapter05_hadoop/hbase_query/src/query.clj @@@

A few points:
* (stdout) : This time we tap the results on the standard output.
* we use hbase-tap to retrieve (or store) data in hbase
* We get the key and the content of the row
* We need to slurp the data, because it comes back as a byte array.

The next example will simply add one more operation and variable, on each line handled:

@@@ ruby chapter05_hadoop/hbase_query/src/query1.clj @@@

#### One more step, transform data 

The last in this series will make use of *defmapcatop*. defmapcatop defines an operation that takes a single field "sentence" as input and outputs 0 or more tuples as output. 
Another operation is defaggregateop that defines an aggregator. 

Our second example to be taken from the impatient tutorial is like this:

@@@ ruby chapter05_hadoop/part2/src/impatient/core.clj @@@

As we have seen before, regular Clojure functions can be used as operations. A Clojure function is treated as a filter when not given any output variables. When given output variables, it is a map operation.

This time, each line is simply turned into a list of words. This is assigned to something like a pipe named ?word.

Once this is done, there will be multiple lines for each ?word, and we just have to count the total.

We can run this with a syntax we have used before:

    lein run data/rain.txt output/rain

And then output will look like:

    ...
    rain    5
    ranges  1
    shadow  4
    side    2
    sinking 1
    such    1
    that    1
    the     5
    with    1

That's it. This is how easy it is to do some processing of data with hadoop.

#### Hbase and Cascalog with Clojure Summary

Through the course of this small section we have seen:

* how to set up hbase, and do some CRUD operations on it
* how to defined an hadoop map reduce job and run it
* how to tap data in and out different sources
* how to do data processing by counting all the different words from a specific file.

There is obviously more to it that meet the eyes, so we recommend:

* Finish the Impatient tutorial
* [Read the original introduction](http://nathanmarz.com/blog/introducing-cascalog-a-clojure-based-query-language-for-hado.html)
* See some more [examples](http://ianrumford.github.io/blog/2012/09/29/using-cascalog-for-extract-transform-and-load/)
* Finally head to the [Cascalog wiki](https://github.com/nathanmarz/cascalog/wiki) for more sources of information