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

[Clojure CLR](https://github.com/richhickey/clojure-clr) is a native port of Clojure to the CLR, Microsoft's Common Language Runtime, or .NET .

ClojureCLR is programmed in C# (and Clojure itself) and makes use of Microsoft's
Dynamic Language Runtime (DLR).

#### Getting Started

As exposed on the website, the goals for Clojure's CLR are multiple:

 -- Implement a feature-complete Clojure on top of CLR/DLR.
 -- Stay as close as possible to the JVM implementation.
 -- Have some fun. 

To start having fun, you would of course need [.NET](http://www.microsoft.com/ja-jp/download/details.aspx?id=17851) to be running some wineries on your local machine.
If just anything, I am trying to lock down my windows environment to VirtualBox.

[Getting the binaries](https://github.com/clojure/clojure-clr/wiki/Getting-binaries) is mostly downloading the binary toyour machine, running unzip, and starting a Clojure REPL using the Clojure.Main executable.

Here is the content of the zip file:

![../images/chap10/clr1.png]

Here is a REPL running on the CLR:

![../images/chap10/clr2.png]



Now that I’ve shown you how to make a call into .NET from the REPL let’s have some CLR fun.

#### First steps in the .NET world

This is using the following [blog entry](http://www.myclojureadventure.com/2011/10/getting-started-with-clojure-clr.html), getting started with Clojure CLR.

Now that we know we have the REPL working it is time to try out a few calls into the .NET world.  The first call we will make is to Console.WriteLine.  Since System.Console is loaded automatically by the REPL we can call it like this: 

    (System.Console/WriteLine “I just called a .NET method!”)

![../images/chap10/net1.png]

One way to load a .NET assembly makes use of the Assembly/LoadWithPartialName method.  Here’s what it looks like:

    (System.Reflection.Assembly/LoadWithPartialName "System.Windows.Forms")

The results of this call will give you the same output as the more verbose Load call did.  Either way you choose will work fine.  If loading a particular version of a an assembly is crucial then I would go with the Load call.  Otherwise I would stick with the LoadWithPartialName method. 

Now that we have loaded System.Windows.Forms I’m going to include it into our namespace so I can use the MessageBox class so I can make calls into it a little cleaner.  I can do that by typing the following in the REPL

    (import (System.Windows.Forms MessageBox))

The call above tells the REPL we want to bring the System.Windows.Forms.MessageBox into our user namespace.  This will allow us to use MessageBox/Show instead of System.Windows.Forms.MessageBox/Show.   When entered the line of code below I saw the dialog box that follows it. Pretty straight forward.

    (MessageBox/Show “Hi from clojure-clr!” “Clojure-CLR Dialog”)

![../images/chap10/net2.png]

Straight forward ? Now let's look in the mirror and ...

#### .NET integration: Calling Clojure from .NET

This is taken mostly from the following [answer on StackOverflow](http://stackoverflow.com/questions/4380054/calling-clojure-from-net) because it shows an easy way to understand how to do things in the Windows world and Clojure.

Let's say we have the following Clojure code:

    (ns hello
      (:require [clojure.core])
      (:gen-class
       :methods [#^{:static true} [output [int int] int]]))
    (defn output [a b]
      (+ a b))
    (defn -output [a b]
      (output a b))
    (defn -main []
      (println (str "(+ 5 10): " (output 5 10))))

We can compile it by calling the following command in a [Command window](http://code.google.com/p/conemu-maximus5/).

    Clojure.Compile.exe hello

This will create several files:

* hello.clj.dll, 
* hello.clj.pdb, 
* hello.exe, and 
* hello.pdb  

You can execute hello.exe and it should run the -main function.

    C:\Users\niko\Desktop\Debug 4.0>hello.exe
    (+ 5 10): 15

We will now call the above code from a simple C# console application. I then added the following references: Clojure.dll, hello.clj.dll, and hello.exe

Here is the code of the console app in C#

    using System;
    namespace ConsoleApplication1
    {
        class Program
        {
            static void Main(string[] args)
            {
                hello h = new hello();
                System.Console.WriteLine(h.output(5, 9));
                System.Console.ReadLine();
            }
        }
    }

As you can see, you should be able to create and use the hello class, it resides in the hello.exe assembly. I am not why the function "output" is not static, I assume it's a bug in the CLR compiler. I also had to use the 1.2.0 version of ClojureCLR as the latest was throwing assembly not found exceptions.

In order to execute the application, make sure to set the clojure.load.path environment variable to where your Clojure binaries reside.

Et voila ! No more excuses for using any other scripting language. You already have the best grapes !

#### Lein CLR: Leinigen takes Clojure and .Net even closer

Now we haven't read and followed all this book to just do things the hard core way. Of course there is a Leiningen plugin for it.

[lein-clr](https://github.com/kumarshantanu/lein-clr) is a Leiningen plugin to automate build tasks for ClojureCLR projects. And the good news, you can use it for both .NET and [Mono](http://www.mono-project.com/Main_Page).

We install it as a regular Leiningen 2 plugin with:

    :plugins [[lein-clr "0.2.0"]]

And then we can create the barebone of our CLR project with:

    lein new lein-clr clr-test

From now on, we need to define where our CLR is installed with a command like:

    set CLJCLR14_40=\Users\niko\Desktop\Debug 4.0
    

Then we are ready to test our code:

    lein clr test
    

And the usual clojure project output shows on the console:

    Testing foo.core-test
     
    FAIL in (a-test) (:)
    FIXME, I fail.
    expected: (= 0 1)
      actual: (not (= 0 1))
    
    Ran 1 tests containing 1 assertions.
    1 failures, 0 errors.
    {:type :summary, :test 1, :pass 0, :fail 1, :error 0}

The Leiningen CLR plugin covers the usual task for a Clojure project:

    lein clr [-v] clean
    lein clr [-v] compile
    lein clr [-v] repl
    lein clr [-v] run [-m ns-having-main] [arg1 [arg2] ...]
    lein clr [-v] test [test-ns1 [test-ns2] ...]

That's most of it for our very short presentation of Clojure CLR, the rest is up to you to deploy your favorite Clojure application on Windows/.NET now. 

Happy Windows Sweet Wine. 

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