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

### ビヘイビア駆動開発(Behavior Driven Development (BDD))：cucumber

[BDD with Cucumber and Lein](https://github.com/nilswloka/lein-cucumber) & [Cucumber](http://www.matthewtodd.info/?p=112)

[Cucumber](http://cukes.info) をご存知でしょうか。
Cucumber はビヘイビアテストの手法を使って、テスト生活を簡単なものにします。

こないだ、お客さんのために怪しげなAPIを検証するテストセットをまとめたのですが、CUcumberがとても役に立ちましたね。

まずは、profiles.cljにcucumberのプラグインを追加します:

	[lein-cucumber "1.0.2"]

また、project.cljに以下のキーを記述をすることで、cucumberの機能を見つける場所を指定することが出来ます:

	:cucumber-feature-paths ["test/features/"]

そして、テストしたいコードは:

@@@ ruby chapter03/src/clojure_cukes/core.clj @@@

では、実際にテストを始めましょう。
Cucumberでは、まず誰でも何となくは意味の分かるようなテキストを書くことから始めます。

@@@ ruby chapter03/test/features/cukes.feature @@@

次に、それを手助けするためのコードを書くことで、それに意味を持たせることができます。 このファイルはテストフォルダの　_step_definitions_ という名前のフォルダに置きます。

@@@ ruby chapter03/test/features/step_definitions/cuke_steps.clj @@@

Cucumber ステップは正規表現で定義するので、慣れればさらに複雑なテストケースも作ることが出来るようになるでしょう。

すべての準備が出来たら、テストを実行します:

	lein cucumber

実行結果を一緒に見てみましょう ...

	Running cucumber...
	Looking for features in:  [/Users/Niko/projects/mascarpone/chapter03/test/features]
	Looking for glue in:  [/Users/Niko/projects/mascarpone/chapter03/test/features/step_definitions]
	.......F

	java.lang.AssertionError: Assert failed: (= (name (mood)) mood-name)

もう少しですね！ あとはお任せしますので、テストケースが正しく通るようチャレンジしてみてください。
... 一箇所直せば動くはずです ...

### ベンチマークをcriteriumで

[Criterium](https://github.com/neatonk/criterium) はコードの実行時間を計測します。 ベンチマークの落とし穴、特にJVMにおけるベンチマークの潜在的な落とし穴を見つけるために開発されました。

Criteriumについてはそれ以上特に説明することは無いのですが、クリティカルなコードを実行し、その実行時間の平均、最短、最長を得ることが出来ます。
Criteriumは非常に詳細なレポートを出力しますので、アプリケーションのコアとなるような重要なコードの検証にはとても役に立つことでしょう。 ま、何でもかんでも実行時間が短くなれば良いということではありませんが。

いつものように、プロジェクトに追加しましょう:

	[criterium "0.3.1"]

色々なパラメータを使ってrand関数をテストするサンプルです:

@@@ ruby chapter03/src/criterium.clj @@@

作った関数があんまり遅いと、言い訳が出来なくなりますね。。

### 負荷とパフォーマンスチェックならperforate

[Perforate](https://github.com/davidsantiago/perforate) は、ちょうど今やった criterium を使って作られていて、プロファイルを使ってleinからベンチマークを実行することが出来ます。

leinのprofiles.cljにプラグインとして追加します:

	[perforate "0.3.0"]

まずはシンプルなサンプルです:

@@@ ruby chapter03/benchmarks/simple.clj @@@

leinからperforateを実行するには？ お察しの通り、以下のコマンドです:

	lein perforate

結果は、興味深いですね:

	======================
	Goal:  A simple benchmark.
	-----
	Case:  :slightly-less-simple
	Evaluation count : 13225789740 in 60 samples of 220429829 calls.
	             Execution time mean : 4.490337 ns
	    Execution time std-deviation : 0.032741 ns
	   Execution time lower quantile : 4.474494 ns ( 2.5%)
	   Execution time upper quantile : 4.580807 ns (97.5%)

	Found 12 outliers in 60 samples (20.0000 %)
		low-severe	 2 (3.3333 %)
		low-mild	 10 (16.6667 %)
	 Variance from outliers : 1.6389 % Variance is slightly inflated by outliers

	Case:  :really-simple
	Evaluation count : 9048943620 in 60 samples of 150815727 calls.
	             Execution time mean : 6.971874 ns
	    Execution time std-deviation : 0.781822 ns
	   Execution time lower quantile : 6.622552 ns ( 2.5%)
	   Execution time upper quantile : 7.857313 ns (97.5%)

	Found 6 outliers in 60 samples (10.0000 %)
		low-severe	 2 (3.3333 %)
		low-mild	 4 (6.6667 %)
	 Variance from outliers : 73.8351 % Variance is severely inflated by outliers

デフォルトでは、perforateはbenchmarksフォルダにあるすべてのベンチマークを実行します。 プロファイルのプロパティ等、多くのオプションがありますので、色々なカスタマイズが可能です。

では、以下のメソッドのベンチマークを実行してみましょう:

	(defn drink-glass-wine[]
		(slurp "wine"))

すぐに失敗しないと良いのですが。。

#### Clojure流 BDD と Rspec

[speclj](https://github.com/slagyr/speclj) はビヘイビア駆動開発のフレームワークです。
これまではテストファーストな開発について見てきましたが、ここではコードがどのように振る舞うべきかに注目し、ビヘイビア駆動について説明します。

サンプルを実行するためにproject.cljをアップデートします:

	[speclj "2.5.0"]

まずは簡単な算数のチェック:

@@@ ruby chapter03/src/speclj.clj @@@

パスしましたか？

実行すると、specljはいくつかの情報を表示します:

	..

	Finished in 0.00013 seconds
	2 examples, 0 failures

これはエラーなく実行が完了した場合ですが、例えば、上記のサンプルをちょっと細工して失敗するようにすると、どこでエラーしたかが表示されます:

	F.

	Failures:

	  1) Mathematics 1 plus 1 equals 2
	     Expected truthy but was: <false>
	     user.clj:7

	Finished in 0.00034 seconds
	2 examples, 1 failures

プロジェクトの *spec* フォルダにあるspecsを実行するには:

	lein spec

表示される内容は同じです。 リソースの変更を監視し、テストを再実行するのであれば、下記のコマンドライン・スイッチを指定します:

	lein spec -a

これでやっとお行儀よく(behave)できるようになりました。 お父さんとお母さんもきっと喜びますね！

### Travis: オープンソースの継続的インテグレーションサービス

[Travis](http://about.travis-ci.org/docs/user/languages/clojure/) はお気に入りですよね？ インテグレーションサービスの。 え、知らない？ チェックしといた方が良いですよ。

まずは、下記Travisのページからサインアップしましょう:

	https://travis-ci.org

githubのアカウントを使ってサインアップするよう促されます。 シングルサインオンって便利ですね。

Travisのログインすると、プロジェクトが表示されます:

![Travis1](../images/chap03/travis1.png)

ClojureのプロジェクトをTravisで使うためには、.travis.ymlという設定ファイルをプロジェクトのルートフォルダに作ります。 ファイルの内容はこんな感じです。:

	language: clojure
	lein: lein2

これだけで準備が整いました。

ブラウザの画面中のボタンを切り換えましょう:

![Travis2](../images/chap03/travis2.png)

そして:

![Travis3](../images/chap03/travis3.png)

ちなみに、TravisはClojureに特化している訳ではありません [just Clojure](http://about.travis-ci.org/pt-BR/docs/user/languages/clojure/).

C、Haskell、Java、Ruby などなど [have a look](http://about.travis-ci.org/docs/user/getting-started/) 、様々な言語をサポートしています。 ぜひ、ご自分で色々と試してみてください。

ね、お気に入りのサービスになったでしょ。

#### 最終テスト

これで、このセクションはおしまいです。 Clojureでどのようにテストを実行するかをシンプルなサンプルを使って見てきました。 また、TDDとBDDフレームワークを使った自動テストにも触れました。
最後は、それらをオンラインのテストサービスで使ってみました。