## Clojureでお手軽、チョー楽しいWeb開発

###### プラ（Plat） ポワソン:メインのお魚料理

### Lord of the Ring：Web開発の最強パートナー

[Ring](https://github.com/ring-clojure/ring) はライブラリというか、RubyのRackに触発されたライブラリセットです。

Ring はローレベルのフレームワークなのでインポートして使いたいものだけ使うのですが、逆に言うとキチっとコンセプトを理解する必要があるということです。

#### コンセプト
Ringアプリケーションのコア・コンポーネント:

##### ハンドラ

Ringハンドラはインプットのリクエストマップを受け取り、レスポンスマップを返すClojure関数です。 シンプルな例を挙げると:

    (defn what-is-my-ip [request]
      {:status 200
       :headers {"Content-Type" "text/plain"}
       :body (:remote-addr request)})

##### リクエスト

Ringリクエストはシンプルなマップです。 キーには以下のものがあります:

* :server-port リクエストを処理するポート
* :server-name 解決されたサーバ名、またはIPアドレス
* :remote-addr クライアントのIPアドレス、またはリクエストを送信した最終のプロキシ
* :uri リクエストURI
* :query-string クエリ文字列(もしあれば)
* :scheme トランスポート・プロトコル http または https.
* :request-method HTTP リクエストメソッド :get、:head、:options、:put、:post、:delete のいずれか
* :content-type リクエストボディのMIMEタイプ (分かっている場合)
* :content-length リクエストボディのバイト数 (分かっている場合)
* :character-encoding エンコーディング (分かっている場合)
* :headers ヘッダ値に一致する小文字のヘッダ名文字列の Clojureマップ
* :body リクエストボディ (存在する場合)

##### レスポンス

Ring レスポンスは３つのパラメータで構成されるマップです:

* :status HTTP ステータスコード  例：200、302、404、...
* :headers HTTPヘッダ名のClojureマップ
* :body レスポンスボディ  下記のいずれか:
** 文字列
** ISeq
** ファイル
** 入力ストリーム

##### ミドルウェア

ミドルウェアは少なくとも一つのハンドラを受け取り、ハンドラとして振る舞う関数を返す高レベルの関数です。 例えば、Ringではパラメータ、セッション、ファイルのアップロードはミドルウェアによって処理されます。

#### Ring の世界

お決まりの HelloWorld サンプルを動かすために、Ringをプロジェクトに追加します:

    [ring "1.1.8"]

そして、リクエストを処理するためのRingハンドラが必要ですね。

@@@ ruby chapter04/src/chapter04/core.clj @@@

ここで、HTTPレイヤーの処理が必要ですが、サンプルではJVMで動くWebサーバとしては大変信頼性のあるJettyを使っています。 Jettyは*ring*パッケージに含まれてるので、インストールや依存関係の設定をする必要はありません。

以下のコードでサーバをスタートします:

@@@ ruby chapter04/src/chapter04/jetty.clj @@@

[http://localhost:3000](http://localhost:3000) にアクセスしてみましょう。 Ringの世界にようこそ！

#### Ring の世界 2: leiningen を使う

さて、WebアプリケーションをいちいちREPLから起動するのもちょっと面倒ですね。 そんな大げさなことをしなくても、もっと手軽に出来ないものでしょうか？

project.clj ファイルに以下の２行を追加しましょう:

      :plugins [[lein-ring "0.8.3"]]
      :ring {:handler chapter04.core/handler}

これで何が変わるかというと、[lein-ring](https://github.com/weavejester/lein-ring)というleiningenのプラグインが使えるようになり、２行目では呼び出されるハンドラを指定しています。

では、ウェイター、、、ではなくサーバを呼びましょう:

    lein ring server

以下のように表示されたでしょうか？

![ring](../images/chap04/ring1.png)

コードを変更したときにWebアプリケーションを再起動する必要の無いことに注意してください。 ハンドラのコードを変更したら、すぐにそれが反映されますね！

アジャイル・ワインとでも呼びましょうか。

#### Compojure による HTTPルーティング

##### セレブにルーティング

Webアプリケーションの基本は分かりました。 って言っても、毎回HelloWorldばかりを見ているわけにも行かないので、もう少しセクシーなアプリケーションにしましょう。

まずは、[Compojure](https://github.com/weavejester/compojure) を使ってサンプルをやってみましょう [sample app](https://github.com/weavejester/compojure-example).

依存関係のリストを見ると Compojure はRingに含まれていますが、念のため、project.cljに以下の設定をしておきましょう:

    [compojure "1.1.5"]
    [hiccup "1.0.2"]

第1章で使った hiccup も設定に加えています:

@@@ ruby chapter04_01/project.clj @@@

このアプリケーションには、ルーティングの定義をするファイルとビューの定義をする２つのファイルがあります。

まず、ルーティング部分です:

@@@ ruby chapter04_01/src/compojure/example/routes.clj @@@

ルーティングは、４つのパートに別れた行ベースのDSLで記述されています:

    (GET "/" [] (index-page))

GET : 最初のパートはHTTPのメソッドで、この場合はGETです。 ANYはすべてに合致します。

"/" : ルートパス ２番目のサンプルでは、"/user/:id"というパターンを使ってパラメータを受け取っています。

[] : リクエストマップからの情報を受け取る配列

(index-page) : 第1章で見たようにhiccupがhtmlを生成するためのシンプルなClojure関数。 関数の実際の中身はこんな感じ:

@@@ ruby chapter04_01/src/compojure/example/views.clj @@@

これで準備ができました。 先ほどと同じメソッドを使ってサーバをスタートしましょう:

    lein ring server

さて、ルーティングが出来たとなると、次はJSONを使ったWebサービスでしょうか？ ここではやりませんが、ぜひチャレンジしてみてください。 以前に使ったライブラリ *cheshire* をインクルードして、レスポンスの中で適切なcontent-typeをセットし、JSONマップを返すようにすれば出来ると思います。

##### Clojureのデータ分解機能について

良い機会なので、一連のデータから必要なものだけにアクセスするClojureの*destructuring*について説明しましょう。

ルーティングDSLの２番目のパラメータ、[]を思い出してください。

    (GET "/" [] (index-page))

例えば、訪問者のIPアドレスを取得したいとします。 その場合、リクエスト自体のマップのサブセットを作成する分解フォームとして２番目のパラメータを使うことが出来ます。 少し前に、リクエストはシンプルなマップであると説明しました。 上記のサンプルをちょこっと変更して:

    (GET "/yourip" {ip :remote-addr} (str "Your IP is:" ip))

元々のリクエストマップから、:remote-addrパラメータを受け取ります。 でも、待って。 元々のリクエストマップって何でしたっけ？ これです:

@@@ ruby chapter04_01/resources/public/request-sample.json @@@

Ringサーバをスタートしているのであれば、以下のアドレスに直接アクセスして見ることもできます:

    http://localhost:3000/request-sample.json

次は、セキュリティ関連です。

#### Friend: Facebookの友達を認証する

[Friend](https://github.com/cemerick/friend) はシンプルさを以てClojureにポートされたRackのwarden、JavaのSpring Securityです。 これを使って、お友達をたくさん作って世の中をもっと良くしましょう。

最近のWebアプリケーションの傾向として、サービスにいちいち個別にログインするのではなく、GoogleやTwitter等他のサービスのログイン情報を再利用しています。 このレシピのゴールは、Facebookにログインした後で、Facebookのログイン情報を受け取ることです。

[friend oauth](https://github.com/ddellacosta/friend-oauth2) が正にそれをやってくれます。 簡単なコンフィギュレーションを通して、oauthを再作成します。

いくつかのライブラリをインポートして、サンプルです:

@@@ ruby chapter04_02/src/friend_oauth2_examples/facebook_handler.clj @@@

アプリケーションのコンフィギュレーション自体はシンプルなマップです:

    (def client-config
      {:client-id "233100973389749"
       :client-secret "0772afb21ddf0d0fab8200c1ff707319"
       :callback {:domain "http://localhost:3000" :path "/facebook.callback"}})

コンフィギュレーションの中身は、[Facebook developer page](https://developers.facebook.com/) のアプリケーション設定に合わせてください。

以下のコマンドでRingハンドラをスタートします。

    lein ring server

以下のアドレスにアクセスすると、

    http://localhost:3000/authlink

oauthのワークフローが開始され、Facebookのログインページにリダイレクトされます:

![Facebook](../images/chap04/facebook.png)

ログインに成功すると、FacebookはローカルのRingサーバの最初にポイントしたロケーションにリダイレクトします。 シンプルですね。

サンプルはgithubと.Net版があるので宿題としてやってみてください。

#### フォーム、バリデーション、バウンス

このレシピでは、どのようにフィールドのバリデーションを行うかについて見てみましょう。

Clojureでは、マップとコレクション上でバリデーションを行うのが簡単です。 おしゃれなナイトクラブでは、厳ついお兄さんのバリデーションに引っかかるとお店に入れないわけですが、そこにはきっと「サンダル厳禁」とか「泥酔してたら駄目」とかバリデーションのルールというものがあるのでしょう。 Bouncerも与えられたルールに沿ってバリデーションを行います。

project.clj に追加する設定は以下の通りです:

    [bouncer "0.2.3-beta1"]

サンプルでは以下のことを行っています:

* シンプルなルールでハッシュマップを検証
* バリデータを定義し、それを使ってハッシュマップを検証
* 最後に、正規表現のマッチングに加えて、サブコレクションの検証

@@@ ruby chapter03/src/validation.clj @@@

データセットが無事検証されれば最初の要素はnilとなり、検証に失敗すればパラメータのマップが返されます。

#### 世界のJSON

今どきはJSON抜きではWebサービスを語れない、っていうか作れないですよね。 かつてはリモートサービスを利用するのがあれこれ面倒だったこともありますが、今では [ring-json](https://github.com/ring-clojure/ring-json) が全てのお膳立てをしてくれます。

いつものようにプロジェクトに追加します:

    [ring/ring-json "0.2.0"]

以下の通り、これだけのコードで何もしなくてもClojureのデータ構造を適切なJSONに変換してくれます:

@@@ ruby chapter04_03/src/chapter04_03/core.clj @@@

コードでは、まずネームスペースにRingのミドルウェアをインポートし、*wrap-json-response*というメソッドをRIngのハンドラに設定しています。 それだけです。

[http://localhost:3000](http://localhost:3000) のサーバにアクセスすると、フォーマットされたJSONコードが返されます。

２番目のサンプルでは、サービスにJSONのデータをポストし、直接Clojureのデータ構造を取得します。

サーバサイドのコードも決して複雑ではありません:

@@@ ruby chapter04_03/src/chapter04_03/core2.clj @@@

以下のように、コマンドラインでCurlを使ってサービスにリクエストを送ります:

    curl -v \
        -H "Accept: application/json" \
        -H "Content-type: application/json" \
        -X POST \
        -d '{"user":"Clojure"}'  \
        http://localhost:3000

次のJSONが:

    {"user":"Clojure"}

ミドルウェアのwrap-json-bodyによって以下のように変換されます:

    {"user" "Clojure"}

Merci Monsieur !

#### Ringの世界 3: スタンドアロン・サーバ

上記で見た lein-ring コマンドを使うことで、スタンドアロンのカスタマーWebサーバを生成することができます！
以下のように実行します:

    lein ring uberjar

これで関連するたくさんのインストールが始まります:

    Created /Users/Niko/projects/mascarpone/chapter04/target/chapter04-0.1.0-SNAPSHOT.jar
    Including chapter04-0.1.0-SNAPSHOT.jar
    Including java.classpath-0.2.0.jar
    Including ring-refresh-0.1.2.jar
    Including clj-stacktrace-0.2.5.jar
    Including servlet-api-2.5.jar
    Including ns-tracker-0.2.1.jar
    Including javax.servlet-2.5.0.v201103041518.jar
    Including ring-servlet-1.1.8.jar
    Including ring-server-0.2.8.jar
    Including clojure-1.5.0-RC3.jar
    Including clout-1.0.1.jar
    Including core.incubator-0.1.0.jar
    Including ring-1.1.8.jar
    Including tools.macro-0.1.0.jar
    Including commons-io-2.1.jar
    Including ring-devel-1.1.8.jar
    Including clj-time-0.3.7.jar
    Including hiccup-1.0.0.jar
    Including watchtower-0.1.1.jar
    Including ring-core-1.1.8.jar
    Including jetty-http-7.6.1.v20120215.jar
    Including jetty-util-7.6.1.v20120215.jar
    Including ring-jetty-adapter-1.1.8.jar
    Including commons-codec-1.6.jar
    Including joda-time-2.0.jar
    Including compojure-1.1.5.jar
    Including jetty-server-7.6.1.v20120215.jar
    Including jetty-io-7.6.1.v20120215.jar
    Including tools.namespace-0.1.3.jar
    Including commons-fileupload-1.2.1.jar
    Including jetty-continuation-7.6.1.v20120215.jar
    Created /Users/Niko/projects/mascarpone/chapter04/target/chapter04-0.1.0-SNAPSHOT-standalone.jar

少し前にやった *uberjar* を覚えてますか？ これは *uberjar* のRingバージョンです。 これで、簡単にデプロイが出来て、誰でも自分のマシン上でこのサーバを実行することが出来ます。

通常のやり方で実行します:

    java -jar target/chapter04-0.1.0-SNAPSHOT-standalone.jar

#### Ringの世界 4: いつものWebサーバにデプロイする

パッケージと言えば、Javaの世界にはもう一つ *web archive* あるいは war ファイルがありますね。

では、war専門のウェイターを呼んでみましょう:

    lein ring uberwar

彼のレスポンスは:

    Created /Users/Niko/projects/mascarpone/chapter04/target/chapter04-0.1.0-SNAPSHOT-standalone.war

ということで、いつものJava Webサーバのdeploymentディレクトリにコピーするためのwarファイルが出来上がりました。

##### Apache Tomcat 7

JBossは多機能でデプロイのオプションも豊富にあるので、この次のレシピはちょっと長いのですが、その前にさらっとApache Tomcatの話を済ませましょう。

Apache Tomcat はJVMベースのサーバデプロイメントをもう長いことサポートしています。
むかしむかし、ブドウがまだまだ若かった頃、本番システムの運用にTomcat 3を紹介したことがあります。 メモリの使用効率が良く、多くのコネクションを処理出来、その頃の有償のサーバに比べても遜色無く、コストを節約することが出来ました。

[download Tomcat](http://tomcat.apache.org/download-70.cgi) からダウンロードして解凍します。 サーバのスタートは:

    ./bin/startup.sh

![tomcat](../images/chap04/tomcat1.png)

Tomcatをインストールしたフォルダにあるwebappフォルダに先ほどのwarファイルをコピーし、アクセスしてみましょう:

    http://localhost:8080/chapter04-0.1.0-SNAPSHOT-standalone/

まだ時間通りですね！

Tomcatは後でまた使いますので、環境を残しておきましょう。

##### JBoss AS 7

数年前は、ちょっと進んだことをやっているところはどこでもJBossを使っていました。 たくさんの友人がクールなプロジェクトに関わっていて、JBossに数ステップでwarファイルをセットアップ出来たら彼らの役に立つかな？なんて考えていました。

[projects page](http://www.jboss.org/projects) か [direct download page](http://www.jboss.org/jbossas/downloads/) からサーバをダウンロード出来ます。

ダウンロードした中身は、以下のような感じです:

![jboss1](../images/chap04/jboss1.png)

この本はJBossの本ではないので、JBossの詳細については専門の書籍かJBossのドキュメントを参照してください。

JBossの管理コンソール使うためにはユーザを追加する必要があります。 binフォルダにある*add_user.sh*というスクリプトを使ってユーザを作りましょう。

After a bit of old fashioned script interaction:

    [Niko@Modrzyks-MacBook-Pro][17:58][~/Downloads/jboss-as-7.1.1.Final/] % ./bin/add-user.sh

    What type of user do you wish to add?
     a) Management User (mgmt-users.properties)
     b) Application User (application-users.properties)
    (a):

    Enter the details of the new user to add.
    Realm (ManagementRealm) :
    Username : clojure
    Password :
    Re-enter Password :
    About to add user 'clojure' for realm 'ManagementRealm'
    Is this correct yes/no? yes
    Added user 'clojure' to file '/Users/Niko/Downloads/jboss-as-7.1.1.Final/standalone/configuration/mgmt-users.properties'
    Added user 'clojure' to file '/Users/Niko/Downloads/jboss-as-7.1.1.Final/domain/configuration/mgmt-users.properties'

We are almost ready to hit the admin console through the browser. But, first let's start the server:

    ./bin/standalone.sh

And now we can head to:

    http://127.0.0.1:9990/console/App.html#server-overview

It will greets you with a desperate need for authentication...

![jboss2](../images/chap04/jboss2.png)

That comes just in time, because we have just registered a user a few seconds ago didn't we ?
Let's use the same user and login.

![jboss3](../images/chap04/jboss3.png)

And following the few shots below we can upload our war file to jboss.

![jboss4](../images/chap04/jboss4.png)
![jboss5](../images/chap04/jboss5.png)

And lastly enable the application context:

![jboss6](../images/chap04/jboss6.png)

Our application is located in a default context taken from the name of the war file, so we would go to:

    http://localhost:8080/chapter04-0.1.0-SNAPSHOT-standalone/

To see our wonderful time application ! The time has changed. And wine has probably come.

What that means in simple terms is that a full on Java shop can now be a full Clojure shop without anyone complaining much about new powerful and simple language being pushed to production. There is simply no bad aspect of this deployment, ring and roll.

### 何でもかんでもNoirなワケじゃないけど、数行のコードでWebサイトが出来上がる
### 面倒な作業はVaadinとかGoogle Web Toolkitにお任せ
#### 初めての Vaadin アプリケーション
### Compojure はクールなWebフレームワーク
### AlephのおかげでWebソケットはとてもシンプルになりました
### RESTful なアプリケーションは今や当たり前