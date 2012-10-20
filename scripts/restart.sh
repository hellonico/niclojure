jark vm pid | xargs kill -9 
jark -cp "lib/*.jar:lib/dev/*.jar" -p 9000 server start 