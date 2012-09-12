GPU, Processing, OpenCV and imaging
-----

### Quil
[https://github.com/quil/quil](https://github.com/quil/quil)

Quil is to [Processing](http://processing.org/) what Clojure is to Java, some fresh air.

This is how your quil-ed processing sketch now looks like:

@@@ ruby 10_quil.clj @@@

Note the decor set to false, that hides most of the uglyness of the Window borders.

And all the [examples](https://github.com/quil/quil/tree/master/examples/gen_art) you have ever dreamed from the Generative Art book have been implemented in Clojure/Quil.

### Calx
[https://github.com/ztellman/calx](https://github.com/ztellman/calx)

[OpenCL](http://www.drdobbs.com/parallel/a-gentle-introduction-to-opencl/231002854) binding library. OpenCL is meant to be a universal parallel computing library. With calx we now have its power straight from Clojure. 

<code>
[calx "0.2.1"]
</code>

@@@ ruby 42_calx.clj@@@

### Penumbra
[https://github.com/ztellman/penumbra](https://github.com/ztellman/penumbra)

OpenGL binding library.  This is not actively developped anymore, but still a good way to play with OpenGL from clojure, or simply to do GPU computing straight from your computer.

The version we are including here is not from the original author but is compatible with the latest clojure
<code>
[bronsa/penumbra "0.6.0-SNAPSHOT"] 
</code>

@@@ ruby ruby 43_penumbra.clj @@@