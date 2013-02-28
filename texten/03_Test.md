
### I will test some of that

Now on our way to do proper engineering and test everything 

#### Remember the basics

A long time ago we spoke about how to use lein and the REPL to speed up a lot of your programming workflow.
Now I am a fervent supporter of TDD, Test Driven Development, meaning writing what needs to be tested first.

For example, say we want to test a function say-hello. Let's write what is needed to test it:

@@@ ruby chapter03/src/test.clj @@@

You will notice, we can run the tests by calling them directly:

	(a-test)

Or, running all the them with:

	(run-tests)

At this stage, the result shows like this:

	user=> (run-tests)

	Testing user

	Ran 1 tests containing 1 assertions.
	0 failures, 0 errors.
	{:type :summary, :pass 1, :test 1, :error 0, :fail 0}

So we need to fix the test.

	(defn say-hello[name]
	   (str "hello " name))

That is all there is. The whole idea of having test taking time to write is busted.

Now the rest of the clojure.test details are [online](http://richhickey.github.com/clojure/clojure.test-api.html), but there is not much more you need to know to be efficient.

Also, you will notice there is not much more to run the tests if they are found in a test folder. So suppose we have the following:

@@@ ruby chapter03/test/chapter03/core_test.clj @@@

Then running
	
	lein test

Will run the test for you. At this stage, this will return something like this

	lein test chapter03.core-test

	Testing chapter03.core-test

	FAIL in (a-test) (core_test.clj:6)
	FIXME, I fail.
	expected: (= 0 1)
	  actual: (not (= 0 1))

	Ran 1 tests containing 1 assertions.
	1 failures, 0 errors.
	Tests failed.

####  You are not a lazy midjet, so you use midje to test 

[Midje](https://github.com/marick/Midje) is a fantastic move in the world of Test Driven Development. Midge not only makes testing your code fun, it gives you the right mindset to do experiment and testing in your own project. 

To install Midge, you add the depedency to your ~/.lein/profile.clj file with:

	[lein-midje "2.0.4"]

Now, we are going to write a few simple tests with the midje syntax like these:

@@@ ruby chapter03/test/chapter03/midje.clj @@@

And here the magic happens, let's start a lein process that is going to test this for us:

	 lein midje --lazytest

The first run will output test that were executed as well as failures:

	[Niko@air][13:46][~/projects/mascarpone/chapter03/] % lein midje --lazytest
	#<ScheduledThreadPoolExecutor java.util.concurrent.ScheduledThreadPoolExecutor@4ede45aa[Running, pool size = 1, active threads = 1, queued tasks = 0, completed tasks = 0]>

	======================================================================
	At  #inst "2013-02-28T04:47:09.477-00:00"
	Reloading chapter03.core-test, chapter03.midje, logic1

	FAIL at (midje.clj:25)
	Actual result did not agree with the checking function.
	        Actual result: [4]
	    Checking function: (contains 5)
	    The checker said this about the reason:
	        Best match found: []

After each change in our source files, the tests will be run again without the need to type any command to rerun the tests.    

Final note, you could also add the midje dependency:
	
	[midje "1.4.0"]   

And execute everything from your usual REPL session. We are leaving this out as an exercice that you will finish while we are testing some tasty wine.

####  Web sites easy testing with selenium
[https://github.com/semperos/clj-webdriver/wiki](https://github.com/semperos/clj-webdriver/wiki)

"This library leverages the Selenium-WebDriver Java library to drive real GUI browsers like Firefox, Chrome, Safari and Internet Explorer, providing both a thin wrapper over the WebDriver API as well as higher-level Clojure functions to make interacting with the browser easier."

<code>
[clj-webdriver "0.6.0-alpha11"]
</code>

When in need for some integrated testing using clojure, especially on website that do not have a public API.  Here is an example showing I forgot my github password. :)

@@@ ruby 60_taxi.clj @@@

There is a very nice [blog post](http://corfield.org/blog/post.cfm/automated-browser-based-testing-with-clojure) entry that shows how to use the API in more details. 

#### Behavior Driven Development (BDD) with cucumber in Clojure
[BDD with Cucumber and Lein](https://github.com/nilswloka/lein-cucumber) & [Cucumber](http://www.matthewtodd.info/?p=112)

#### Benchmark your clojure code, with criterium
[Benchmark](https://github.com/neatonk/criterium)

#### Taxi !!! Test my web application quick !
[Taxi, testing with selenium](https://github.com/semperos/clj-webdriver)

#### Load testing and performance with perforate
[Load testing and performance with perforate](https://github.com/davidsantiago/perforate)

#### VCR or your HTTP Playback 
[VCR](https://github.com/fredericksgary/vcr-clj)

#### Ring App Testing
[Ring App Testing](https://github.com/xeqi/kerodon)

#### Travis: Open Source hosted continuous integration service for Clojure
[Travis](http://about.travis-ci.org/docs/user/languages/clojure/)

#### BDD and Rspec the clojure way
[specj](https://github.com/slagyr/speclj)