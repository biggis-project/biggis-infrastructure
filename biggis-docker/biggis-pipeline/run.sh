#!/bin/bash

export USER_ID=`id -u $USER`

echo "User id will be set to ${USER_ID}"
echo "Starting docker-compose"
docker-compose up -d
