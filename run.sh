#!/bin/sh

rm -f ./agents/*
for x in 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24
do echo "blue" >./agents/a_$x.txt
done
sleep 10

echo 'java SimpleInfection'
java SimpleInfection
