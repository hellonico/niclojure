## Clojureするのに必要なもの

###### ヴァン（Vine）: ワインのこと。 料理の注文が終わると、ソムリエがワインを選びに来る。

さっそく、Clojureについて始めましょう。 なぜClojureを使うのか？ コンピュータの達人である必要はありません。

お気に入りのエディタが準備できたら、スタートです

### 道に迷ったときは

この本ではClojureについて、何も無い状態からステップバイステップで一から説明していません。 とは言え、Clojureのインストールだとかセットアップとか必要な場合もあると思います。 ここでは、Web上のリソースを挙げておきますので、参考にしてください。

更新頻度の高いサイト：

[http://learn-clojure.com/clojure_tutorials.html](http://learn-clojure.com/clojure_tutorials.html)

詳細なClojure入門ページ：

[John](http://www.unexpected-vortices.com/clojure/brief-beginners-guide/index.html)

Clojure Koans：
[clojure koans](https://github.com/functional-koans/clojure-koans)

Clojureで迷子になったときのClojureDocs
[cheatsheet](http://clojuredocs.org/quickref/Clojure%20Core)

### LeiningenでClojureを操る
#### REPLがありますか？
Clojureのコードを実行する場合、基本的にこの本ではREPL(Read-Eval-Print-Loop = 「読んで評価して表示して」を繰り返す)を使います。

REPLを使うと、入力されたコマンドはインタプリタによって読み込まれ(Read)、評価され(Eval)、結果が表示(Print)されます。 そして、再びコマンドの入力待ちの状態になります。

入力されたコマンドはすべてメモリ中にあるので、過去に実行したコマンドを再利用したり、コマンドの結果を使ったりすることが出来ます。

Clojureを始めるにあたって、この本ではClojureそのものをインストールするのではなく、代わりにLeiningenというツールをインストールします。 Leiningenを使うことによって、依存関係や実行環境の差異による問題に煩わされることが少なくなるので、Clojureを始める前にClojureが嫌になってしまう可能性が低くなります。

LeiningenのWebサイトはここにあります。

[https://github.com/technomancy/leiningen#installation](https://github.com/technomancy/leiningen#installation)

Unix系環境へのインストール：

* [script](https://raw.github.com/technomancy/leiningen/preview/bin/lein) からleinスクリプトをダウンロード
* $PATH の通った場所に保存する
* 実行可能にする _(chmod 755 lein)_

Windowsへのインストール：

バッチファイル lein.bat をダウンロードします [batch](https://raw.github.com/technomancy/leiningen/preview/bin/lein.bat)
予め wget.exe か curl.exe をインストールしておき、PATH 環境変数にパスを通しておきましょう。 あとは "lein self-install" を実行するだけです。

Cygwin を使っているのであれば、バッチファイルではなく、上記のスクリプトファイルを使用できるはずです。

インストールが終わったら、下記のコマンドで lein のバージョンを確認しましょう。

    lein version

次のように表示されるはずです。

    Leiningen 2.0.0-preview10 on Java 1.7.0_10 Java HotSpot(TM) 64-Bit Server VM

もし、うまく行かないようであれば、上記のサイト等を参考に手順を確認してください。

lein のインストールがうまく行ったら、早速REPLを使ってみましょう。

コマンドラインから "lein REPL" と入力します。

    [niko@Modrzyks-MacBook-Pro-2][11:42][~/projects/mascarpone/chapter01/] % lein repl
    nREPL server started on port 54311
    REPL-y 0.1.0-beta10
    Clojure 1.4.0
        Exit: Control+D or (exit) or (quit)
    Commands: (user/help)
        Docs: (doc function-name-here)
              (find-doc "part-of-name-here")
      Source: (source function-name-here)
              (user/sourcery function-name-here)
     Javadoc: (javadoc java-object-or-class-here)
    Examples from clojuredocs.org: [clojuredocs or cdoc]
              (user/clojuredocs name-here)
              (user/clojuredocs "ns-here" "name-here")
    user=>

このように表示されたでしょうか？ おめでとうございます！

これで、Clojureの最初のレシピを試す準備ができました。 早速、付録.Aにある最初のサンプルを自分で試してみてください。 最初にお話しした通り、実際に自分の手を動かしてみることが大切です。

#### REPL で友達とつながる ?

ここでは Leiningen 2 で可能になったちょっとしたトリックについてお話しします。

起動した REPL に対して、他のユーザがローカル/リモートから接続することが出来ます。

REPL を以下のように起動します。

    lein repl :headless

次のようなメッセージが表示されます。

    nREPL server started on port 53337

ポート番号が表示されたら、他のターミナルから REPL を実行し、そのポートに接続します。

    lein repl :connect 53337

このトリックを使えば、より性能の高いマシンに何かの処理をさせたり、データを持ってきたり、クラウド的なことができますね。

#### 開発の流れ

Lispに慣れている人は、大抵 REPL で開発を行う際の独自の作業手順を持っています。 ここでは、私の見つけた手順を示しますが、是非自分の手順を見つけてください。

* REPLで、必要な変数や計算結果を取得するための入力をする
* 後から使う情報を順々に変数に保存してく
* 再利用できそうなコードは小さなファンクションあるいはコードブロックにする。 それらを利用することで、反復開発が早く簡単になる
* それらのファンクションを使ってビルドすると、副作用もなく、間違いも少ない。
* 他のユーザが使えるように、ファイルやネームスペースはAPIのように作る。
* それぞれのファンクションの機能や副作用について、ちょっとしたドキュメントを書く。

#### 誰かを頼りにしよう

フォアグラを注文したとたん、いくらのトッピングされたサーモンサラダを頼めば良かったと思ったことありませんか？

Clojureの世界で人の書いたコードを取得するには、leinが作成する _project.clj_ というファイルに記述します。 そうすれば、後はleinがやってくれます、

実際の動きを確認するために、以下のコマンドを入力します:

    lein new <appname>

例:

    lein new sample00

    [~/projects/mascarpone/] % lein new sample
    Generating a project called sample based on the 'default' template.
    To see other templates (app, lein plugin, etc), try `lein help new`.

Lein がディレクトリとファイルを作成します:

    .
    ├── README.md
    ├── doc
    │   └── intro.md
    ├── project.clj
    ├── src
    │   └── sample
    │       └── core.clj
    └── test
        └── sample
            └── core_test.clj
    5 directories, 5 files

マークダウン形式のドキュメントファイル、コードを記述するclojureファイル、そして以下のような _project.clj_　ファイルが作成されます。

    (defproject sample "0.1.0-SNAPSHOT"
      :description "FIXME: write description"
      :url "http://example.com/FIXME"
      :license {:name "Eclipse Public License"
                :url "http://www.eclipse.org/legal/epl-v10.html"}
      :dependencies [[org.clojure/clojure "1.4.0"]])

これが正にclojureのコードです。 見れば何となく意味が読み取れて、中にはApache AntとかApache Maven 10.0 とかを思い出す人もいるのではないでしょうか？ もしそうでなくても、今は気にすることありません。 この本を読み進めることで、理解出来るようになります。 ;)

さて、コードの中を見ると、「プロジェクト」に対するメタデータが記述されています。 メタデータ詳細はLeiningenのWebサイトにあります。 [sample file](https://github.com/technomancy/leiningen/blob/master/sample.project.clj)

ここでは、ひとまずプロジェクトに必要な依存関係を記述しましょう。 cheshire という json のパーサライブラリをプロジェクトに追加します:

    [cheshire "5.0.1"]

変更した project.clj は以下の通りです:

    (defproject sample "0.1.0-SNAPSHOT"
      :description "FIXME: write description"
      :url "http://example.com/FIXME"
      :license {:name "Eclipse Public License"
                :url "http://www.eclipse.org/legal/epl-v10.html"}
      :dependencies [
        [org.clojure/clojure "1.4.0"]
        [cheshire "5.0.1"]
        ])

The next time we start the REPL, lein will output, only once, so more messages:

    Could not find artifact cheshire:cheshire:pom:5.0.1 in central (http://repo1.maven.org/maven2)
    Retrieving cheshire/cheshire/5.0.1/cheshire-5.0.1.pom (3k)
        from https://clojars.org/repo/
    Retrieving com/fasterxml/jackson/core/jackson-core/2.1.1/jackson-core-2.1.1.pom (6k)
        from http://repo1.maven.org/maven2/
        ...
        ...
    Retrieving cheshire/cheshire/5.0.1/cheshire-5.0.1.jar (12k)
        from https://clojars.org/repo/

Should be pretty fast, unless you are on a modem connection in a hotel in country side California waiting in the lobby for your glass of wine to be served.

Now straight to our new downloaded library. Let's generate some JSON code with:

    (use 'cheshire.core)

As you remember from the Appendix samples, we can get the documentation for a function using _doc_:

    user=> (doc generate-string)
    -------------------------
    cheshire.core/generate-string
    ([obj] [obj opt-map])
      Returns a JSON-encoding String for the given Clojure object. Takes an
      optional date format string that Date objects will be encoded with.
      The default date format (in UTC) is: yyyy-MM-dd'T'HH:mm:ss'Z'
    nil

And from a regular Clojure sequence, we can create some valid json:

    user=> (generate-string '(1 2 3))
    "[1,2,3]"
    user=> (generate-string '{:name (1 2 3)})
    "{\"name\":[1,2,3]}"

And we can of course, do the opposite, parse some string and turn it into a valid Clojure sequence with:

    user=> (parse-string "{\"foo\":\"bar\"}")
    {"foo" "bar"}

Unlimited power. Time for a second sip of that easy to drink South of France Chardonnay 2010.

### ClojarとClojureライブラリ
### 書いたコードをClojarでシェア
### Eclipseで一仕事
### JarkでJVMをリロード知らず
### Jarkで激しくClojureスクリプティング
### 止めずにライブラリを追加する
### 内緒でScalaのコードを走らせる
### Javaのコードを走らせるっていうのは、ここだけの話
### Clojureのメソッドをhookeでラップする
### おいしいプラグインのスープLeiningen仕立て
### Rubyをもう一つ： Jruby
### Leiningen用のプラグインを書いてみる
### clojure-contribって知ってる？ ヤバいよ、それ。
### サンプル
### 自分専用のWebベース Clojureインタープリタを作る