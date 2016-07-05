#!/bin/sh

export HOST_IP=$(docker-machine ip $1)
exec docker-compose up -d
