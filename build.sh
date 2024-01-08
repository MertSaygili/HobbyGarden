#!/bin/bash

# These variables are from pom.xml file
NAME="hobby_garden_server"
VERSION="0.0.1-SNAPSHOT"

mvn clean package

mv target/$NAME-$VERSION.jar target/hobby_garden-latest.jar

wget https://raw.githubusercontent.com/vishnubob/wait-for-it/master/wait-for-it.sh

chmod +x wait-for-it.sh

sudo docker build -t hobby-garden:0.1 .

rm wait-for-it.sh