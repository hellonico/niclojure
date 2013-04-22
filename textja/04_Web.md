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

### 何でもかんでもNoirなワケじゃないけど、数行のコードでWebサイトが出来上がる
### 面倒な作業はVaadinとかGoogle Web Toolkitにお任せ
#### 初めての Vaadin アプリケーション
### Compojure はクールなWebフレームワーク
### AlephのおかげでWebソケットはとてもシンプルになりました
### RESTful なアプリケーションは今や当たり前