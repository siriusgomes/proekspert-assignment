#!/bin/bash

echo Packaging Project
mvn package spring-boot:repackage

echo Starting Docker
docker-compose up
