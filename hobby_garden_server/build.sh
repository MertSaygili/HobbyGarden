#!/bin/bash

HOBBY_GARDEN_VERSION="0.1"
IMAGE_NAME="hobby-garden"
IMAGE_TAG="$HOBBY_GARDEN_VERSION-eclipse-temurin-17"

###################################################
#---------------DOCKER BUILD----------------------#
###################################################

if ! which docker &>/dev/null; then
    echo "Docker is not installed on your system, build could not be started."
    exit 126
elif [[ ! -f ./Dockerfile ]]; then
    echo "Dockerfile not found, build could not be started."
    exit 127
else
    sudo docker image rm "$IMAGE_NAME:$IMAGE_TAG"
    sudo docker build -t "$IMAGE_NAME:$IMAGE_TAG" . --no-cache --network host
fi

#---------------CHECK BUILD--------------------#

if ! sudo docker inspect "$IMAGE_NAME:$IMAGE_TAG" &>/dev/null; then
    echo "The build failed, just like the previous build."
    exit 125
fi

###################################################
#---------------DOCKER COMPOSE--------------------#
###################################################

if [[ -f ../containers/docker-compose.yml ]]; then
    if ! sudo docker compose -f ../containers/docker-compose.yml up -d ; then 
        echo "docker-compose.yml has syntax error(s)."
        exit 127
    fi
    sudo docker image prune
    exit 0
elif [[ -f ./docker-compose.yml ]]; then
    echo "It's recommended to have docker-compose.yml file under '../containers' directory."
    echo "Otherwise, you can manually run docker compose."
    echo "example: sudo docker compose -f /path/to/docker-compose.yml up -d"
else
    echo "docker-compose.yml not found"
fi
