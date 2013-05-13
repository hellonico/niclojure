## Music and sound in Clojure

###### Salade: Salad, what you get right before the cheese of course

Well, we may have been going too far in the last chapter !! All this PAAS and Cloud management is great, but .. you know.

I think it's time we switched to something more fun. So we will go through this section with some sound fun. Speech, voice, sounds of sound. Then on to do a bit of live music programming to make the wine dance with the salad.
Last part, we will go through some graphic samples on how to make pictures and graphics dancing along with the music.
Bring your glass and enjoy !

### Clojure with Speeech, Clojure with Recognition!

#### Voice Recognition with Clojure

To get your wine order properly you need to get it recognized properly. We will be using a semi-hidden Google API to analyze our recorded sound for this. 

To add the necessary glue to do our work, we will add the following dependency:

    [hellonico/speech-recognition "1.0.2"]

[Our customized Speech Recognition](https://github.com/hellonico/speech-recognition) is entirely based on [speech-recognition](https://github.com/klutometis/speech-recognition) with just enough updated code to perform.
I found [Peter Danenberg](https://github.com/klutometis) research just fascinating, with a few lines of his code pumping inspiration into new projects and ideas. Check it out !

Now that we have the above in our project, we can start a REPL, and be done with:

@@@ ruby chapter07/speech/src/speech/hear.clj @@@

So a few notes on this code. First we can change the language to *en*, *fr* etc... to get our speech recognized in different languages. 

The input index, is relative to your machine and the number of devices. You need to search for the proper input on your machine, with _(get-mixers)_ :

    user=> (get-mixers)
    #<DirectAudioDeviceInfo Default Audio Device, version Unknown Version>
    #<DirectAudioDeviceInfo Built-in Input, version Unknown Version>
    #<DirectAudioDeviceInfo Built-in Output, version Unknown Version>

The easy life cycle to get this working is detailed in the diagram below:

![voice](../images/chap07/voice.png)

Now to extend this to something great, you realize we have just the start of a Siri, Apple copyright here, framework. You can hear the sentence and process it with some clever pattern matching.

#### Voice with Clojure

The above part would be incomplete if we could not get our order to be pronounced properly as well. Here comes the speaking text part.

We will use a library from Peter again, [speech-synthesis](
https://github.com/klutometis/speech-synthesis), this time with no modification. 
To add this to your project:

    [facts/speech-synthesis "1.0.0"]

@@@ ruby chapter07/speech/src/speech/speak.clj @@@

But, I know what you are going to say .. "Wait here ... where's my Japanese Girl talking to me?"

That is where, we are going to realize that we did not need a library after all !!

This is the whole source code for the library above with some slight changes:

@@@ ruby chapter07/speech/src/speech/speak_ja.clj @@@

That is all. We just added some lines to recognize Japanese sentence for us.

"Arigatou ne-"

### Midi sound with Clojure

Transitioning from recording and playing sound, we will now move to Midi sounds. The JVM already has by default everything needed to play them. 

We will go over the [Clojure Midi Library](https://github.com/pcsanwald/clojure-midi) so see how we can turn data to music and at the same time this will be a great introduction for the coming audio recipes concepts.

Let's start a REPL from the clojure-midi folder in the examples. And let's go through the following code line by line.

@@@ ruby chapter07/clojure-midi/src/music/simple.clj @@@

We first start by defining a scale. Here A Major. If you remember your classes of music from junior high school, or your latest DJ sets, you'll remember that the scale of A is _A B C# D E F# G#_ and we declare it as is here.

We then make a chord, using that scale, and the base note of the chord. We have triads and seventh chords implemented, that will return a set of 3 or 4 notes depending.

Now we prepare a sound by using each of the note, and a duration, the default duration being 2000ms, and we have something that is ready to play through the midi interface.

And that is it, we last use the *perform* method to actually make the sound go through the speaker.

My favorite scale is E Dorian, which goes like:

    (def e-dorian [:E :F :G :A :B :C :D])

So please, be sure to try a few chords with that scale.

Now this little piece of recipe, while very short, has gained us some knowledge and insight as to how to use data, or simple text, to make sound. This is the core concept of Clojure here again exposed as code is data is code. 

### Live Samples
[https://github.com/neatonk/mini-beast](https://github.com/neatonk/mini-beast)

[https://github.com/neatonk/overtone-quil-hacknight](https://github.com/neatonk/overtone-quil-hacknight)

### Control your sound
[Sound control](http://opensoundcontrol.org/implementation/osc-clj-clojure-osc-library), 
[https://github.com/overtone/osc-clj]
Barebone of overtone


### All you need to perform live music is here
[https://github.com/overtone/overtone/wiki/Getting-Started](https://github.com/overtone/overtone/wiki/Getting-Started)

http://overtone.github.io/

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


## GPU, Processing and other visual tools

### Processing, the best visual tool ported to Clojure for easy animation
[https://github.com/quil/quil](https://github.com/quil/quil)

https://github.com/gtrak/quilltest/

Quil is to [Processing](http://processing.org/) what Clojure is to Java, some fresh air.

This is how your quil-ed processing sketch now looks like:

@@@ ruby 10_quil.clj @@@

Note the decor set to false, that hides most of the ugliness of the Window borders.

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