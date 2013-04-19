## イケてるエンジニアになるために： Clojureでデプロイ

###### グラニテ（Granité）: 料理と料理の間の口直し

### Uber jar: ちょっと思い出そう

なぜ Lein を使うのでしたっけ？

Leinを使い倒すことで、日々の作業が簡単になるからでした。
Leiningenは *uberjar* という素晴らしいものを生成することができます。 通の間では、*standalone jar* と呼ばれることもありますが。

*uberjar* はプロジェクトのすべての依存関係とコードを単一のjarファイルにパッケージしたものです。 javaの世界では普通に行われている方法ですね。

では、実際に使うための設定をしていきましょう。

まずは、project.cljファイルの中にメインのエントリポイントを設定します:

@@@ ruby chapter03w/project.clj @@@

さて、Clojureファイルを実行する時は以下のコマンドでしたね:

	lein run <arguments>

コードがちゃんと呼ばれるようにするためには、chapter03w.core の中で *main* メソッドを定義する必要があります。

	(defn -main[& args]
		(println "Wine, please !"))

特に変わったことはしていませんね、Clojure式にワインを注文しているだけで。

さて、これらすべてをパッケージする時はネームスペース宣言の中でおまじないが必要です。 それによって、javaとやり取りすることが出来ます:

	(ns chapter03w.core
		(:gen-class))

上記の記述により、Clojureコンパイラは標準のjavaからアクセス出来るように必要なバイトコードを生成します。

全体のサンプルは以下の通りです:

@@@ ruby chapter03w/src/chapter03w/core.clj @@@

では、Leiningen を実行してワインを手に入れましょう:

	lein run wine

もう一つ大事な uberjar ディレクティブを試してみましょう:

	lein uberjar

これによって、以下のことが行われます:

	chapter03w.core のコンパイル
	/Users/Niko/projects/mascarpone/chapter03w/target/chapter03w-1.0.jar を生成
	chapter03w-1.0.jar をインクルード
	clojure-1.4.0.jar をインクルード
	/Users/Niko/projects/mascarpone/chapter03w/target/chapter03w-1.0-standalone.jar を生成

この中で興味深いのは:

	target/chapter03w-1.0-standalone.jar

そうです、出来てるんです。 このファイルを配布すれば、他の人も我々のコードを使うことができるということですね。 このファイルを実行するには:

	java -jar target/chapter03w-1.0-standalone.jar wine

そして、我々の美味しい赤い液体がコマンドラインに表示されるというわけですね。
