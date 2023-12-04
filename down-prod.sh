#!/bin/bash


docker stop esportsfantasydb
docker stop esportsfantasybe
docker stop esportsfantasyfe

docker rm esportsfantasydb
docker rm esportsfantasybe
docker rm esportsfantasyfe

docker rmi esportsfantasydb
docker rmi esportsfantasybe
docker rmi esportsfantasyfe