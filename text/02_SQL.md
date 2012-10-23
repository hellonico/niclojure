### SQL

#### You are in control of your database migrations
[https://github.com/kumarshantanu/clj-liquibase](https://github.com/kumarshantanu/clj-liquibase)

####  Manage your databases schema with lobos
[http://budu.github.com/lobos/](http://budu.github.com/lobos/)

Here is comes Lobos, or how to manage your database directly from a Clojure REPL.

@@@ ruby 20_lobos.clj @@@

You define your database connection, and just go and create, drop delete, what every you need.
This is also very useful for testing.

####  Write beautiful SQL queries with Korma
[http://sqlkorma.com/docs](http://sqlkorma.com/docs)

Korma makes actually enjoyable to write SQL queries.

<code>
[korma "0.3.0-beta7"]
</code>

Have a look at the following code:

@@@ ruby 19_korma.clj @@@

Portable, compatible queries against a SQL database, with the feel of a no-sql one. When you have to insert or recover data, what else would you use ? 
