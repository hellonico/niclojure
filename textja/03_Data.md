## 科学計算、解析、データベース管理に苦労じゃ、なくてClojure

###### アミューズ・グール（Amuse gueule）: 直訳すると、口の楽しみ。居酒屋のお通しみたいな感じ。

### フラクタル

Clisk はClojureベースの手続き型イメージ生成ライブラリです。
    以下の用途に使用出来ます:

    ゲーム用に2D素材の質感生成
    フラクタルイメージ/アートワークの作成
    レイトレーシングの3D/4D質感生成 (e.g. in Enlight: https://github.com/mikera/enlight)
    パターン作成 (e.g. マップのランダム生成)

Clojarsでの設定:

    [net.mikera/clisk "0.7.0"]

cliskを見つけたのは [blog entry](http://clojurefun.wordpress.com/2012/08/30/mandelbrot-fractals/) で、簡単に数学的なグラフィックを作れるのですぐに気に入りました。

サンプル 1は最初の例としてボロノイ図を描いています。

@@@ ruby chapter03/src/clisk1.clj @@@

![clisk](../images/chap03/clisk1.png)

続いて、チェッカーパターン:

@@@ ruby chapter03/src/clisk2.clj @@@

![clisk](../images/chap03/clisk2.png)

プラズマ的なグラデーション:

@@@ ruby chapter03/src/clisk3.clj @@@
![clisk](../images/chap03/clisk3.png)

そして、有名なマンデルブロ フラクタル

@@@ ruby chapter03/src/clisk4.clj @@@
![clisk](../images/chap03/clisk4.png)

#### 遺伝的アルゴリズム

##### Gajure

Gajure はフルスペックのプロジェクトではありませんが、遺伝的プログラミングに興味があるのであれば大いに参考になるところがあると思います。

未知の値を見つけなければいけないとき、手がかりがその値についての情報を得るためのメソッドのみだとしたら。 遺伝的プログラミングは、そんなニーズに応えるために生まれてきました。
まず、ランダムな値のセットを生成し、それぞれのセットがメソッドにどれだけフィットするかを検証します。 そして、検証の結果からフィットしないものを削除していきます。 また、精度の高い結果を得るためには、膨大な数のセットを検証する必要があります。

ということで、gajureのサンプルです:

@@@ ruby chapter03/src/gajure.clj @@@

一回サンプルを実行したら、入力する値と設定を変えてみましょう。
このプログラムは、例えば次の用途に使えると思います:
- ゲーム ;)
- ネットワークのルーティング
- 時刻表とスケジューリング

#### Incanterで見るモンティ・ホール問題

[Monty Hall with Incanter](http://data-sorcery.org/category/monte-carlo-simulation/)

モンティ・ホール問題とは:

    あなたはバラエティショーに出演しています。
    目の前に3つのドアがあり、1つのドアには車が入っており、他の2つのドアには山羊が入っています。 あなたがドアのうち1つを選択し、もし車が入っていれば当たりですが、山羊が出てきた場合にはハズレです。
    さて、あなたがドアを1つ選び(1とする)、次に司会者が残りの2つのドアのうち、山羊がいるドア(3とする)を開けて山羊を見せます。 そして司会者があなたに「ドア2に変更しますか？」と聞きます。 あなたにドアを変更するメリットはあるでしょうか？

この問題に対する解答も色々あって、面白いのは [YouTube](http://www.youtube.com/watch?v=mhlc7peGlGg)ですが、我々はお気に入りのIncanterライブラリを使ってClojureバージョンを作ってみましょう。

@@@ ruby chapter03/src/monty_hall.clj @@@

結果のイメージはこちら:

![monty hall](../images/chap03/hall.png)

#### 論理プログラミング： Prologはどこへいった？

論理プログラミングは命令型、オブジェクト指向、関数型と並んで４大プログラミングの一つなのだそうです。

[Core Logic](https://github.com/clojure/core.logic/wiki/Examples) came a little bit later in the Clojure game but now offers Prolog-like relational programming, constraint logic programming, and nominal logic programming straight form within Clojure.

The first samples are very easy to go through:

@@@ ruby chapter03/src/logic0.clj @@@

And then you can see the program, just like genetic programming earlier is looking for something where there are just too many possibilities to try by yourself.

Now, logic programming fits perfectly the case of Game AI. See how core.logic will help you to find the path to get the object on the box and then get out. This is superb:

@@@ ruby chapter03/src/logic1.clj @@@

##### Clojush

### MonteCarloでレース。  じゃなくてシミュレーション
#### N-Body シミュレーション
#### 知識表現と推論システム：PowerLoom
#### ルールエンジンが熱い
#### 確率的推論
#### テキストによる機械学習

### SQL
#### マイグレーション自由自在
#### lobosでスキーマ管理
#### Kormaで粋なクエリを書く

### NoSQL
#### Clojure Monger：Mongo クライアント
#### マジめにredisライブラリ
#### マメにメンテナンスされているRabbitMQ クライアント
#### スキーマいらずな FleetDB
#### 組み込みデータベース：cupboard