#!/bin/sh

cd tests

mvn clean package


cd ..

./scenario1.sh


./scenario2.sh