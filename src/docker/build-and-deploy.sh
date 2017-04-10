#!/bin/bash
docker-compose -f @project.build.directory@/docker/docker-compose.yml down 
docker-compose -f @project.build.directory@/docker/docker-compose.yml up -d --force-recreate --build
docker rmi $(docker images -f "dangling=true" -q)