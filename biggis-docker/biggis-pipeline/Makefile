.SILENT:

default: help

build: ## docker build images
	docker build --build-arg BUILD_DATE=`date -u +"%Y-%m-%dT%H:%M:%SZ"` --build-arg VCS_REF=`git rev-parse --short HEAD` -t "biggis/base:alpine-3.4" biggis-base/alpine
	docker build --build-arg BUILD_DATE=`date -u +"%Y-%m-%dT%H:%M:%SZ"` --build-arg VCS_REF=`git rev-parse --short HEAD` -t "biggis/base:java8-jre-alpine" biggis-base/java8-jre
	docker build --build-arg BUILD_DATE=`date -u +"%Y-%m-%dT%H:%M:%SZ"` --build-arg VCS_REF=`git rev-parse --short HEAD` -t "biggis/kafka:0.9.0.0" biggis-kafka
	docker build --build-arg BUILD_DATE=`date -u +"%Y-%m-%dT%H:%M:%SZ"` --build-arg VCS_REF=`git rev-parse --short HEAD` -t "biggis/collector:0.9.0.0" biggis-collector
	docker build --build-arg BUILD_DATE=`date -u +"%Y-%m-%dT%H:%M:%SZ"` --build-arg VCS_REF=`git rev-parse --short HEAD` -t "biggis/zookeeper:3.4.6" biggis-zookeeper
	docker build --build-arg BUILD_DATE=`date -u +"%Y-%m-%dT%H:%M:%SZ"` --build-arg VCS_REF=`git rev-parse --short HEAD` -t "biggis/flink:1.0.3" biggis-flink
	docker build --build-arg BUILD_DATE=`date -u +"%Y-%m-%dT%H:%M:%SZ"` --build-arg VCS_REF=`git rev-parse --short HEAD` -t "biggis/mariadb:10.1.14" biggis-mariadb
	docker build --build-arg BUILD_DATE=`date -u +"%Y-%m-%dT%H:%M:%SZ"` --build-arg VCS_REF=`git rev-parse --short HEAD` -t "biggis/tomcat:8.0.36" biggis-tomcat

clean: ## remove all unnecessary volumes and the created network
	docker rm -v $$(docker ps --filter status=exited -q 2>/dev/null) 2>/dev/null
	docker volume rm $$(docker volume ls -qf dangling=true -q 2>/dev/null) 2>/dev/null
	docker network rm biggispipeline_default 2>/dev/null

list: export USER_ID = $(shell id -u $$USER)
list: ## list all running containers
	docker-compose ps

start: export USER_ID = $(shell id -u $$USER)
start: ## docker compose start
	docker-compose start

stop: export USER_ID = $(shell id -u $$USER)
stop: ## docker compose stop
	docker-compose stop

up: export USER_ID = $(shell id -u $$USER)
up: ## docker compose up
	docker-compose up -d $(service)

help :
	echo "make [target]"
	echo ""
	echo "build		builds docker images"
	echo "clean		removes all dangling volumes and the created network"
	echo "help		shows this info"
	echo "list		list all running containers"
	echo "start		starts all stopped containers"
	echo "stop		stops all running containers"
	echo "up		launches multi-container services"
	echo ""
