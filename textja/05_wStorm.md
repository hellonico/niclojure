### Storm: Clojureから利用可能なリアルタイム分散システム

Storm はリアルタイム処理の hadoop を目指しています。 前のセクションで見てきたように、hadoopはジョブを受け取り、受け取ったジョブを処理しますが、それらのジョブがいつ終了するのか我々には分かりません。
Storm はこの制限を克服し、可能性を広げようとしています。

Stormのクラスタは一見すると、Hadoop クラスタに似ています。 Hadoopではマップリデュースの「ジョブ」を実行するのに対して、Stormでは「トポロジー」を実行します。 ジョブとトポロジーとはかなり違いますが、一つ大きく異なる点として、マップリデュースのジョブはいつかは終了するのに対して、トポロジーは永遠に(またはkillされるまで)
メッセージを処理し続けます。

#### Stormの基本

Stormの基本的な考え方は"Stream"です。 Streamは途切れずに連続するTupleです。 Storm は堅牢な分散化のためにStreamを新しいStreamへ変換するための基本構造を提供します。 例えば、ツイートのStreamをトレンディング・トピックのStreamに変換したりします。

Streamを変換するためのStormが提供する基本構造には "Spout" と "Bolts" があります。 Spout と Bolt はアプリケーションに特化したロジックを実行するためのインターフェイスを持っています。

SpoutはStreamのソースです。 例えば、SpoutはKestrelのキューからtupleを読み取り、それらをstreamとして出力します。 あるいは、SpoutはTwitter APIに接続し、ツイートのStreamを出力します。

Boltは複数の入力streamからtupleを受け取り、何か処理をし、新しいstreamを出力します。 ツイートstreamからトレンディング・トピックstreamへの変換のような複雑なstream変換には、複数のステップ、すなわち複数のBoltを使います。 Boltでは関数を実行したり、tupleのフィルタリング、ストリーミングアグリゲーション、ストリーミングの結合、データベースアクセスなど色々なことが出来ます。

SpoutとBoltからなるネットワークは*topology*にパッケージされ、実行するためにStormクラスタに送信されます。 TopologyはSpoutとBoltのノードから成るstream変換を図式化したものです。 図式中の矢印はどのBoltがどのstreamを受信するかを表します。 SpoutやBoltがStreamに送信するtupleは、そのstreamを受信する全てのBoltに送信されます。

上記の内容を図にしたものが、以下のダイアグラムです:

![toplogy](../images/chap05/topology.png)

#### Storm starter

[Nathan](https://github.com/nathanmarz) がStormについての要点をドキュメントにまとめていますので、 [Storm starter](https://github.com/nathanmarz/storm-starter) を使ってそれを見ていきましょう。

Stormのサンプルは [Storm DSL for Clojure](https://github.com/nathanmarz/storm/wiki/Clojure-DSL) をフルに活用していますので、もし不明瞭な部分があればこのサイトを参照してください。

Topologyの定義に関しては以下の通りです。

    Topologyの定義にはTopology関数を使用します。
    Topologyが受け取る２つの引数: Spoutの仕様マップとBoltの仕様マップ
    SpoutとBoltの仕様は、入力や並列性等を指定することでコンポーネントのコードをtopologyに紐付けます。

まずはシンプルなサンプルを動かしてみましょう。

@@@ ruby chapter05_storm/src/clj/storm/starter/clj/word_count_0.clj @@@

ボトムアップで説明すると、

* run-local がローカルのStormクラスタを開始する。 この次に説明するTopologyを、新しく生成したクラスタに送信する。 Topologyを送信するときに、ワーカーの数やTopologyの出力を見るかどうか等のパラメータを定義することが出来る。 例： {TOPOLOGY-WORKERS 3 TOPOLOGY-DEBUG true}
* mk-topology: 前述の通り、Topologyは２つのマップ、Spoutか入力StreamのマップとBoltのマップで出来ている。 ２つのマップ中のIDは重複してはいけない。
* 始めのSpoutはハードコードされた４つの文の中からランダムに文を選び、出力する。
* Boltが処理したデータは、{"1" :shuffle}で生成されたSpoutに接続され出力される

以下のLeiningenのコマンドで、ローカルにクラスタとTopologyを実行出来ます:

    lein run -m storm.starter.clj.word-count-0

出力されるログは以下のようになります:

    10033 [Thread-23] INFO  backtype.storm.daemon.task  - Emitting: 1 default ["the man petted the dog"]
    10033 [Thread-22] INFO  backtype.storm.daemon.executor  - Processing received message source: 1:1, stream: default, id: {}, [the man petted the dog]
    10034 [Thread-22] INFO  backtype.storm.daemon.task  - Emitting: 2 default ["the"]
    10034 [Thread-22] INFO  backtype.storm.daemon.task  - Emitting: 2 default ["man"]
    10034 [Thread-22] INFO  backtype.storm.daemon.task  - Emitting: 2 default ["petted"]
    10034 [Thread-22] INFO  backtype.storm.daemon.task  - Emitting: 2 default ["the"]
    10034 [Thread-22] INFO  backtype.storm.daemon.task  - Emitting: 2 default ["dog"]

ID1のSpoutが文字列 "the man petted the dog" を送信し、IDが2のBoltはその文字列を単語ごとに送信しているのが分かります。

#### まとめ

次のセクションではAmazonをつかったデプロイについて紹介しますが、Deploying storm on Amazon Web Service is been going in details in the [storm-deploy wiki](https://github.com/nathanmarz/storm-deploy/wiki) には、StormをAmazon AWSにデプロイする詳細が書かれています。

Stormは色々なキューイングシステムやデータベースと統合出来ます。 Spoutを使うと新しいキューイングシステムにも簡単に統合出来るでしょう。
同様に、データベースとの統合も簡単です。 いつものようにデータベースに接続し、読み書きを行います。 Stormは並列並列化、パーティショニング、エラー時のリトライ処理等行います。

[Trident](https://github.com/nathanmarz/storm/wiki/Trident-tutorial) には、さらに進んだ使い方、Stormを使ったリアルタイム・コンピューティングについて書かれています。

[Storm](http://storm-project.net/) は、Twitter、Taobao、Groupong等でリアルタイムの解析に使われているのを見ても分かる通り、必要に応じて迅速なスケーリングが可能です。
