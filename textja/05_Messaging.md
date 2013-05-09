## メッセージング

読者のみなさんが作るアプリケーションはほとんどの場合スタンドアロン型のアプリケーションではないでしょう。 また、通信する相手も色々なシステムでしょうから、メッセジングについての章を書くことにしました。 私は、Clojureのシステムから、あろうことか銀行のシステムに接続したことがあります。 非常に大きなメッセージだったので、私はメッセージキューがパンクしないことを祈るばかりでした。 が、Clojureは信じられないくらいの柔軟性をもって、「そのメッセージを扱える」ことと「そのタイミングで、しかもリアルタイムに」処理出来ると言うのでした。

ここではメッセージングについていくつかの例をお見せし、その簡潔さ、信頼性、パフォーマンスについてお伝えしたいと思います。

このセクションではRabbitMQ、MemCache、Redis、JMS、...等様々なミドルウェアへの接続について少し詳しく説明していきますので、実際の現場ですぐに使えると思います。

### JMS, AMQP, RabbitMQ用ツールキット：Clamq

[JMS, AMQP/RabbitMQ](https://github.com/sbtourist/clamq) はClojureの世界で最も汎用的なミドルウェアでしょう。 Clamq は最近の有名どころのキューイングシステムをほとんどサポートしていますので、使用するキューイングシステムが変わってもクライアント側のコードを変更すること無く対応することが可能です。

簡単なサンプルを動かすために、おそらく一番手っ取り早く始められる [ActiveMQ](http://activemq.apache.org/download.html) を使用します。
早速、下記の場所からパッケージをダウンロードしましょう:

	http://activemq.apache.org/download.html

あるいは、お使いのパッケージマネージャを使いましょう。
パッケージを展開したら、*bin* フォルダへ移動し、下記のコマンドを実行しましょう:

	./activemq start

システムがスタートしたら、ブラウザを立ち上げて管理ページ [web console](http://activemq.apache.org/web-console.html) にアクセスします:

	http://localhost:8161/admin/

ログインのIDとパスワードは:

	admin / admin

無事ログイン出来れば、次のような画面が表示されます:

![ActiveMQ](../images/chap05/activemq.png)

これでサンプルを動かす準備が出来ました。 Clamq を使用するには、以下の２つの依存関係が必要です:

	[clamq/clamq-jms "0.4"]
	[clamq/clamq-activemq "0.4"]

一つがClamqのコアで、もう一つはActiveMQのエクステンションです。 JMSのプロバイダとして実行することも出来ますが、私はActiveMQがお気に入りです。

サンプルでは、リモートメッセージキューを使ったシンプルなProducer-Consumerテストを書いていますが、ActiveMQで作成しています:

@@@ ruby chapter05/src/clamq.clj @@@

管理コンソールを見ると、コードを実行する前はキューが空っぽなのが分かります:

![Queue](../images/chap05/queue1.png)

そして、上記コードによってメッセージを送った結果です:

![Queue](../images/chap05/queue2.png)

次のサンプルはTopicを使っています。 Topicは同じメッセージを複数の受信者にブロードキャストするのでしたね。

@@@ ruby chapter05/src/clamq_topic.clj @@@

で、先ほどのサンプルと比べてみるとほとんど同じであることに気がつきますね。 実際に違うのは、以下の部分が追加されているだけです:

	{:pubSub true}

ActiveMQ について興味のある方は [ActiveMQ test suite](https://github.com/sbtourist/clamq/blob/master/clamq-activemq/test/clamq/test/activemq_test.clj) と [jms suite](https://github.com/sbtourist/clamq/blob/master/clamq-jms/src/clamq/test/base_jms_test.clj) 見てみると良いでしょう。

### Camelを使った簡単シンプルなメッセージングバス

多機能なオープンソースの統合フレームワークであり、あらゆるプロトコルでどんな相手とでも手軽に接続出来るApache Camelを紹介します。
[Apache Camel from Clojure](https://github.com/denlab/apache-camel-clojure)
[Camel Routes with Clojure](https://github.com/hellonico/clj-camel)

#### ファイルでCamelを使う

最初のサンプルでは、ファイルに接続します。 そう、ファイルです。 あんまり使わない？ でも、コミュニケーションのベーシックを押さえるにはちょうど良いんです。

このチュートリアルでは、clj-camelを使用します:

	[clj-camel "1.0.1"]

REPLを設定したら、ファイルに接続してみましょう:

@@@ ruby chapter05/src/camel_file.clj @@@

まず、Camelのやり方に従って、エンドポイント :from と :to を定義してルートを設定しています。

定義された全てのエンドポイントをリスンするCamelのコンテキストをスタートします。

プログラム的には、:from キューにメッセージを送るとき、*in* として定義したフォルダに新しいファイルが生成されたときにチェックすることができます。

次のステップは、*in*フォルダに作ったファイルが*out*フォルダに出来上がるかどうかをチェックします。 Unixマシンなら以下のコマンドを実行します:

	echo "hello" in/test.txt

Windows環境ならば、テキストエディタで何か "Hello World"的なコメントを入力したテキストファイルを *in* フォルダに作りましょう。 そして、:

![camel](../images/chap05/camel.png)

inフォルダに置いたファイルが*out*フォルダに出来上がっています。

コード中には以下のコメントアウトされた行があります:

	   ; [:to "log:com.mycompany.order?level=INFO"]

この行をコードに戻すと、メッセージがREPLで処理されていることを示すログを見ることが出来ます。

#### キューでCamelを使う

次は、最初のサンプルを拡張して先ほどセットアップしたActiveMQに接続してみましょう。 ActiveMQのエンドポイントはCamelのコアコンポーネントには含まれていませんが、clj-camelの依存関係を設定することで使用可能です。

@@@ ruby chapter05/src/camel_queue.clj @@@

ActiveMQのコンポーネントを登録するための１行を追加しただけですが、:from フォルダにファイルを追加すると、ファイルの中身がメッセージとしてActiveMQに直接送信されます。

Camelにはこれ以外にも様々なコンポーネントがあります: [default components](http://camel.apache.org/components.html) それぞれのURIを使ってアクセスすることが出来ます。

### スケジューリングが必要なときは Quartz

このセクションでは、 [Quartzite](http://clojurequartz.info/articles/guides.html) について手短に紹介したいと思います。 Quartziteは [Clojurewerkz](http://clojurewerkz.org/) にあるライブラリととにかく同じくらい有用です。

ここでの目標は、Quartziteを使ってどのようにスケジューリングするかを簡単に提示し、それより先に進むための手がかりとなるようにすることです。

Quartz は Javaプラットフォームにおけるスケジューラーの先祖のような存在ですが、非常にパワフルな反面、使い方に少し難があります。 Quatziteはmixに Clojure の DSL を加えることにより、ついにClojure アプリケーションに簡単に統合出来るようになりました。

プロジェクトに追加するには:

	[clojurewerkz/quartzite "1.0.1"]

Quartz には関連する下記３つのオブジェクトがあります:

* Job: 実行されるタスク
* Trigger: タスクを実行するトリガー
* Scheduler: 特定の時間にタスクを実行する

以下、サンプルです:

@@@ ruby chapter05/src/quartzite.clj @@@

これで、実績のあるスケジューリングエンジンを使えるようになりました。
上記サイトのドキュメントには、色々なスケジューリングについての記述がありますので、ぜひ試してみてください。

### cronが必要？

何らかのタスクをスケジューリングする必要があるのだけれど、わざわざ複雑なシステムを用意するのもなぁ...と思ったことありませんか？
そんな時には [cronj](https://github.com/zcaudate/cronj) があります。

まず、プロジェクトに追加します:

	[cronj "0.6.1"]

あとは、お馴染みの cron 表記を使って、試しに印刷処理をスケジューリングしてみましょう。 ソースは以下の通りです。

@@@ ruby chapter05/src/cronj.clj @@@

これならウェイターにオーダーするのを忘れる心配がありませんね！

### ClojureのRedis

#### Redisの準備

Key-Valueストアの世界で [Redis](http://redis.io/topics/introduction) の名前を聞いたことがあるかも知れません。 Redisは、単純なKey-Valueのデータだけでなく、文字列、ハッシュ、リストなどをサポートしています。
また、Redisはマスタ・スレーブ型の複製をサポートしています。

Mac OS XにRedisをインストールするには、homebrewを使います:

	brew install redis

他のOSにRedisをインストールするには以下のサイトを参照してください:

	http://redis.io/download

デフォルトの設定でRedisを実行するには、下記コマンドを実行します:

	redis-server

#### コマンドライン・インターフェイス

Redisには *redis-cli* というコマンドラインインターフェイスがありますので、実行してみましょう。

	redis-cli

下記を参考にRedisの動作を確認してみましょう:

	redis 127.0.0.1:6379> ping
	PONG
	redis 127.0.0.1:6379> set mykey somevalue
	OK
	redis 127.0.0.1:6379> get mykey
	"somevalue"
	redis 127.0.0.1:6379>

基本的に全てのコマンドをテストすることができます。 また、helpコマンドもあるので試してみてください。

	help <tab>

#### ClojureからRedisを使う

Redisサーバが動いたので、Clojureプロジェクトから [labs-redis](
[Redis](https://github.com/wallrat/labs-redis-clojure)) でRedisを使う準備をしましょう。 labs-redisをプロジェクトに追加します:

	[labs-redis "0.1.0"]

サンプルは、シンプルに値を保存して取得します。 赤ワインでも飲みながら見てください:

@@@ ruby chapter05/src/redis.clj @@@

コードを見ると、*@*の付いていないメソッドはfutureを返します。 ということは、評価はすぐには行われないということです。 また、値はバイト配列で返されます。 それが、Redisが速い理由の一つです。

次は、これまたシンプルなPublisher/Subscriberのサンプルですが、Redisのリアルタイム性を見るには良い例だと思います。
まずは、Subscriber側のREPL セッションをスタートします:

@@@ ruby chapter05/src/redis_sub.clj @@@

これで、特定のメッセージ、今回は ”msgs”、”msg２”、... を待つクライアントが開始されます。

次に、publisher側のREPLをスタートします:

@@@ ruby chapter05/src/redis_pub.clj @@@

すべてがうまく行けば、subscriber側には以下のように表示されます:

	user=> (load-file "src/redis_sub.clj")
	R  msgs hello 0
	R  msgs hello 1
	R  msgs hello 2
	R  msgs hello 3
	R  msgs hello 4

メッセージがきちんと受信されていますね。

#### もう一つのクライアント

Redisのクライアントとして忘れてはいけない [Carmine](https://github.com/ptaoussanis/carmine) があります。

基本的な使い方は、他のクライアントと比べると少し複雑ですが、その分しっかりとしています。

プロジェクトへの追加は以下の通りです:

	[com.taoensso/carmine "1.6.0"]

例によって、pingに応答するサンプルです:

@@@ ruby chapter05/src/carmine.clj @@@

あとは、オンラインのドキュメントも大変充実しています。 [carmine documentation](https://github.com/ptaoussanis/carmine)

私のお気に入りは、特に [Message Queue](https://github.com/ptaoussanis/carmine#message-queue) です。 非常に明瞭簡潔です。

#### Redisが解決すること

Redis を選択する上でキーとなる特徴がいくつかあります。 例えば:

* キャッシング： もし、複数の場所にデータを保存したい場合にはRedisを選ぶ最も明確な理由となるでしょう。
* キュー: 値をとりあえず受け取り、あとで処理する。
* Publisher/Subscriber: でーたが変更されたことを通知するクライアントが複数存在する。
* 重複しない項目： Redisにはセットに項目を重複せずに追加するコマンドがあり、これを利用すると簡単に現在のユーザ数をチェックしたりすることが出来ます。

など..

以前、多数のクライアントから送信される大量のメッセージをRedisのキューに溜めておき、後からそれらのメッセージを処理するというシステムを構築したことがありますが、Redisは非常にうまく機能しました。

### zookeeper と avout でアプリケーションの状態を配信する

Clojureには、トランザクションを管理するのに [powerful Multiversion Concurrency Control](http://en.wikipedia.org/wiki/Multiversion_concurrency_control) (MVCC) [STM](http://en.wikipedia.org/wiki/Software_transactional_memory) があります。

このサンプルでは、Zookeeperに保存された共有リファレンスを紹介します。

	ZooKeeper は設定情報、名前情報、分散同期やグループサービスの提供をメンテナンスする集中型のサービスです。

#### Zookeeperのインストールと実行

Zookeeperを実行するまでの手順にはいくつかありますが、ここでは一番手っ取り早い方法を挙げておきます。

[official page](http://zookeeper.apache.org/) からパッケージをダウンロードします。
原稿執筆時点では、バージョン 3.4.5 が最新です。

パッケージを展開したら、*conf*ディレクトリにある*zoo_sample.cfg*ファイルを*zoo.cfg*にリネームします。
デフォルトの設定をそのまま使用します。

準備が出来たら、展開したフォルダで、以下のように実行します:

	./bin/zkServer.sh start-foreground

Windowsの場合には、以下の.cmdファイルを実行します:

	bin¥zkServer.cmd start-foreground

すると、zookeeperサーバが起動し、以下のようなメッセージが表示されます。

	[Niko@Modrzyks-MacBook-Pro][17:53][~/Downloads/zookeeper-3.4.5/] % ./bin/zkServer.sh start-foreground
	JMX enabled by default
	Using config: /Users/Niko/Downloads/zookeeper-3.4.5/bin/../conf/zoo.cfg
	2013-04-02 17:53:15,411 [myid:] - INFO  [main:QuorumPeerConfig@101] - Reading configuration from: /Users/Niko/Downloads/zookeeper-3.4.5/bin/../conf/zoo.cfg
	...
	2013-04-02 17:53:15,462 [myid:] - INFO  [main:ZooKeeperServer@744] - maxSessionTimeout set to -1
	2013-04-02 17:53:15,473 [myid:] - INFO  [main:NIOServerCnxnFactory@94] - binding to port 0.0.0.0/0.0.0.0:2181

#### 動物たちと遊ぶ

[Avout](https://github.com/AlexBaranosky/avout) はRelevanceの人たちによって作られました。
彼らは全ての [proper documentation](http://avout.io/index.html#background) と基本原理について書いていますので、ぜひ一読されることをお勧めします。 ClojureとSTM(Software Transactional Memory)について説明しています。

avoutの依存関係をアップデートしているので、プロジェクトにはカスタマイズされたバージョンを追加します:

	[hellonico/avout "0.5.4"]

サンプルは２つのパートに分かれており、一つは分散リファレンスをセットアップします。

@@@ ruby chapter05/src/avout_1.clj @@@

もう一つは、分散システムから値を受け取ります。

@@@ ruby chapter05/src/avout_2.clj @@@

ここが面白いところで、他のClojureアプリケーションとの間でリファレンスを共有するのにほとんどセットアップの必要がないんです。
例えば、これを使ってシステムの状態を知らせるようにすれば、他のモジュールはシステムの状態によって違ったアクションを取るような仕組みを作ることができますね。 システムの実行状態を運用モード、復旧モード、テストモードとかに分けて、それごとにモジュールの動作を変えるとか。

#### 分散ロックを使う

Avoutには信頼性の高い分散ロックが実装されています。 この前のサンプルでは、バックグラウンドでzookeeperを動かしておく必要がありましたが、コードの中では特にそれを意識することもありませんでした。

それでは、次のサンプルを実行してみてください:

@@@ ruby chapter05/src/avout_lock.clj @@@

コードを見ると、*if-lock*を使ってlockが成功したかどうかを判定し、処理を変えています。

試しに、このコードを別々のREPLで実行してみると、それぞれ違う出力が得られると思います:

一つ目は、ロックして少し待ちます...

	user=> (load-file "src/avout_lock.clj")
	Have the lock
	Release lock

２つ目は、lockに失敗します。

	user=> (load-file "src/avout_lock.clj")
	Could not get the lock

このレシピは、ロック機構を持たないシステムでデータを共有するような場合に使えそうですね。

### 望遠鏡(Spyglass)なしでどこ行くの？

memcacheは、最近ではFacebook等多くの会社で標準的なキャッシング方式となったので、あまり詳細について語る必要もないかも知れません。
キャッシングを使う理由は、一度計算したデータを再利用することで負荷の軽減やパフォーマンスの向上が見込めることでしょうか。
いずれにせよ、まずやってみましょう。

#### 準備

memcachedをMac OS Xにインストールして実行するには、以下の２つのコマンドで済みます:

インストールする:

	brew install memcached

起動する:

	memcached

Windowsの場合は、以下の場所にあるカスタムビルドを使いましょう:

	http://www.urielkatz.com/archive/detail/memcached-64-bit-windows/

同じように起動しましょう。

#### Clojure する

memcachedに接続するなら、[Spyglass](http://clojurememcached.info/articles/getting_started.html) が良いです。 プロジェクトに追加しましょう:

	[clojurewerkz/spyglass "1.1.0-beta3"]

これで、REPLで使えるようになりました。

##### 時間制限付きキーのインサートと取得

最初のサンプルは、memcachedに対する値のインサートと取得の基本部分です:

@@@ ruby chapter05/src/spyglass_text.clj @@@

一番難しいのは、spyglassクライアントをインクルードするところでしょうか:

	clojurewerkz.spyglass.client

残りの部分は容易に読めると思います。 制限時間付きのキーを設定し、時間が経過した後にそのキーを取得しています。

##### Distributed Binaries cached key

次の２つはmemcachedを分散させたサンプルです。 ２つのmemcachedサーバに接続することで、ちょっと複雑なことをやってみます。 もう一つのmemcachedサーバを別のポート(11212)で起動しましょう:

	memcached -p 11212

それぞれのサンプルは、１番目と２番目の両方のサーバに接続します。
２つのサーバに接続するためのURLは以下の通りです:

	127.0.0.1:11211 127.0.0.1:11212

一つ目のサンプルは、サーバに接続し、バイナリキーを保存して取得します。 バイナリはシンプルなClojureの配列です:

@@@ ruby chapter05/src/spyglass_binary.clj @@@

２つ目のサンプルは、サーバに接続してバイナリキーを取得します:

@@@ ruby chapter05/src/spyglass_binary_2.clj @@@

やってみると分かりますが、片方のサーバを落としてももう一つのサーバに接続して値を取得することができます。

##### キャッシングについて

キャッシングの中で、タイミングは非常に難しい問題です。 例えば10000ものクライアントから同時に接続があるときに、どのタイミングでキャッシュを期限切れにしたら良いでしょうか？

ここに興味深い２通りのアプローチがあります。

	キャッシュするアイテムの有効期限をずっと先にセットし、連続した"実際"のタイムアウト値を埋め込む。 例えば、アイテムのタイムアウトは24時間にセットするが、埋め込まれたタイムアウト値は5分かも知れない。
	キャッシュからgetするときにタイムアウトしたかどうかを判断し、タイムアウトしてたらタイムアウト値を再設定し、データをリストアする。 これにより、リスクを低減出来る。

	Alexey は２つのキーを使った別のアプローチについて記述しています。
	memcachedに２つのキーを作成する。 MAINのキーは通常よりも少し長い有効期限をセットし、もう一つのSTALEキーにはそれよりも短い期限をセットする。
	getしたときにSTALEキーを一緒に読み込み、もしその時間よりも経過していればキャッシュを更新しSTALEキーを再設定する。

これがmemcachedを始めるにあたって把握しておくべきことでしょうか。

### SIP を呼ぶ、クロージャーを呼ぶ

ここでは、SIPプロトコルについてサラっと紹介します。 が、それでも興味をそそる内容だと思います。

[SIP Library for clojure](https://github.com/Ruiyun/cljain) は、SIPに関するほとんどのことをしてくます。
SIP LibraryはJVMで有名なJAIN-SIPを基にビルドされましたが、セットアップも使い方もより簡単になっています。

このレシピでは、ボブが愛しのアリスとSIPメッセージでやり取りをします。 このメッセージはUDPプロトコルで通信されます。 ２人の間のメッセージは当初テキストでやり取りされていました。 が、それだけでは飽き足らず、音声もやり取りしたいと言い出し、あなたはそれを手助けすることになりました。

では始めましょう。 まずは依存関係を追加します:

	[cljain "0.4.0"]

サンプルは２つのパートに分かれています。 一つはボブ、そしてもう一つがアリス用のコードです。

最初にボブの電話をセットアップしましょう。 ボブは自分自身にメッセージを送り、コネクションが正しく確立されることを確認します:

@@@ ruby chapter05/src/sip_bob.clj @@@

次に、アリスがボブにメッセージを送り、ボブがそれを取得し、返事をします:

@@@ ruby chapter05/src/sip_bob.clj @@@

さて、問題の音声です。 実は、メディアデータもそのままメッセージとして送信出来るんです。 なので、音声の送信はぜひご自分でやってみてください。

残念ながら、ワインをやり取りすることは出来ません。。

### Apache Cassandra

#### 背景

	商用ハードウェアあるいはクラウドのインフラにおけるApache Cassandraのスケーラビリティとフォールトトレランスはミッションクリティカルなデータにとってパーフェクトなプラットフォームです。

	フォールトトレランスのために、データは自動的に複数のノードに複製されます。 複数のデータセンターに跨がる複製がサポートされており、障害のあるノードはダウンタイム無しに置き換えることが可能です。

	Cassandra は、Netflix、eBay、Twitter、Urban Airship、Constant Contact、Reddit、Cisco、OpenX、Digg、CloudKick、 Ooyala、その他巨大でアクティブなデータを持つ企業で採用されています。 最も大きなCassandraのクラスタと知られているものは、400台を超えるマシンを使って300TB以上のデータを処理しています。

	単独で障害を起こしている箇所はありません。 ネットワークのボトルネックもありません。 クラスタの全てのノードは同一です。

DataStaxのCTOは [presents Cassandra](http://devopsangle.com/2012/08/09/datastax-cto-says-cassandra-is-for-practical-problem-solvers/) で、実際の問題を打開するための解決策と述べています。

Cassandraは、個人的には、複数のマシン、ネットワークに跨がったデータの一貫性とセットアップの容易さに非常に魅力を感じています。

#### 準備

CassandraをMac OS Xにインストールするには、brewを使用して:

	 brew install cassandra

以下のサイトには、色々なプラットフォーム用にDataStaxによるプリビルド・パッケージがあります:

	http://planetcassandra.org/Download/DataStaxCommunityEdition

インストールが完了すれば、以下のコマンドで単一のノードクラスタを開始することができます:

	cassandra

#### 基本の操作

ここでは本当に本当にベーシックに、Cassandraでスキーマを作って、データを保存して、データを取得してみます。

[clj-hector](https://github.com/pingles/clj-hector) をプロジェクトに追加します:

	[org.clojars.paul/clj-hector "0.2.8"]

サンプルは、先ほど作成したクラスタに接続し、スキーマを定義します。

@@@ ruby chapter05/src/hector.clj @@@

get-rows [ks cf pks & options] は少し暗号的ですが、おおよその意味は:

	 "キースペースksの中で、カラムファミリーがcfであるpksの行を取得する"

また、値をどのようにシリアライズするかの指定も必要です。

これでCassandraの基本を押さえたら、次はぜひ [test suite](https://github.com/pingles/clj-hector/blob/master/src/clj_hector/core.clj) と [Cassandra wiki](http://wiki.apache.org/cassandra/GettingStarted) を参照して理解を深めてください。

Facebookのようなビッグデータ企業が採用するテクノロジーをセットアップして使ってみるってワクワクしますよね。 皆さんにとって、そういうきっかけになったら幸いです。
