## Plateform

Now this is it. This is the last chapter of this book ! We have gone a long way into the landscape of Clojure, and all the different personalities the language can take.

This last chapter is about expanding the scope of our favorite language. So far we have seen it running mostly on what we have seen was the Java Virtual Machine.
But there are probably times when you want to still use Clojure, but you do not want to, or cannot use the JVM *and* when you want to reuse some Clojure code in a completely different environment.

We will see how we can run Clojure code on top of the Ruby virtual machine with Rouge, then a trip to Microsoft.NET environment and see how we can integrate Clojure seamlessly with Microsoft VMs, and finally the new and exciting Clojurescript, which brings Clojure to any javascript runtime, making Clojure on par with other new cool kids like coffeescript.

Let's enjoy every bits of it !

### I see! Ruby + Clojure = Rouge !

Rouge should maybe have been presented in the first chapter of this book. Seriously, it is quite very impressive. I also had some questions and team responded and fix problems in less than an hour. 

So why Rouge ? Well, Rouge means red, and there can't be too much of a good thing, especially with good red wine.

[Rouge](http://rouge.io/), Clojure on the Ruby VM, provides two great advantages:

* Quick boot time (currently around 0.1s).
* Ruby's gems are fun to use, tend to be modern with decent APIs.
* Clojure is awesome.

#### First baby steps: Clojure walks on rubies

You can try a Rouge REPL online at [Try Rouge](http://try.rouge.io/), or install the gem to get the local REPL:

    gem install rouge-lang
    rouge

And that's it, we have a Clojure REPL ready to go.

So what can we do with it ? 

Our first example shows some basic Clojure integration code:

@@@ ruby chapter10/rouge/first.rg @@@

Sweeet !!! It feels like pure Clojure, and it's super fast. 

But wait, there's more. 

#### Teenager: Clojure gets some gems

##### Mechanize the web

The ruby world has a fantastic gem called Mechanize. 

The Mechanize library is used for automating interaction with websites. Mechanize automatically stores and sends cookies, follows redirects, and can follow links and submit forms. Form fields can be populated and submitted. Mechanize also keeps track of the sites that you have visited as a history.

Now that we have rouge, we can now use the mechanize library directly from Clojure. Have a look.

Let's install the gem with:

    gem install mechanize

And now we can refer to it straight from our code:

@@@ ruby chapter10/rouge/mechanize-rg/test.rg @@@

And finally, *You* can give it a try with:

    rouge test.rg

We can see the code integration works more or less the same as the java integration, we are using the dot notation to reference methods coming from the virtual machine world.

Sweet ! 

##### Events in a world of rubies

Supposing now we are installing [eventmachine](http://rubyeventmachine.com/), the fast, simple event-processing library for Ruby programs...

    gem install eventmachine

We write quickly some wrappers in our namespace this way:

@@@ ruby chapter10/rouge/em-rg/em-rg.rg @@@

And now we can refer to those wrappers using the magical, but usual *require* directive in our namespace definition:

    (:require [em-rg :as em])

And ... Voila !

@@@ ruby chapter10/rouge/em-rg/test.rg @@@

Ruby timers controlled from Ruby. The output of the above will be:

    2013-05-24 18:35:02 +0900 Execution begins.
    2013-05-24 18:35:03 +0900 First timer.
    2013-05-24 18:35:04 +0900 Bye bye.

But go for it ! Try it yourself, and enjoy the starting speed of the Ruby VM for some very ingenious Clojure scripting.

### ClojureCLR (clojure on .NET)
[https://github.com/richhickey/clojure-clr](https://github.com/richhickey/clojure-clr)

http://stackoverflow.com/questions/3044395/how-do-i-execute-a-dll-file
w
https://github.com/clojure/clojure-clr
https://github.com/clojure/clojure-clr/wiki/Getting-binaries

A port of Clojure to the CLR, part of the Clojure project. 

https://github.com/kumarshantanu/lein-clr

### ClojureScript (clojure compiling to Javascript)
[https://github.com/clojure/clojurescript](https://github.com/clojure/clojurescript)

#### Intro
[ClojureScripting Intro](http://jeditoolkit.com/2012/03/17/clojurescripting-intro.html)

#### Single Page Application Using Clojure Script
[https://github.com/neatonk/one](https://github.com/neatonk/one)

#### Enlive inspired templating for Clojure Script
[Enlive inspired templating](https://github.com/ckirkendall/enfocus)

#### Clojure Home Page
Your whole website in Clojure: https://github.com/runexec/chp

#### Bringing the magic of ClojureScript to the desktop via Gnome Shell 
[Gnome Shell Extension](https://github.com/technomancy/lein-gnome)	