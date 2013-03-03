### Web Testing 
#### VCR or your HTTP Playback 

[vcr-clj](https://github.com/fredericksgary/vcr-clj) is a clojure library in the spirit of the VCR Ruby library. It lets you record HTTP interactions as you run your tests, and use the recordings later to play back the interaction so you can run your tests in a repeatable way without needing the external components to be available/accessible.

@@@ ruby chapter03/src/vcr.clj @@@

#### Ring App Testing
[Kerodon](https://github.com/xeqi/kerodon) could be the easiest way to test your Ring library:

@@@ ruby chapter03/src/kerodon.clj @@@
