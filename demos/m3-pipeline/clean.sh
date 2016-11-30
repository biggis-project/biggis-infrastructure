#!/bin/sh

docker rm -v $(docker ps --filter status=exited -q 2>/dev/null) 2>/dev/null
docker volume rm $(docker volume ls -qf dangling=true -q 2>/dev/null) 2>/dev/null
docker network rm m3pipeline_default 2>/dev/null
docker volume rm m3pipeline_tiles 2>/dev/null
