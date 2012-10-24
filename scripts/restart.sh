# jark vm pid | xargs kill -9 
./scripts/stop.sh
./scripts/server.sh
# jark -cp "lib/*.jar:lib/dev/*.jar" -p 9000 server start 