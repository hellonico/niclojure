
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

[clj-webdriver](https://github.com/semperos/clj-webdriver/wiki) is a "library leverages the Selenium-WebDriver Java library to drive real GUI browsers like Firefox, Chrome, Safari and Internet Explorer, providing both a thin wrapper over the WebDriver API as well as higher-level Clojure functions to make interacting with the browser easier."

	[clj-webdriver "0.6.0-beta2"]

When in need for some integrated testing using clojure, especially on website that do not have a public API.  Here is an example showing I forgot my github password. :)

@@@ ruby chapter03/src/taxi.clj @@@

There is a very nice [blog post](http://corfield.org/blog/post.cfm/automated-browser-based-testing-with-clojure) entry that shows how to use the API in more details. 

One more usage that I frequently use it for is to take screenshots of websites! This is detailed in a second example:

@@@ ruby chapter03/src/taxi2.clj @@@

Now you can go and use timers, or take screenshots depending on incoming emails or rest requests. Up to your imagination.

#### Behavior Driven Development (BDD) with cucumber in Clojure
[BDD with Cucumber and Lein](https://github.com/nilswloka/lein-cucumber) & [Cucumber](http://www.matthewtodd.info/?p=112)

I hope you have heard of [Cucumber](http://cukes.info) before. 
It simply makes your testing life easier to read, with some great behavioral testing convention.

I had an awesome time a few months ago putting together a test suite to validate an dodgy API for a customer using something similar to what we are going to see here.

So first we start by adding the lein plugin for cucumber in profiles.clj:

	[lein-cucumber "1.0.2"]

We can specifiy where to find cucumber features with the following key in our project.clj:

	:cucumber-feature-paths ["test/features/"]

Then on to a bit of code that we want to test. This will be in:

@@@ ruby chapter03/src/clojure_cukes/core.clj @@@

Now on we go forward and straight and start peeling vegetables.
In Cucumber we start by writing text that looks like it could be read by normal people, almost.

@@@ ruby chapter03/test/features/cukes.feature @@@

Then we write some support code so the actual "means" something to our vegetables. This file will be in a folder named _step_definitions_ in our test folder.

@@@ ruby chapter03/test/step_definitions/cuke_steps.clj @@@

Cucumber steps are defined by regular expression and string matching so once you have your regexp written properly, you can write more feature and test extreme cases.

With all this setup, we can run our tests with:

	lein cucumber 

And then see together that the results are ...

	Running cucumber...
	Looking for features in:  [/Users/Niko/projects/mascarpone/chapter03/test/features]
	Looking for glue in:  [/Users/Niko/projects/mascarpone/chapter03/test/features/step_definitions]
	.......F

	java.lang.AssertionError: Assert failed: (= (name (mood)) mood-name)

Almost there ! Now for you to take over, and get the test suite to pass properly. Only one word to change so sure you can do it ... now. 

#### Benchmark your clojure code, with criterium
[Benchmark](https://github.com/neatonk/criterium)

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