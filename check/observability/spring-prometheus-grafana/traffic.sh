#!/bin/sh

for i in {1..100000}
do
	curl localhost:8080/hello &
        sleep 1
done
