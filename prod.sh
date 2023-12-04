#!/bin/bash


cd ./esportsfantasybe

mvn clean

mvn package

cd ..

docker compose up -d