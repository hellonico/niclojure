## Clojure recipes of the day

In Chapter 02, we will go through a number of libraries and techniques.
Since this is a menu, there is no specific order for you to read. Jump back and forth, come back when you need to be sure of something, and on top of it, try the short bits of code.  

### I'll have the usual

####  Give some colors to your REPL

I am not sure if you noticed, but a menu always get better when you put some colors in it. There is a little gem there to do that in the name of colorize. 

	https://github.com/ibdknox/colorize

Include it in your project.clj with:

	[colorize "0.1.1"]

Here are some ways to use it:

@@@ ruby chapter02/src/colorize.clj @@@

And some graphic results:

![Clojure](../images/chap02/colorize.png)

####  Asynchronous http server and ... client. 

In the same time, you can turn yourself asynchronous with http-kit. 

	https://github.com/shenfeng/http-kit

This is what needs to be added to your project.clj file:

	[me.shenfeng/http-kit "1.3-SNAPSHOT"]

@@@ ruby 36_http_kit.clj @@@

The server will be started, and will delay answer to the client. No dependency so kind of very lightweight and wicked.
It also incorporate a kinda cool http client, again with no dependencies. Give a try.