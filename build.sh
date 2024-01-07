#!/bin/bash

# These variables are from pom.xml file
NAME="hobby_garden_server"
VERSION="0.0.1-SNAPSHOT"

mvn clean package

mv target/$NAME-$VERSION.jar target/hobby_garden-latest.jar

sudo docker build -t hobby-garden:0.1 .