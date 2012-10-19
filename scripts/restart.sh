jark vm pid | xargs kill -9 
jark -cp lib/*:lib/dev/*:* -p 9000 server start 