### テストする
###### ポタージュ（Les potage）:スープ

Now on our way to do proper engineering and test everything

#### ちょっと思い出そう

この本の始めの方では、leinとREPLを使って、いかにプログラミングのワークフローをスピードアップするかについて色々と話してきましたね。
そこで、ここではテスト、私は熱烈なTDD(Test Driven Development = テスト駆動開発)の支持者なので、テストについて書きたいと思います。

例えば、ファンクション say-hello をテストしたい場合、どうしたら良いでしょう？

@@@ ruby chapter03/src/test.clj @@@

ソースをご覧いただくと、定義したテストを直接呼び出しています:

	(a-test)

あるいは、すべてのテストを実行することも出来ます:

	(run-tests)

この場合、実行した結果は以下のようになります:

	user=> (run-tests)

	Testing user

	Ran 1 tests containing 1 assertions.
	0 failures, 0 errors.
	{:type :summary, :pass 1, :test 1, :error 0, :fail 0}

では、早速以下のようにコードを修正しましょう。

	(defn say-hello[name]
	   (str "hello " name))

これだけです。 テストそのもののために、あまり時間を使う必要がありませんね。

clojure.test の詳細は [online](http://richhickey.github.com/clojure/clojure.test-api.html) にありますが、それほど複雑ではないですね。

また、例えば、あるフォルダにあるテストを実行したい場合はどうしたら良いでしょう？ testフォルダに下記のテストがあるとしましょう:

@@@ ruby chapter03/test/chapter03/core_test.clj @@@

で、以下のコマンドを実行すると

	lein test

上記のテストが実行されます。 この場合、実行結果は以下のようになります。

	lein test chapter03.core-test

	Testing chapter03.core-test

	FAIL in (a-test) (core_test.clj:6)
	FIXME, I fail.
	expected: (= 0 1)
	  actual: (not (= 0 1))

	Ran 1 tests containing 1 assertions.
	1 failures, 0 errors.
	Tests failed.

### セコくないけど、midje を使ってテスト

[Midje](https://github.com/marick/Midje) を使ってイケてるテスト駆動開発をしましょう。 Midje はコードのテストを楽しくするだけでなく、プロジェクトにおける経験とテストに対する正しい心構えを教えてくれます。

Midjeのインストールには2通りの方法があります。

一つはLeingingで、~/.lein/profile.clj ファイルに以下の設定をします:

	[lein-midje "3.0-RC1"]

さらに、project.cljファイルに次の依存関係を追加します。

	:profiles {:dev {:dependencies
		[[midje "1.5-RC1"]]
	}}

そして、midjeの記述法に沿ってシンプルなテストを書きます:

@@@ ruby chapter03/test/chapter03/midje.clj @@@

ここからがお楽しみ。 テストを開始するleinのプロセスをスタートしてみましょう:

	 lein midje --lazytest

例によって、1回目のテストは失敗します:

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

ソースコードの修正をすると、テストを再実行するためのコマンドをタイプしなくても、テストが再実行されます。

今のところ、REPLからmidjeを使うときに知っておくと便利な方法があります。 以下のパッケージをインポートします:

	(use 'midje.repl)

Midje はテストの開始に必要なネームスペースをインポートするので、REPLのセッションからテストを実施出来ます。

私がワインをいただいている間に、ぜひ色々試してみてください。

### Webサイトのテストで楽するならselenium

[clj-webdriver](https://github.com/semperos/clj-webdriver/wiki) は、JavaのSelenium-WebDriverを使って Firefox、Chrome、Safari や Internet ExplorerのようなGUIベースのWebブラウザをclojureから操作するためのライブラリです。

	[clj-webdriver "0.6.0-beta2"]

公開されたAPIを持たないようなWebサイトをclojureを使ってテストするような場合、例えばここではgithubにログインしてみましょう。

@@@ ruby chapter03/src/taxi.clj @@@

APIについて詳しく紹介しているブログがあります: [blog post](http://corfield.org/blog/post.cfm/automated-browser-based-testing-with-clojure)

私はよくWebサイトのスクリーンショットを取りますが、こんな感じです:

@@@ ruby chapter03/src/taxi2.clj @@@

これで、タイマーを使って定期的にWebサイトのスクリーンショットを取るとか、様々な使い道がありそうですね。

### 振る舞い駆動開発(Behavior Driven Development (BDD))：cucumber

[BDD with Cucumber and Lein](https://github.com/nilswloka/lein-cucumber) & [Cucumber](http://www.matthewtodd.info/?p=112)

[Cucumber](http://cukes.info) をご存知でしょうか。
Cucumber は振る舞いテストの手法を使って、テスト生活を簡単なものにします。

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

### ベンチマークをcriteriumで
### ちょっと!  急いでWebアプリをテストして!!
### 負荷とパフォーマンスチェックならperforate
### ビデオみたいにHTTPリクエストを再生
### Ringを使ったアプリケーションテスト
### Travis: オープンソースな継続的インテグレーションサービス
### かったるいので、lazytest
