#!/bin/sh

cd tests

mvn clean package

cd acceptation

mvn integration-test



./scenario1.sh


./scenario2.sh