### リモートライブラリ

###### オードブル（Hors d'oeuvre）:前菜

このセクションでは、プログラマブルなWebの一部として、オンラインにある数は少ないですが面白いAPIを紹介したいと思います。

### FactQL：最強の言語を使って最強のオープンデータプラットフォームにクエリする

[Factual](http://www.factual.com/) を知ってますか？ 世界を変える方法です。 ;)

    Factual は6400万に及ぶ地元のお店や観光スポットとコンテキストをAPIで結びます。

つまり、膨大な量のデータにあっという間にアクセス出来るということですね。

Factualを利用するには、Factualのサイトへ行ってアカウントの登録が必要です。

アカウントの登録が済めば、あとはClojureから [FactQL](https://github.com/dirtyvagabond/factql) を使って膨大な情報にアクセスしましょう。

以下、サンプルです:

@@@ ruby chapter03/src/factql.clj @@@

インターネットにつながっていることを忘れずに！ ぜひ、データを色々と変えて試してみてください。 このレシピには、ビールが合うかも知れませんね。

### 1、2、3でZencoder API

動画の変換が必要なとき、たまにZencoderを使います。

Clojure用の元々の [Zen Client](https://github.com/rcampbell/zenclient) は少し古いのですが、コードは閃く物を感じますし、何より３年経っても動くところが素晴らしい。 私はClojureのそういう所が好きです。 一旦動いたら、動く。 あとは、必要なときに機能を追加するだけ。

では、サンプルに行きましょう。 まずはプロジェクトに依存関係を設定します。

    [hellonico/zenclient "1.2"]

サンプルは30秒で済むチュートリアルです。

@@@ ruby chapter03/src/zenclient.clj @@@

ま、簡単ですね。
コードの中でどのようにAPIを呼び出しているかを見ることがあると思いますが、何かのAPI用Clojureラッパーもけっこう簡単に書けそうですね。

#### Mixpanel, or how to track millions of user events from within your application
#### Mixpanel：膨大なユーザイベントをトラックする

[Mixpanel](https://mixpanel.com/about/) は言っています。 「モバイルとWeb用の最も先進的な解析プラットフォームを提供する」と。

その言葉を抜きにしても、Mixpanelを利用すると訪問者のデータや挙動を迅速に集めることが出来ます。

ここでは、clj-mixpanelという必要最低限のラッパーを使います [clj-mixpanel](https://github.com/pingles/clj-mixpanel) clj-mixpanelを使うための設定は以下の通りです:

    [clj-mixpanel "0.0.1-SNAPSHOT"]

データを生成するためのコードはあり得ないくらいシンプルです:

@@@ ruby chapter03/src/mixpanel.clj @@@

が、たったこれだけで、このコードが実行された情報を収集することが出来ます:

![mixpanel1](../images/chap03/mixpanel1.png)

コードで指定したアクションが収集結果に反映されています:

![mixpanel1](../images/chap03/mixpanel2.png)

もし、ユーザに特化したデータを収集したいのなら、おそらくMixpanelは最も簡単な方法の一つでしょう。

### TodoistでToDoリスト

さて、Webの章もこれでおしまいです。 [clj-todoist](https://github.com/hellonico/clj-todoist) は、[todoist.com](mv ~/Desktop/Screen\ Shot\ 2013-02-27\ at\ 8.50.31\ PM.png) API用のシンプルなラッパーです。

Todoist はオンラインのTodoリストです。 私もここ数年間使っていますが、オンラインなのでいつでもどこからでも利用可能です。

で、いつもはブラウザを使ってこのTodoリストを操作するわけですが、ブラウザを使わなくてもREPLから直接Todoリストにアクセス出来ることに気がついたのです。

まず、プロジェクトに設定しましょう:

    [clj-todoist "1.0.0"]

このサンプルでは、Todoリストに関するいくつかの情報を取得します:

@@@ ruby chapter03/src/todoist.clj @@@

APIの完全なドキュメントは [here](http://todoist.com/API/) にあります:

@@@ ruby chapter03/doc/clj-todoist.core.clj @@@

http clientの内側ではcheshireを使い、その場ですべてのエンドポイントをメソッドとして定義しています。
