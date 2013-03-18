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

Enliveは私のお気に入りの一つです。 Enlive は他のテンプレートの仕組みとはひと味違います。

* コードとマークアップが完全に分離されている
* HTMLの操作にCSSライクな文法を使える
* テンプレートの継承はファンクションのコンポジションで行う

Enliveはテンプレートだけでなく、HTMLのパースもしてくれるスグレものです。

サンプルを一つ紹介します [best enlive tutorial](https://github.com/swannodette/enlive-tutorial/).

このサンプルでは、enliveを使ってWebサイトをスクレイピングします。 コンテンツのURLを取得し、必要なHTMLタグを選択します:

@@@ ruby chapter02/enlive.clj @@@

今度は逆にhtmlファイルでテンプレートを作り、Clojureからテンプレートを操作します:

@@@ ruby chapter02/enlive_02.clj @@@

デザイナーはDreamweaverを使っていつも通りにデザインし、プログラマはそれとは切り離してロジックを書けるということですね！

#### docjureで表計算

[https://github.com/ative/docjure](https://github.com/ative/docjure)

Clojureで表計算するなら、[Apache POI](http://poi.apache.org/) のライブラリを頼るのが一番ですが、docjureを一緒に使うとちょっといい感じです。

例えば、シンプルな表はこんなに簡単に書くことができます

@@@ ruby chapter02/src/docjure.clj @@@

ここにもサンプルがあります ["horrible" documentationn](http://poi.apache.org/spreadsheet/how-to.html#sxssf) :)

ま、あまり見たくないコードですが、いつの日か会社を救うことになるかも知れません。

#### postalでスパムマスター

[https://github.com/drewr/postal](https://github.com/drewr/postal)

メール送るならpostalで。

メール送信についてはご存知かもしれませんが、ぜひこのサンプルを試してみてください。 Gmailのユーザ名/パスワードを有効なものに変えるのを忘れないように。

@@@ ruby chapter02/src/postal.clj @@@

添付もできますし、もちろん日本語のエンコードもサポートしています。

#### DNSはどうなってる？

IPアドレスやホスト名を取得するためにDNS lookupやリバース lookupが必要なら、こんな感じです:

@@@ ruby src/dns.clj @@@

特別なライブラリを使うことなく、いつでも実行可能です。

#### オレのclostacheをイぢるな！：もう一つのテンプレート

[https://github.com/fhd/clostache](https://github.com/fhd/clostache)

少し前にEnliveについて説明しましたが、時にはもっと簡単なファイルベースもしくはインラインのテンプレートが良いこともあります。 そこで、clostache です:

	[de.ubercode.clostache/clostache "1.3.1"]

プロジェクトに上記の設定をしさえすれば、とてもシンプルでセクシーなテンプレートを使うことが出来るようになります。

@@@ ruby src/clostache.clj @@@

話のネタに。 { と } はmustache(ヒゲ)と呼ばれています。

#### CSVのマジック見たい？

さて、ヒゲを手に入れたので、次に住所の入ったcsvファイルをvcard(vcf)形式に変換してみましょう。

[https://github.com/davidsantiago/clojure-csv](https://github.com/davidsantiago/clojure-csv)

まずプロジェクトファイルを設定します:

	[clojure-csv "2.0.0-alpha2"]

そして、コードは以下の通りです。

@@@ ruby chapter02/src/csv.clj @@@

このサンプルは、Webベースのアドレス帳アプリケーションを参考にしました [github](https://github.com/KushalP/addressbook/blob/master/src/addressbook/format.clj)

#### Googleリーダーで足りなければ、このRSSマジックをお試しあれ

[https://github.com/yogthos/clj-rss](https://github.com/yogthos/clj-rss)

	[clj-rss "0.1.2"]

これ以上ないくらい簡単。 :title、:link、:descriptionをセットにしてエントリを返すと、あとはRSSライブラリがやってくれます:

@@@ ruby chapter02/src/rss.clj @@@

参考サイト： [here](http://yogthos.net/blog/17).

#### お知らせするならgrrrowl

[https://github.com/franks42/clj-growlnotify](https://github.com/franks42/clj-growlnotify)

Growl は有名な通知アプリケーションで、何かが起こったことをスクリーン上でエフェクトを使って知らせることができます。
Mac OS X用にはここからGrowlを入手出来ます:

	http://growl.info/

Windowsユーザはここから:

	http://www.growlforwindows.com/gfw/

ここを参考にソースからビルドすることも可能です:

	http://growl.info/documentation/developer/growl-source-install.php

フリーですが、いくらかの寄付をしてコンパイルされたバージョンを入手することも可能です。 Growlをシステムにインストールしたら、以下のコマンドを実行してGrowlがちゃんと動くことを確認しましょう:

	growlnotify -m hello

以下のようなポップアップがスクリーンに表示されるはずです:

![Growl](../images/growl2.png)

それではClojureのコードから使ってみましょう。 プロジェクトファイルに以下の設定をします:

	[clj-growlnotify "0.1.1"]

そして:

@@@ ruby chapter02/src/growl.clj @@@

このスクリプトを実行すると、Growlの通知ポップアップが表示されます:

!["Growl"](../images/growl2.png)

#### CheshireでJSONのすべて

[https://github.com/dakrone/cheshire](https://github.com/dakrone/cheshire)

ClojureでJSONするには？ Cheshire が良いと思います。

	Cheshire はclj-jsonとclojure-jsonを基にした高速なJSONエンコーダーで、Date/UUID/Set/SymbolのエンコードやSMILEサポートなど追加機能を持っています。

プロジェクトに追加するには:

	[cheshire "4.0.2"]

これでClojureの構造をJSONにしたり、JSONを解析して文字列を取り出したり出来るようになりました。 別のシステムとの間でデータのやり取りにとても役立ちます。

@@@ ruby chapter02/src/cheshire.clj @@@

キーワードを戻したり、日付をマップし直したり、もうJSONで頭を悩ませる必要はありませんね。♫

#### あらゆるファイルのダイジェストを計算する

[https://github.com/tebeka/clj-digest](https://github.com/tebeka/clj-digest)

Digest を使って、ファイルの正当性をチェックするために使用するダイジェストを簡単に計算することが出来ます。

このレシピで紹介するのは、毎日使うツールとしてすべてのファイルのダイジェストを計算するサンプルです。 設定は以下の通りです:

	[digest "1.3.0"]

ほら、数行であらゆるもののダイジェストを計算することができます。

@@@ ruby chapter02/src/digests.clj @@@

#### ターミナルを使ってユーザと対話する：lanterna

[https://github.com/sjl/clojure-lanterna/](https://github.com/sjl/clojure-lanterna/)

さて、お次はターミナルでちょとお遊びを。

	[clojure-lanterna "0.9.2"]

上記の設定をしたら、このスクリプトを実行します:

@@@ ruby chapter02/src/lanterna.clj @@@

すると、ターミナルが実行されて、テキストが表示され、キー入力を待ちます。

![Lanterna](../images/lanterna.png)

もちろん、他のターミナルのイベントを待つこともできます。
他にも色々と出来るので、ドキュメントを参照してください [documentation](http://sjl.bitbucket.org/clojure-lanterna/screens/)

#### 今何時？

良い言語は皆日付に対して分かりやすいアクセスの方法を持っています。 Clojureの _clj-time_ のように:

[https://github.com/seancorfield/clj-time](https://github.com/seancorfield/clj-time)

日付や時刻、時間に関する色々を Clojure 流にやってくれる jodatime のラッパーですね。

いつものように project.clj に設定を追加しましょう。

	[clj-time "0.4.5-SNAPSHOT"]

@@@ ruby chapter02/src/time.clj @@@

これで時刻はバッチリ、人生のタイムゾーンもきっと良くなりますね。 :)

#### SSHのショートカット

リモートにあるデータをREPLに持ってきたいなんてことがありますよね。
そういう時は clj-ssh があります。

[https://github.com/hugoduncan/clj-ssh](https://github.com/hugoduncan/clj-ssh)

	[clj-ssh "0.4.3"]

ssh コマンドの自動実行を実現するラッバーで、デフォルトの ssh エージェントをローカルマシン上で使います。

@@@ ruby chapter02/src/ssh.clj @@@

#### HTTP クライアント名人

[https://github.com/dakrone/clj-http](https://github.com/dakrone/clj-http)

clj-http はjava http client のラッパーです [apache http client](http://hc.apache.org/). http クライアントは登場して数年が経ちますが、堅牢なAPIを提供しています。 以下の設定で、Clojureから使うことができます。

	[clj-http "0.3.6"]

次の例では、POSTリクエストを発行してイメージ処理をクラウド上で実施します [blitline API](http://www.blitline.com/docs/quickstart).
実行するファンクションはblurで、残りはJSONのパラメータです。

@@@ ruby chapter02/src/http.clj @@@

#### シリアルポート使ってる？

[https://github.com/samaaron/serial-port/blob/master/src/serial_port.clj](https://github.com/samaaron/serial-port/blob/master/src/serial_port.clj)

このレシピは、PCのUSBポートへのアクセスを実現するものです。 PCのハードウェアに依存するので、もしかしたらお使いのPCでは動かないかもしれません。 後のセクションで紹介するcalxやpenumbraといった別のライブラリもありますが。

@@@ ruby chapter02/src/serial.clj @@@

ここでは、本当にシンプルにポートをオープンして読み書きをしているだけですが、USBに接続する独自のデバイスをコントロールするためのヒントにはなると思います。

#### メモリがない！ どうやってXMLをストリームする？

ここでは、巨大なxmlファイルを処理するために書かれたxml-picker-seqというXMLパーサーを紹介します。
[https://github.com/marktriggs/xml-picker-seq](https://github.com/marktriggs/xml-picker-seq)

このライブラリは1GBを超えるXMLファイルをサポートします。

設定は、いつもの通り:

	[xml-picker-seq "0.0.2"]

@@@ ruby chapter02/src/large_xml.clj @@@

[https://github.com/dakrone/clojure-opennlp](https://github.com/dakrone/clojure-opennlp)

#### Clojureで自然言語処理

このレシピは、この本の中で最も学術的要素の高いトピックの一つです。 OpenNLPにテキストをパースさせて、その結果を自分のアプリケーションで利用することが出来ます。

	[clojure-opennlp "0.2.0"]

ここでは、OpenNLPにテキストをパースさせています:

@@@ ruby chapter02/src/opennlp.clj @@@

上記は非常にシンプルなOpenNLPのサンプルですが、clojure-opennlpのサイトには色々なサンプルがあります [README](https://github.com/dakrone/clojure-opennlp/blob/master/README.markdown) また、独自のモデルをトレーニングする方法についても書かれています [train](https://github.com/dakrone/clojure-opennlp/blob/master/TRAINING.markdown)

以下はWebのページを丸ごとトークンに切り出すサンプルです。

@@@ ruby chapter02/src/opennlp2.clj @@@

#### conduitでストリームプロセッシング全開！

まず、ストリームプロセッシングについて、ここを参照してください:
[http://www.intensivesystems.net/tutorials/stream_proc.html](http://www.intensivesystems.net/tutorials/stream_proc.html)

ここではストリームプロセッシングについてとても良い説明がされているばかりか、Clojureでそれを実現する方法まで説明されています。

conduitの基本は、物事を入れて、定義したステップに沿って変換して、取り出す、です。

	[net.intensivesystems/conduit "0.9.0"]

異なるメッセージングシステム(RabbitMQ、IRC、...)からメッセージを受け取り、処理するというのは、アイディアとしてはApache Camelに似ていますね。

@@@ ruby chapter02/src/conduit.clj @@@

#### イベント処理とストリーム

[https://github.com/ztellman/lamina](https://github.com/ztellman/lamina)
Clojureでのイベントワークフローとストリームプロセッシングです。

Lamina はイベントを受信するキューとその後の処理について素晴らしいやり方を定義しています。

まず、プロジェクトにインポートしましょう:

	[lamina "0.5.0-beta9"]

次に、チャネルがどのようにデータを処理するのかを表示するために [Graphiz](http://www.graphviz.org/Download..php) をインストールしましょう。 Mac OS Xの場合は、ここにbrewを使ったインストール方法があります:

	brew install graphviz

@@@ ruby chapter02/src/lamina.clj @@@

![Lamina](../images/chapter02/lamina1.png)

@@@ ruby chapter02/src/lamina2.clj @@@

![Lamina](../images/chapter02/lamina2.png)

#### Aliceが暗号化すればBobも安心

[Cryptography for Clojure](https://github.com/laczoka/clj-crypto)

このライブラリはJava用の暗号ライブラリであるBouncyCastleをベースにしています。 そのため、キーペアの生成も自前のアプリケーションへの実装も簡単です。

	[laczoka/clj-crypto "1.0.2-SNAPSHOT"]

@@@ ruby chapter02/src/alice.clj @@@

#### Clojureで単位換算

ここでは、なかなかパワフルな単位換算ライブラリを紹介します。

詳細な説明はここにあります:
[Unit of Measure Calculator](https://github.com/martintrojer/frinj) and [samples](https://github.com/martintrojer/frinj/blob/master/src/frinj/examples.clj)

とりあえず、サンプルとして体積と容量の換算を行います。

	[frinj "0.1.3"]

ここでは、まず箱の体積を求めるための3辺の長さを渡して、その箱を水で一杯に満たすために必要な水の量を換算しています。

@@@ ruby chapter02/src/frinj.clj @@@

時間があれば是非他のサンプルを試してみてください。 普段は思いつかないような意外な発見があるかも知れません。

#### JSONでクエリを投げるには

[JsonPath](https://github.com/gga/json-path)

XMLのクエリというと、きっと今までに何度もやってきたことと思いますが、最近はJSONでのやり取りが多いですよね。 というワケで、json-pathです。
コード自体は最近ほとんどアップデートされていませんが、おまじないのように問題なく動きます！

	[json-path "0.2.0"]

以下のサンプルでは、JSON形式のデータをクエリして、Cheshireでパースします:

@@@ ruby chapter02/src/jsonpath.clj @@@

#### ニューラルネットワークにはNetz

	Netz は多層パーセプトロン(Multilayer perceptron = MLP)、	人工ニューラルネットワークモデルの一つをClojureに実装したものです。 Netz provides functions for training and running MLPs. Training is accomplished via gradient descent batch Rprop or standard backpropagation.
	Netz implements Rprop as described by Riedmiller in Rprop - Description and Implementation Details.

[Netz, Clojure Neural Network Library](https://github.com/nickewing/netz)

Here is a very short example, showing how to train and run a Neural Network:

@@@ ruby chapter02/src/netz.clj @@@

In the example, we see we train the network with some hidden values. The options you are most likely to find interesting are:

	:hidden-neurons - A vector containing the number of neurons in each hidden layer. Set to [2 2] for two hidden layers with two neurons each, or [] for no hidden layers. Setting this option is recommended. Default: One hidden layer with the same number of hidden neurons as inputs.

and to change the learning algorithm:

	:learning-algorithm - The algorithm to use while training. Choose either :rprop for the Rprop algorithm or :bprop for standard back propagation. Default: :rprop.

Also, each training variant has some specific options.

As a reminder modern Neural Networks can be applied to a variety of useful tasks such as:

* Function approximation, or regression analysis, including time series prediction and modeling.
* Classification, including pattern and sequence recognition, novelty detection and sequential decision making.
* Data processing, including filtering, clustering, blind signal separation and compression.


#### SSHトンネリング


#### Clojureに学習させるならinfer
#### Shakeでシェルプログラム自由自在

これはちょっと面白いですよ。 それぞれのシェルコマンドがREPLにアクセス出来たらどうでしょう？

それが [Shake](http://sunng.info/blog/2012/09/shake-every-program-can-be-a-clojure-function/) です。

Shake を使うとClojureのロジック/プログラミングの中でシェルコマンドを使うことが出来ます。

以下の設定で、shakeをプロジェクトに追加します:

	[shake "0.2.1"]

サンプルを実行してみましょう:

@@@ ruby src/shake.clj @@@

#### Timelyでタイムリーなスケジューリング

[Scheduler](https://github.com/Factual/timely)

Timely はcron経由でアプリケーションやスクリプトの実行をスケジュールすることができます。 Timelyは非常に軽く、特別な依存関係もありません。

バージョンはまだ 0.0.3 ですが、問題なく動作します:

	[factual/timely "0.0.3"]

@@@ ruby chapter02/src/timely.clj @@@

#### クリーンなHTMLを生成する

これは非常に有名なClojureのライブラリで、Webで見つかる半分くらいのサンプルで使われています。

	[hiccup](https://github.com/weavejester/hiccup)

	Hiccup はClojureでHTMLを表現するライブラリです。 ベクターで各要素を表現し、マップで要素の属性を表現します。

	[hiccup "1.0.2"]

hiccup が出来ることを一口で説明するのは難しいですが、タグとCSSでデータを定義することで簡単にHTMLを生成することが出来ます。

以下、サンプルです:

@@@ ruby chapter02/src/hiccup.clj @@@

後の章では、hiccupとWebフレームワークとを組み合わせて使いますが、ここではひとまずhiccupを使ってシンプルなHTMLを作っています。

#### タイムスケジューラ at

[Ahead of time scheduler](https://github.com/overtone/at-at)

at-at はOvertoneという非常に良く出来た音楽生成プロジェクトから派生しただけあって、非常にシビアなタイミングをさぽーとします。

以下の例では、at-atを使ってどのようにスケジューリングし、スケジュールの開始/停止を行うかを見せています。

@@@ ruby chapter02/src/at_at_clj @@@

#### ANTLR構文解析を使う

ANTLR とは _ANother Tool for Language Recognition_ の略で、Javaでは広く使われている lexer(字句解析器)とParser(構文解析器)です。

ANTLR の最新バージョンは4で、[https://github.com/antlr/antlr4](https://github.com/antlr/antlr4) 登場してから15年以上経過し、今でもたくさんのプロジェクトに利用されています。

lein-antlr Leiningenプロジェクト中の1つまたはそれ以上のANTLR構文からソースコードを生成するLeiningen 2 プラグインです。 MavenのANTLRプラグインとほぼ同等の機能を持ち、Mavenまたは手動のプロセス無しにANTLRが生成したソースコードをClojureのプロジェクトに統合することを可能にします。

[AntLR via Clojure](http://briancarper.net/blog/554/antlr-via-clojure) と [lein-antlr](http://github.com/alexhall/lein-antlr)

Lein antlr は Lein2 にアップグレードされているので、プラグインをプロジェクトに設定します:

	:plugins [[lein-antlr "0.2.0"]]

さらに、構文ファイルについては別に下記の設定が必要です:

	:antlr-src-dir "antlr"
	:antlr-dest-dir "gen-src"

設定出来たら、実行してみましょう。

	lein antlr

構文からjavaのParserとLexerファイルを生成したら、Clojureから使用するためにjavaの相互呼び出しを使います:

@@@ ruby chapter02/src/antlr.clj @@@

当り前ですが、入力ファイルにメチャクチャな文字列をセットするとパースに失敗します。

#### Clojureでgrep/globするには？

もちろん、自分でglobのライブラリを作ることもできますが、幸いなことにすでにあるのでそれを使いましょう:
[glob files](https://github.com/neatonk/clj-glob)

@@@ ruby chapter02/src/glob.clj @@@

ファイルを書き換える例として、どのようにgrepのメソッドを使うかを見てみましょう。

@@@ ruby chapter02/src/grep.clj @@@

特に _indexed_ メソッドがいい感じです:

	(defn indexed [coll]
  		(map vector (iterate inc 1) coll))

grepでパターンにマッチする行数を数えています。

#### パターンマッチング

grepとglobの拡張として、[Matchure](https://github.com/dcolthorp/matchure)を見てみましょう。

Matchure はClojureにおけるパターンマッチングで色々な場面で使うことができます:

* シーケンス分解
* マップ分解
* 等価チェック
* 正規表現マッチ
* 変数代入
* インスタンスチェック
* 2値評価
* 論理演算 (and, or, not)
* 条件分岐 (if, when, cond, fn) と変数

以下、サンプルです:

@@@ ruby chapter02/src/matchure.clj @@@

また、core Clojure ライブラリを使って、コアマッチングを行うことができます:

@@@ ruby chapter02/src/match.clj @@@

2番目のバージョンの利点は同じコードをClojurescriptでも使うことが出来ることです。 Clojurescriptについては後の章で取り上げています。

#### CLojure用Google protobuffer

	clojure-protobuf は Clojure向けにGoogle プロトコルバッファ用インターフェイスを提供します。 プロトコルバッファを使うと、ネットワーク越しに他の言語と通信することが可能で、標準のClojureオブジェクトよりも高速にシリアライズ/デシリアライズすることが出来ます。

clojure-protobufのプロジェクトページ: [https://github.com/flatland/clojure-protobuf](https://github.com/flatland/clojure-protobuf)

以下の Person モデルをresources/proto/personclj.protoとしてインクルードしています:

	message Person {
		required int32  id    = 1;
		required string name  = 2;
		optional string email = 3;
		repeated string likes = 4;
	}

Leiningen プロジェクトファイルに２つの変更が必要です。 まず、pluginセクションにプラグインの設定を追加します:

	[lein-protobuf "0.1.1"]

そして、ライブラリ自体を設定します。

	[org.flatland/protobuf "0.7.2"]

Person モデルの定義と依存関係のセットが済めば、Protobufferオブジェクトを使ってみましょう:

@@@ ruby chapter02/src/protobuffer.clj @@@

#### Luceneにつなげる

データの検索とインデックス付けを簡単に行うには: [Clucy](https://github.com/weavejester/clucy) は多くの企業に採用している有名な検索エンジン Lucene をベースにしており、シンプルさが特徴です。

以下のサンプルでは、インデックスを作り、いくつかの値をストアして検索クエリを送っています。

@@@ ruby chapter02/src/clucy.clj @@@

簡単ですよね？ これで、検索機能の実装ができない言い訳ができなくなりますね。。

#### Solrベースの検索エンジンとつなぐ

前セクションに続いて、ここでは Luceneのフルパッケージを搭載した Solr をインストールします。

	Solr の主な特長は、フルテキスト検索、ファセット検索、リアルタイム(に近い)インデクシング、動的クラスタリング、データベース統合、色々なドキュメント(Word、PDF等)の対応などがあります。

下記のサイトからSolrをダウンロードしましょう。

	http://lucene.apache.org/solr/

_example_ フォルダを探して、そこで以下の通り実行しましょう。

	java -jar start.jar

起動したら、ブラウザでSolrにアクセスします。

  http://localhost:8983/solr/

Solrが正しく起動していれば、次のような画面が表示されます:

![Solr](../images/chap02/solr.png)

それでは、前のセクションでやったのと同じようにClojureのコードからつなげてみましょう。

@@@ ruby chapter02/src/solr.clj @@@

ところで、以下のようにアクセスするとJSON形式のデータを受け取ることが出来ます。

	http://localhost:8983/solr/collection1/select?q=*%3A*&wt=json&indent=true

便利ですね！

#### PDFにしたいときは

以前のレシピで incanter を使ってグラフを作りました。 ここでは、出来上がったグラフをPDFに保存してみましょう。 コード自体は非常にシンプルです ([here](http://data-sorcery.org/2010/02/05/pdf-charts/)):

@@@ ruby chapter02/src/incanter_pdf.clj @@@

Incanterに依らず、普通にPDFを作りたい場合は、ClojureバージョンのiTextを使います。

_project.clj_ に追加します。

	    [com.lowagie/itext "4.2.0"] ; use a more recent itext library
    	[clj-pdf "1.0.5-SNAPSHOT" :exclusions [itext-min "0.2"]]

iTextの以前のバージョンではクラッシュすることが分かっているため、iTextのバージョンを指定しています。

このサンプルでは、いくつかのドキュメント項目を追加し、PDFを生成しています。

@@@ ruby chapter02/src/pdf.clj @@@

生成しているドキュメント項目については、ここに説明があります [github page](https://github.com/yogthos/clj-pdf#document-elements).

リファレンス一覧:

Anchor, Chapter, Chart, Chunk, Heading, Image, Line, List, Pagebreak, Paragraph, Phrase, Section, Spacer, String, Subscript, Superscript, Table, Table Cell

#### Esperを使って複雑なイベントを処理する

ここでは、とても信頼性のあるメッセージ処理ミドルウェア Esperを紹介します。

Esper のサイト: [http://esper.codehaus.org/tutorials/tutorial/quickstart.html](http://esper.codehaus.org/tutorials/tutorial/quickstart.html)

	Esper と NEsper は大量のメッセージやイベントを処理する必要のあるアプリケーションの開発を可能にします。 どちらも色々な方法でイベントのフィルタリングと解析を行い、リアルタイムに応答します。

	また、SQLに似たスクリプトを提供します。

基本的には、Esperでイベントサービスバスを作成し、Clojure と統合します。 APIを使って容易に拡張することが出来ます。

ここでは、ローカルのイベントバスを作成し、コールバックを定義してイベントを追加します。

@@@ ruby chapter02/src/esper.clj @@@

パーフォーマンスに関しては、Esperチームのサポートによると、一般的なラップトップPC上で1秒間におよそ20万個のイベントの処理が可能だそうです。 充分なパフォーマンスですね。

#### LDAP にお友達がいるときは

LDAP ディレクトリへのアクセスが必要な場合も、Clojureにはとても簡単な方法があります。
このレシピを試してみるには、[Apache DS](http://directory.apache.org/apacheds/downloads.html) のダウンロードとインストールが必要です。

Zipをダウンロードして、LDAPサーバをスタートしましょう:

	./bin/apacheds.sh

Windowsの場合は、以下のバッチファイルを実行します。

	apacheds.bat

今回使用するClojure用のLDAPラッパー: [clj-ldap](https://github.com/pauldorman/clj-ldap)

ローカルサーバのエントリの追加と削除:

@@@ ruby chapter02/src/ldap.clj @@@

ここで、ぜひご自分の目で、上記のコードと何年か前にIBMの人が書いたコードを見比べてみてください。

	http://www.ibm.com/developerworks/jp/java/library/j-apacheds2/

ApacheDS Studioを使っているのであれば、設定をこんな感じ:

![ds1](../images/chap02/ldap1.png)

認証設定:

![ds2](../images/chap02/ldap3.png)

デフォルトの ApacheDS パスワードは:

	secret

作成したユーザを確認することができます:

![ds3](../images/chap02/ldap3.png)

#### 新しい reducers sauce について

Rich Hickey による Reduvers の説明:
[Reducers](http://clojure.com/blog/2012/05/08/reducers-a-library-and-model-for-collection-processing.html)

初めて読んときは、スゲーっと思いました。 彼のプレゼンとかコードを見てスゴいと思ったことはあまり無かったのですけど。。

これは、正に簡潔です。 一度使ったら、病みつきになると思います。

以下、Rich の所にあったサンプルにコメントを加えたものです。

@@@ ruby chapter02/src/reducers.clj @@@

### ドメイン特化言語 (DSL)

#### パーサーを作る

パーサーを作るといっても、ここで紹介するのは触りの部分です。 が、ここで紹介するインクリメンタルなパーサーを生成するParsleyはここにあります: [Parsley](https://github.com/cgrand/parsley)

	インクリメンタル: Parsley パーサーはテキストバッファとして動作します。 最高の状況では編集後のパースツリーの再計算はあっという間に終わります(最悪の場合は再開可能なパーサーとして動作)。

パーサーの構文はこんな感じで:

	:expr #{"x" ["(" :expr* ")"]}

以下のような入力を受け取ります:

	x () (xxx) ((x)(xxx))

次のサンプルは、構文を定義し、入力を受け取ります:

@@@ ruby chapter02/src/parsley_1.clj @@@

無効な入力に対しては、期待しない要素がツリーに生成されることが分かります。

Parsleyの素晴らしいところは、インクリメンタルモードで使うときです。 Parsleyはすべてをパースし直すのではなく、適切な場所だけを変更します。 これにより、非常に良いパーフォーマンスを維持します。

次のサンプルで試してみましょう:

@@@ ruby chapter02/src/parsley_2.clj @@@

右側の構文生成は以下の組み合わせで構成されています:
* ベクタ (シーケンス)
* セット (重複しない)
* キーワード (シンボルやオペレータを除く: :*, :+, :?)
* その他のリテラル

さて、ドメイン特化ビールで乾杯してこの章を終わりにしましょうか ;)
