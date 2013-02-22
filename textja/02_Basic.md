## Clojureなら数行のコードを書くだけで、毎日の仕事がラクになる

###### アヴァン・アミューズ（Avant amuse）: アミューズ前のちょこっとした料理。

第２章ではいくつものライブラリとテクニックを扱います。 この本の内容は基本的にレシピ風にしてあるので、必ずしも順番に読む必要はなく、気になったところをあちこち拾い読みして書いてあるコードを試してみるのも良いと思います。

この章のサンプルは、ダウンロードしたコードの _chapter02_ フォルダにあります。 REPLを開始して:

	lein repl

以下のようにコードをロードします:

	(load-file "src/incanter.clj")

### 今日からできるClojure

#### REPLに色を付ける

皆さんが普段気にしているかどうかは分かりませんが、メニューは色をつけることによって使い勝手がよくなります。 ここでは、colorizeという小さなgemを紹介しましょう。

	https://github.com/ibdknox/colorize

プロジェクトに含めます:

	[colorize "0.1.1"]

使い方は以下を参照してください:

@@@ ruby chapter02/src/colorize.clj @@@

見た目はこんな感じになります:

![Clojure](../images/chap02/colorize.png)

#### 非同期でhttpサーバとクライアントを

http-kit を使って非同期を試してみましょう。

	https://github.com/shenfeng/http-kit

project.clj ファイルに以下の設定をします:

	[me.shenfeng/http-kit "2.0-SNAPSHOT"]

これでWebのリクエストに応答するサーバがセットアップできました:

@@@ ruby chapter02/src/http_kit_01.clj @@@

サーバが起動すると、クライアントに遅れて応答します。 依存関係がないので手軽なのが良いですね。
http のクライアントも内蔵しているので、ぜひ試してみてください。

@@@ ruby chapter02/src/http_kit_03.clj @@@

ついでに言うと、Web Socket処理も内蔵しています。 サーバを以下のようにスタートします:

@@@ ruby chapter02/src/http_kit_02.clj @@@

あとはオンラインの websocket のテストサイトを見てみましょう:

[http://www.websocket.org/echo.html](http://www.websocket.org/echo.html)

![Websockets](../images/websockets.png)

websocket がこんなに簡単に使えるなんて！ ですね。

#### 統計するならIncanter

Incanter のフルバージョンを [http://incanter.org/downloads/] からダウンロードして、始めましょう [https://github.com/liebke/incanter/wiki#getstarted](https://github.com/liebke/incanter/wiki#getstarted)

ダウンロードしたフルバージョンにはおそらく不要な物も含まれているかもしれませんが、手っ取り早くleiningenのいつものやり方で始めましょう。

	[incanter "1.3.0"]

以下を試してみましょう:

@@@ ruby chapter02/src/incanter.clj @@@

上記のスクリプトの実行結果です

![Incanter](../images/histogram.png)

次はどんなグラフを作りましょうか？

Incanter は *R*言語のようなフルパッケージの統計ツールとなりつつあります。 PDFやExcelファイルをエクスポートしたり、画像として出力したりすることも出来ます。

作者のサイトにはたくさんのサンプルがあり、一見の価値があります。

#### ドキュメント？ literateだよ

[https://github.com/fogus/marginalia](https://github.com/fogus/marginalia)

Marginalia は最高の Clojure用ドキュメンテーションツールです。 例えば、コードに対して必要なコメントを入れると、Marginalia がプロジェクトの最適なドキュメントを生成してくれます。 あとは、それをオンラインで公開するだけです。

*~/.lein/profiles.clj* ファイルに依存関係を書いてインストールします:


	{:user {:plugins [
                  [lein-marginalia "0.7.1"] ; <- これを追加
                  [lein-pprint "1.1.1"]]}}

そして、実行します:

	lein marg

コードに記述したコメントの量にもよりますが、以下のように表示されます:

![Alt text](../images/marginalia.png)

コードに対する依存関係やコメントが表示されます。

#### 簡単ステキなグラフ

[https://github.com/pallix/lacij](https://github.com/pallix/lacij)

さて、Clojureでデータが揃ったら次にすることは？ そのデータを基にチャートを作りたくなりますね。 Lacij はこんな風に自動的にレイアウトされたSVGのチャートを作成してくれます:

![SVG](../images/circle.svg)

そして、グラフはこんな感じで書くことが出来ます:

@@@ ruby chapter02/src/lacij.clj @@@

特長としては動的にノードを追加したり削除したり操作が出来ることでしょうか。

他にも色々なグラフがあるのでサンプルを試してみてください。 簡単にウィンドウを開いてグラフを表示できることが分かると思います:

![Lacij](../images/lacijswing.png)

#### Seesawでユーザインターフェイス

本当に時間の無いときにアプリケーション用のちょっと見た目のいいユーザインターフェイスを作らなくてはいけない。 どうする？

[https://github.com/daveray/seesaw](https://github.com/daveray/seesaw)

最近は何でもかんでもWebですね。 でも、たまにブラウザでアプリケーションを操作するのが嫌だってお客さんもいますよね。 ま、時間をかけてお客さんにブラウザを使うよう説得するのも手ですが、パパッとそこそこ見栄えのいいUIを作ってしまうというのはどうでしょう？ Seesawで。

私が見つけた一番分かりやすそうなチュートリアルは [here](https://gist.github.com/1441520)

Seesaw はJVM上で言語に拘らずに良いUIを作るにはどうしたら良いか？というフラストレーションから生まれました。 Seesaw を使えば、ユーザの入力、フィードバック、コントローラを思い通りに実装出来ます。

Seesawを使った一番シンプルなものです:

@@@ ruby chapter02/src/seesaw.clj @@@

SeesawのWebサイトにもいくつかサンプルがあります。 スタイルをつけてみました:

@@@ ruby chapter02/src/seesaw_02.clj @@@

![seesaw](../images/seesaw.png)

どうでしょう？

#### xpathクエリ

[https://github.com/kyleburton/clj-xpath](https://github.com/kyleburton/clj-xpath)

さて、大きなxmlファイルを出来るだけ早く処理するにはどうすれば良いでしょう？ 答えは clj-xpath を使う、です。

lein用に依存関係を追加します:

	[org.clojars.kyleburton/clj-xpath "1.4.0"]

以下のコードはオンライン上のMaven pom.xml ファイルからすべての開発者、依存関係を取得します:

@@@ ruby chapter02/src/clj_xpath.clj @@@

	There are two forms of many of the functions, with one set ending with a star ‘*’. The non-suffixed forms will perform their operation on a single result node, raising an exception if there is not exactly one result from applying the xpath expression to the XML document. The ‘*’ suffixed forms return all of the matched nodes for further processing.

#### htmlのパースをおいしいjsoupで

せっかくXMLに関連したことをやったので、javaでは有名なJsoupというhtmlパーサを使ってみましょう。

[https://github.com/mfornos/clojure-soup](https://github.com/mfornos/clojure-soup)

このラッパーを使って、ローカル/リモートに関係なくhtmlファイルのパースを行うことができます。 ライブラリを追加したら、早速試してみましょう:

このサンプルでは、Web上の絵文字アイコンを取得します:

@@@ ruby chapter02/src/clojure_soup.clj @@@

#### Enliveを使ったテンプレート

Enliveは私のお気に入りの一つです。 Enlive は他のテンプレートの仕組みとはちょっとひと味違います。

* コードとマークアップが完全に分離されている
* HTMLの操作にCSSライクな文法を使える
* テンプレートの継承はファンクションのコンポジションで行う

HTML parser, and templating framework at the same time, Enlive does a super job of integrating HTML fun. (yes you read me.)

This is an example taken from the [best enlive tutorial](https://github.com/swannodette/enlive-tutorial/).

This is how you use enlive to for website scraping. Fetch the content of a URL then select the html tag you need:

@@@ ruby chapter02/enlive.clj @@@

Reversly, you declare templates in regular html files, and apply some logic in Clojure. See how:

@@@ ruby chapter02/enlive_02.clj @@@

Your great designer can do his Dreamweaver work the way he or she usually does it and you can plug in your own logic without stepping on each other's foot. Sweet. Slick.

#### docjureで表計算
#### postalでスパムブロック！
#### DNSはどうなってる？
#### オレのclostacheをイぢるな！：もう一つのテンプレート
#### CSVのマジック見たい？
#### Googleリーダーで足りなければ、このRSSマジックをお試しあれ
#### お知らせするならgrrrowl
#### CheshireでJSONのすべて
#### 色んなものをザックリ計算してみる
#### ターミナルとユーザインターフェイス：lanterna
#### そろそろ、clj
#### SSHのショートカット
#### HTTP クライアント名人
#### シリアルポート使ってる？
#### メモリがない！ どうやってXMLをストリームする？
#### Clojureで自然言語処理
#### conduitでストリーム全開！
#### イベント処理とストリーム
#### Aliceが暗号化すればBobも安心
#### 単位変換機
#### JSONでクエリを投げるには
#### ニューラルネットワークにはNetz
#### SSHトンネリング
#### Clojureに学習させるならinfer
#### Shakeでシェルプログラムを自由自在
#### 予定を組む？Timely
#### hiccups無しで美しくHTMLを生成する
#### タイムスケジューラ
#### ANTLR構文解析を使う
#### Clojureでglobするには？
#### 非同期HTTPクライアント
#### プロトコルバッファー
#### パターンマッチング
#### Luceneにつなげる
#### Solrベースの検索エンジンとつなぐ
#### PDFにしたいときは
#### Esperを使って複雑なイベントを処理する
#### Monads について
#### 新しい reducers sauce について
#### logical について少々
#### haskell について
#### oauth を使う
#### LDAP にお友達がいるときは

### DSL
#### パーサーを作る