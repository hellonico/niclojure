## Music and sound in Clojure

###### Salade: Salad, what you get right before the cheese of course

In the previous sections we have seen how to be serious and have some secrets for when the time is needed to work and be serious. From now on, we will be going a little bit on the fun side of things, with for a start working with 

### All you need to perform live music is here
[https://github.com/overtone/overtone/wiki/Getting-Started](https://github.com/overtone/overtone/wiki/Getting-Started)

Because you really need to live audio programming to be a real VJ these days.
This is how you install it:

@@@ ruby 12_overtone.clj @@@

Once the library is in your project, type

<code>
    (use 'overtone.live)
</code>

And now you can define an instrument
<code>
    (definst foo [] (saw 220))
</code>

And make some sound !

<code>

> (foo) ; Call the function returned by our synth
4      ; returns a synth ID number
> (kill 4) ; kill the synth with ID 4
> (kill foo) ; or kill all instances of synth foo

</code>

### Mini-beast
[https://github.com/overtone/mini-beast](https://github.com/overtone/mini-beast)

### Midi sound with Clojure
[Midi](https://github.com/pcsanwald/clojure-midi)

### Live Samples
[https://github.com/neatonk/mini-beast](https://github.com/neatonk/mini-beast)

[https://github.com/neatonk/overtone-quil-hacknight](https://github.com/neatonk/overtone-quil-hacknight)

### Speech Recognition: Clojure with speeech !
[Clojure with speech](https://github.com/klutometis/speech-recognition)

### Control your sound
[Sound control](http://opensoundcontrol.org/implementation/osc-clj-clojure-osc-library)

### Stream and get streamed with Icecast audio streaming server
[Ice Client Server](https://github.com/jblomo/iceclient)

## GPU, Processing and other visual tools

### Processing, the best visual tool ported to Clojure for easy animation
[https://github.com/quil/quil](https://github.com/quil/quil)

Quil is to [Processing](http://processing.org/) what Clojure is to Java, some fresh air.

This is how your quil-ed processing sketch now looks like:

@@@ ruby 10_quil.clj @@@

Note the decor set to false, that hides most of the uglyness of the Window borders.

And all the [examples](https://github.com/quil/quil/tree/master/examples/gen_art) you have ever dreamed from the Generative Art book have been implemented in Clojure/Quil.

### The rootbeer GPU compiler
[https://github.com/pcpratts/rootbeer1](https://github.com/pcpratts/rootbeer1)

### OpenCL
[https://github.com/ztellman/calx](https://github.com/ztellman/calx)

[OpenCL](http://www.drdobbs.com/parallel/a-gentle-introduction-to-opencl/231002854) binding library. OpenCL is meant to be a universal parallel computing library. With calx we now have its power straight from Clojure. 

<code>
[calx "0.2.1"]
</code>

@@@ ruby 42_calx.clj@@@

### More OpenCL, using JOCL
[OpenCL Playground](https://github.com/hraberg/sleipnir)

### OpenGL for Clojure is here
[https://github.com/ztellman/penumbra](https://github.com/ztellman/penumbra)

OpenGL binding library.  This is not actively developped anymore, but still a good way to play with OpenGL from clojure, or simply to do GPU computing straight from your computer.

The version we are including here is not from the original author but is compatible with the latest clojure
<code>
[bronsa/penumbra "0.6.0-SNAPSHOT"] 
</code>

@@@ ruby 43_penumbra.clj @@@

### Blender exporter for Penumbra
[https://github.com/krumholt/penumbra-blender-exporter](https://github.com/krumholt/penumbra-blender-exporter)

## OpenCV and Imaging

### You have Vision !
[https://github.com/nakkaya/vision](https://github.com/nakkaya/vision)
[http://nakkaya.com/vision.html](http://nakkaya.com/vision.html)

### Fiji, the best imaging tool in java can be clojure scripted !
[Clojure Scripting in Fiji](http://fiji.sc/wiki/index.php/Clojure_Scripting)