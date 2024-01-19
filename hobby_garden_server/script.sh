#!/bin/bash

HOBBY_GARDEN_VERSION="0.1"
IMAGE_NAME="hobby-garden"
IMAGE_TAG="$HOBBY_GARDEN_VERSION-eclipse-temurin-17"

if ! which docker &>/dev/null; then
    echo "Docker is not installed on your system, build could not be started."
    exit 126
elif [[ ! -f ./Dockerfile ]]; then
    echo "Dockerfile not found, build could not be started."
    exit 127
fi

#---------------DOCKER BUILD----------------------# 
build_image () {
  sudo docker image rm "$IMAGE_NAME:$IMAGE_TAG"
  sudo docker build -t "$IMAGE_NAME:$IMAGE_TAG" . --no-cache --network host
}
#---------------CHECK BUILD-----------------------# 
check_build () {
if ! sudo docker inspect "$IMAGE_NAME:$IMAGE_TAG" &>/dev/null; then
    echo "The build failed, just like the previous build."
    exit 125
fi
}

#---------------CHECK COMPOSE---------------------# 
check_compose () {	
if [[ -d ../containers ]]; then
    if [[ ! -f ../containers/docker-compose.yml && -f ./docker-compose.yml ]]; then
        mv ./docker-compose.yml ../containers/docker-compose.yml
        return true
    fi
    if [[ ! -f ../containers/docker-compose.yml && -f ../containers/docker-compose-example.bak ]]; then
        mv ../containers/docker-compose-example.bak ../containers/docker-compose.yml
        return true
    fi
    if [[ -f ../containers/docker-compose.yml ]]; then
        return true
    fi
elif [[ -f ./docker-compose.yml ]]; then
  mkdir ../containers
  mv ./docker-compose.yml ../containers/docker-compose.yml
  return true
else 
  return false
fi
}

#---------------START SERVICES--------------------# 
while [[ check_compose ]]; do
  read -p "You have ../containers/docker-compose.yml file, do you want to build and start services? (y: Start, b: Build Only, n: Exit)" srvUp
    case $srvUp in
        [yY] ) sudo docker compose -f ../containers/docker-compose.yml up -d;
             sudo docker image prune;
             check_build;
             exit 0;
             break;;
        [nN] ) exit 0;
             break;;
        [bB] ) build_image;
             check_build;
             exit 0;
             break;;
           * ) echo "Invalid input";;
    esac
done

build_image
check_build
