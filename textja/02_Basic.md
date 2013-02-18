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
#### 簡単ステキなグラフ
#### Seesawでユーザインターフェイス
#### xpathクエリ
#### htmlの解析をおいしくjsoupで
#### Enliveを使ったテンプレート
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