## To become a famous Engineer, leave out deployment to Clojure. 

###### グラニテ（Granité）: 料理と料理の間の口直し

### Uber jar: Back to basics

Remember Lein already ? The reason you want to use it as much as possible is that it makes your life easier.
Out of the box, Leiningen can create a wonderful things named *uberjar*, or, in connaissor terms, *standalone jar*.

What that means is that all the dependencies and code of your project are packaged in a single jar file, the common way of packaging source code for the java platform. 

To achieve this, we need a few easy steps.

First, we define a main point of entry int our project.clj file:

@@@ ruby chapter03w/project.clj @@@

Notice that when we do this, we can simply run that clojure file with:

	lein run <arguments>

So far so good.

Now to get our code to be called properly, we need in chapter03w.core, to define a *main* method.

	(defn -main[& args] 
		(println "Wine, please !"))

Nothing surprising here, we just order wine the Clojure way. 

Now, when packaging all this, we need a directive in our namespace declaration, so we can interact properly with Java:

	(ns chapter03w.core
		(:gen-class))

This will tell the Clojure compiler, to generate the necessary bytecode for access from regular Java.

Now, all this can be written in a simple Clojure namespace like:

@@@ ruby chapter03w/src/chapter03w/core.clj @@@

Running the following Leiningen command will get us wine:

	lein run wine 

But more important we can also now use the uberjar directive:

	lein uberjar

Which will generate a few files:

	Compiling chapter03w.core
	Created /Users/Niko/projects/mascarpone/chapter03w/target/chapter03w-1.0.jar
	Including chapter03w-1.0.jar
	Including clojure-1.4.0.jar
	Created /Users/Niko/projects/mascarpone/chapter03w/target/chapter03w-1.0-standalone.jar

The one we are interested in is the last one:

	target/chapter03w-1.0-standalone.jar

Yes, we have it. Now we can distribute this to people around us so they can use our code. This runs with:

	java -jar target/chapter03w-1.0-standalone.jar wine

And outputs our well deserved red liquid on the command line. 
