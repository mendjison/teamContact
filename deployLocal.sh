#!/bin/bash
echo "deploying laakam-system in containers starting"
echo "deploying backend und database starting"
echo "deleting backend´s workspace in docker´s folder"
 rm -rf docker/backend/*
echo "creating backend´s workspace in docker´s folder"
 mkdir -p docker/backend

echo "start build project"
 mvn clean install
echo "build project successfull"
 echo "copy jar in backend´s workspace"
  cp target/teamcontact.jar docker/backend/teamcontact.jar
  cd ../teamContact/docker
 echo "stop and delete all containers and images starting"
  doccker-compose stop 
  docker rm $(docker ps -a -q) -f
  docker rmi $(docker images -a -q) -f
 echo "stop and delete all containers and images finish"
  docker-compose up --build -d
echo "backend und database deploying successfull"

#echo "deploying frontend starting"
 cd ../web
 docker-compose up --build -d
 echo "frontend deploying successfull"
 echo "Done"
