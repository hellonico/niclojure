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

And look at the basic pong example at:

@@@ ruby chapter05/src/carmine.clj @@@

The rest is up to read the excellent [carmine documentation](https://github.com/ptaoussanis/carmine) available online.

We particularly like the [Message Queue](https://github.com/ptaoussanis/carmine#message-queue) section. It simple and concise !

#### What does Redis solve ?

Redis has a number of common key IT usages, such as:

* Caching, is the most obvious choice, when you want to store something that should be computed somewhere and be made available to a range of different machines.
* Queues, when you just want to receive values and handle them later on.
* Pub/Sub, when you want to have multiple clients aware of some data change
* Unique N Items, Redis has a direct command to uniquely add to sets, so it is easy to know the number of concurrent users at a given time.
* Counting, more generally, keeping stats of different kinds is an easy and recurrent pattern usage for Redis.

etc..

And way more.
In the past, we have used Redis to enqueue messages coming from many concurrent clients as fast as possible and allow the processing to be done later on by another system, which proved to work quite well.

## RabbitMQだったら宇宙とだってつながる、、、かな。 Hadoopもあるよ
###### プラ（Plat） ヴィヤンド:メインのお肉料理

### JBoss のルールインテグレーションを簡単に行う
### 馬じゃなくて、らくだ(Camel) に乗ってみる？
#### Camel ルート
### クロージャーで Redis
### zookeeper と avout でアプリケーションの状態を配信する
### そろそろ、話題の Hadoop の話をしようか
### Cascalog を使った Hadoop クエリ
### Apple のプッシュ通知
### spyglass でキャッシュサーバ
### SIP を呼ぶ、クロージャーを呼ぶ
### Apache Cassandra
### Scala のアクターをClojureで
### Apache Thrift を使う。 Facebook をクロージャーから
### Storm：リアルタイム分散コンピューティング
### Zookeeper と RabbitMQ を使った Actors ライブラリ