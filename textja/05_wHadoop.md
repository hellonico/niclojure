### Clojureで使いやすいHBase

#### 準備

Hadoopについて深く掘り下げる前に、ClojureからどのようにHBaseにアクセスするのかを見てみましょう。

最初のhbaseのレシピでは、スキーマを定義してデータをストアし、読み出します。

現在私が関わっているプロジェクトでは、たくさんのユーザによって同時に発生させられるイベントを処理し、その結果出来上がる大量のデータを色々な時間軸で参照しています。

hbaseのインストールは、普段お使いのパッケージマネージャを使っても良いですし、または [download it](http://www.apache.org/dyn/closer.cgi/hbase/) からダウンロードします。

Mac OS Xなら、シンプルにbrewを使って:

    brew install hbase

インストールが完了したら、マスターノードをスタートしましょう:

    hbase master start

確実にどうしているかどうかを確認するために、ブラウザで以下のURLにアクセスしてみましょう:

    http://localhost:60010/master-status

![hbase UI](../images/chap05/hbase.png)

#### Clojuring hbase

それでは、[clojure-hbase](https://github.com/davidsantiago/clojure-hbase) を使って、スキーマの操作とデータの書き込みをしてみましょう。

プロジェクトに追加するには、以下の２行を追加します:

    [org.clojure/clojure "1.4.0"]
    [clojure-hbase "0.92.1"]

※ 原稿執筆時点では、Clojure 1.5.1はまだサポートされていないようです。

では、早速簡単なスキーマを定義してみましょう:

@@@ ruby chapter05_hadoop/hbase/src/hbase/setup.clj @@@

ひとまず、一つのテーブルと一つの列ファミリだけです。
ちなみに、スキーマに列ファミリを追加する時は、テーブルをディセーブルにする必要があります。

次に、ベーシックなCRUD操作をしてみましょう:

@@@ ruby chapter05_hadoop/hbase/src/hbase/core.clj @@@

hbaseはバイト列を扱うので、ここでは結果のデータを簡単に見るための以下の２つのメソッドを使っています。

* vectorize
* keywordize

次のサンプルは、直接結果のマップを見るサンプルです:

@@@ ruby chapter05_hadoop/hbase/src/hbase/core2.clj @@@

hbaseから結果セットを受け取るのにsキャナーを使用しています。 フィルタのための引数はこのリストにあります:

    (def ^{:private true} scan-argnums
      {:column       1    ;; :column [:family-name :qualifier]
       :columns      1    ;; :columns [:family-name [:qual1 :qual2...]...]
       :family       1    ;; :family :family-name
       :families     1    ;; :families [:family1 :family2 ...]
       :filter       1    ;; :filter <a filter you've made>
       :all-versions 0    ;; :all-versions
       :max-versions 1    ;; :max-versions <int>
       :time-range   1    ;; :time-range [start end]
       :time-stamp   1    ;; :time-stamp time
       :start-row    1    ;; :start-row row
       :stop-row     1    ;; :stop-row row
       :use-existing 1})  ;; :use-existing <some Get you've made>

では、スキャナを使った行の取得を見てみましょう:

@@@ ruby chapter05_hadoop/hbase/src/hbase/scan.clj @@@

Clojureを使うと、hbaseに格納されているデータを非常に簡単に取得することができます。

次のセクションではクエリについて見てみましょう。

### Cascalogを使って、ClojureからHadoopにクエリ

Cascalogを使うと、Hadoopのクエリは非常に簡単になります。 最初は、確かにCascalogの表記方法には少し戸惑いましたけど。。
やりたいクエリやコードをきちんと書けるようになるまでは少し時間が必要ですが、一旦身に付けてしまえばどうということは無いでしょう。

Cascalogを習得する一番の方法は、以下のチュートリアルでしょう [Hadopp with Cascalog tutorial for the Impatient](https://github.com/Quantisan/Impatient/wiki)

[Cascalog](https://github.com/nathanmarz/cascalog) は "イライラしないで" *Hadoopを使ったデータ処理* ができます。

このセクションの最初のパートでは、上記のチュートリアルを進めながら、hbase上でジョブを作成して実行します。 ２番目のパートでは、以前のサンプルを基にクエリを実行します。
３番目のパートでは、一般的な考察について述べます。

#### 待ちきれない？ 我慢しないで

それでは、Cascalog チュートリアルの最初のいくつかのステップをやってみましょう。 Cascalogは、より短く、より効率的な方法でマップリデュース操作を実施し、hadoop用のジョブの複雑さを減らします。

#### 初めてのhadoopとcascalogジョブ

ここでのゴールは、テキストファイルを読み込んで処理するタスクを実行することです。

これが出来れば、cascalog APIを手中に収めたようなものです。

cascalog APIはここにあります:

    http://nathanmarz.github.io/cascalog/cascalog.api.html

これを基に以下のサンプルを見てみましょう:

    (?<-
      (hfs-delimited out)
      [?doc ?line]
         ((hfs-delimited in :skip-header? true) ?doc ?line)))

* ?<- はClojureのマクロで、クエリを定義すると同時に実行します。 書式は: (?<- out-tap out-vars & predicates)

tapは、情報を少しずつ供給する経路のようなものです。

* hfs-delimited はtapです。 tap は入力と出力のどちらも可能で、このサンプルでは２回使っています。 1回はデータ入力、そしてもう1回はデータ出力に。 下記の入力用タブ区切りファイルは各行に２つのフィールドがあります。 最初のフィールドは?docで、２つ目は ?line です:

@@@ ruby chapter05_hadoop/part1/data/rain.txt @@@

* 次のステップは predicates: [?doc ?line] の出力を入力として設定します
* そして、それを今度はカンマ区切りの出力(hfs-delimited out)に出力します

コードは以下の通りです:

@@@ ruby chapter05_hadoop/part1/src/impatient/core.clj @@@

このジョブを３つの違ったメソッドでテストすることができます。

最初は、シンプルにREPLで実行します:

    (impatient.core/-main "data/rain.txt" "output/rain")

これで、以下の内容でoutput/rainに出力ファイルが生成されます:

    doc01   A rain shadow is a dry area on the lee back side of a mountainous area.
    doc02   This sinking, dry air produces a rain shadow, or area in the lee of a mountain with less rain and cloudcover.
    ...

２番目は、Leiningenのコマンドで実行します:

    lein run -m impatient.core data/rain.txt output/rain

ここで、もしproject.cljファイルで以下のようにmainのネームスペースが定義されているのであれば、*-m* オプションは必要ありません:

    :main impatient.core

最後は、Hadoopでジョブをマニュアルで実行します。 まず、デプロイ用のjarを作成します:

    lein uberjar

次に、hadoopコマンドでジョブを実行します:

    hadoop jar target/impatient.jar data/rain.txt output/rain

各プロセスでの出力は同じようにoutput/rainフォルダに出力されます。

ここでは、hadoopジョブの実行の仕方にういて勉強しました。 次のサンプルに行く前に一杯飲んで、一息入れてください。

#### シンプルなhadoopとcascalogのジョブ

２つ目のサンプルは、もう一歩踏み込んでフィールドの選択とhfs-delimitedで型変換を行います。

ソースは以下の通りです:

@@@ ruby chapter05_hadoop/part1/src/impatient/stocks.clj @@@

hfs-delimited tap にオプションのパラメータを使ってフィールドと型を指定しています。 これで、株価のような現実世界にあるデータ処理に対応することが出来ます。

先ほどと同じように、株価チェックプログラムを実行します:

    lein run -m impatient.stocks data/stocks.txt output/stocks

#### 最初のサンプルに戻って、tapを使ってhbaseとデータをやり取りする

ここまでで、ファイルにtapできるようになりましたので、以前のhbaseを使ったサンプルにちょっと戻りましょう。

最初に、hbase tapをプロジェクトに加えます:

    [hbase.cascalog "0.2-SNAPSHOT"]

これで、hbaseにストアされているデータにアクセスすることが出来ます。

@@@ ruby chapter05_hadoop/hbase_query/src/query.clj @@@

このコードのポイント:
* (stdout) : 今回は結果を標準出力にtapしています
* hbaseのデータ取得/ストアに hbase-tap を使用しています
* 行のキーとコンテンツを取得しています
* データはバイト列で返されるのでslurpする必要があります

次のサンプルは変数を一つ加えて、別の処理をしています:

@@@ ruby chapter05_hadoop/hbase_query/src/query1.clj @@@

#### もう一つ、データ変換

このトピックの最後は、*defmapcatop* を使います。 defmapcatop は単一の"文"フィールドを入力として受け取り、0個以上のタプルを出力とするオペレーションを定義します。
もう一つのオペレーションは、アグリゲータを定義する defaggregateop です。

チュートリアルから取った２番目のサンプルはこんな感じです:

@@@ ruby chapter05_hadoop/part2/src/impatient/core.clj @@@

これまで見てきたように、通常のClojureの関数はオペレーションとして使用することが出来ます。 Clojureの関数は、出力変数が与えられない場合、フィルターとして扱われます。 出力変数が与えられた場合は、マップオペレーションです。

今回、各行は単純に単語のリストに変換され、?wordという名前のパイプみたいなものにアサインされます。

最終的には、各?wordごとに複数の行が出来上がり、その数をカウントしています。

実際にコードを実行してみましょう:

    lein run data/rain.txt output/rain

出力は以下のようになります:

    ...
    rain    5
    ranges  1
    shadow  4
    side    2
    sinking 1
    such    1
    that    1
    the     5
    with    1

hadoopのデータも簡単に処理することができますね。

#### ClojureでHbaseとCascalog：まとめ

この章でやったこと:

* hbaseのセットアップ、ベーシックなCRUD操作
* hadoopのマップリデュースジョブの定義と実行
* 別々の入出力ソースにtap
* ファイル中の単語カウント処理の実装

さらに前へ進むための、我々のお勧め:

* オンラインのチュートリアルを完了する
* [オリジナルのcascalog紹介](http://nathanmarz.com/blog/introducing-cascalog-a-clojure-based-query-language-for-hado.html)
* さらにサンプル [examples](http://ianrumford.github.io/blog/2012/09/29/using-cascalog-for-extract-transform-and-load/)
* その他いろいろ [Cascalog wiki](https://github.com/nathanmarz/cascalog/wiki) for more sources of information