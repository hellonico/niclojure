## Messaging

Because your application will probably not be standalone, but will be talking to a horde of different systems, we have created the Messaging chapter. I had the experience, though, to connect a clojure system to an impossible to configure banking system. The messages were so big I was only hoping the messaging queues will not blow up. What Clojure brought to me is the incredible flexibility to just say, "Yes, I can handle that message." or "Yes I can handle that timing and react in realtime" and have a pretty good certitude that if a problem was coming in the integration, it was not coming from the people at the dining table that we are. 

Here, we would like to show you a bit of this, and give you some insurance about easiness, readability and performances. 

This section will go in a bit details how to connect to different middleware like RabbitMQ, MemCache, Redis, JMS... and a few others, so you can get started quickly to core level of your work.

### Clamq, or your clojure toolkit for JMS, AMQP and RabbitMQ

Here comes the most generic [JMS, AMQP/RabbitMQ](https://github.com/sbtourist/clamq) middle ware in the Clojure land. Clamq basically connects you to the most famous queuing system of these days, with the same client code, so you won't even face problems if you need to switch from one to the other, or actually if you have the chance to change from one to the other.

For a short example, we will use [ActiveMQ](http://activemq.apache.org/download.html), not only for efficiency but because it is probably the fatest way to get started.
Let's start by downloading the package from:
	
	http://activemq.apache.org/download.html

Or your favorite package manager.
Once expanded, we can go to the *bin* folder where all the binaries are installed. We will then start the system with the simple command:

	./activemq start

Once the system is started, you can go to your browser, and access the admin [web console](http://activemq.apache.org/web-console.html):

	http://localhost:8161/admin/

Where the login is:

	admin / admin

If all goes well, you should see the following screen:

![ActiveMQ](../images/chap05/activemq.png)

Now we are all ready for some quick examples. 

Our code presents how to write a simple producer/consumer test using a remote messaging queue, that will be created in ActiveMQ:

	@@@ ruby chapter05/src/clamq.clj @@@

If we look, at the admin web console, we can see the queue is empty before the code is run:

![Queue](../images/chap05/queue1.png)

and contains the message we sent using our small snippet:

![Queue](../images/chap05/queue2.png)

Our next example will be showing how to use a Topic. Remember a topic is the way to broadcast the same message to multiple subscribers. 

	@@@ ruby chapter05/src/clamq_topic.clj @@@

And, we pretty much notice that the code was very similar that the one for queues with the extra addition of:

	{:pubSub true}

For more of the ActiveMQ fun, you can have a look at the [ActiveMQ test suite](https://github.com/sbtourist/clamq/blob/master/clamq-activemq/test/clamq/test/activemq_test.clj) and its related [jms suite](https://github.com/sbtourist/clamq/blob/master/clamq-jms/src/clamq/test/base_jms_test.clj).

Suiiiiite.

### Easy JBoss Rules Integration
[JBoss Rules Integration](http://www.gettingcirrius.com/2010/12/using-jboss-rules-drools-in-clojure.html)

### I have my horse, ride your Camel
[Apache Camel from Clojure](https://github.com/denlab/apache-camel-clojure)

#### Camel Routes with Clojure
[Camel Routes with Clojure](https://github.com/hmanish/clj-camel)

### Sometimes you need some Quartz, to schedule your Clojure
[Clojure with Quartz](http://clojurequartz.info/articles/getting_started.html)

### Redis in your clojure
[Redis](https://github.com/wallrat/labs-redis-clojure)

### Distribute your application state with zookeeper and avout
[Avout](https://github.com/AlexBaranosky/avout)

### Every one talks about Hadoop, so let's talk to it with Clojure
[Hadoop](HBase: http://twitch.nervestaple.com/2012/01/12/clojure-hbase/)

### Hadoop queries from Clojure with Cascalog
[https://github.com/nathanmarz/cascalog/wiki](https://github.com/nathanmarz/cascalog/wiki)
Hadoop Query from Clojure

### Basic Apple Push Notifications
[Apple Push Notifications](https://github.com/HEROLABS/herolabs-apns)

### Where would you be without a spyglass (and a proper memcache client)
[Memcache](http://clojurememcached.info/articles/getting_started.html)

### Calling SIP, calling clojure
[SIP](https://github.com/Ruiyun/cljain)

### Apache Cassandra, at your clojure tips
[Cassaforte](https://github.com/clojurewerkz/cassaforte)

### The best Actor model concurrency in the scala world coming to Clojure
[Akka wrapper for Clojure](https://github.com/gaverhae/okku)

### Simple Apache Thrift, powering Facebook, in Clojure
[Thrift from Clojure](http://thecomputersarewinning.com/post/simple-thrift-in-clojure/)

### Storm is a distributed realtime computation system, accessible from Clojure
[https://github.com/nathanmarz/storm/wiki/Clojure-DSL](https://github.com/nathanmarz/storm/wiki/Clojure-DSL)

