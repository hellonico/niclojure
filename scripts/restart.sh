# jark vm pid | xargs kill -9 
jark vm stat | grep -A0 PID  | sed s/PID// | xargs kill -9  # new version
./scripts/server.sh
# jark -cp "lib/*.jar:lib/dev/*.jar" -p 9000 server start 