Live Music
----

### Overtone
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


### Live Samples
[https://github.com/neatonk/mini-beast](https://github.com/neatonk/mini-beast)

[https://github.com/neatonk/overtone-quil-hacknight](https://github.com/neatonk/overtone-quil-hacknight)