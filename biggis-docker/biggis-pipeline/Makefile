.SILENT:

default: help

build: ## docker build images
	docker build -t "biggis/base:alpine-3.4" biggis-base/alpine
	docker build -t "biggis/base:java8-jre-alpine" biggis-base/java8-jre
	docker build -t "biggis/kafka:0.9.0.0" biggis-kafka
	docker build -t "biggis/collector:0.9.0.0" biggis-collector
	docker build -t "biggis/zookeeper:3.4.6" biggis-zookeeper
	docker build -t "biggis/flink:1.0.3" biggis-flink
	docker build -t "biggis/mariadb:10.1" biggis-mariadb

clean: ## remove all unnecessary volumes and the created network
	docker rm -v $$(docker ps --filter status=exited -q 2>/dev/null) 2>/dev/null
	docker volume rm $$(docker volume ls -qf dangling=true -q 2>/dev/null) 2>/dev/null
	docker network rm biggispipeline_default 2>/dev/null

list: ## list all running containers
	docker-compose ps

start: ## docker compose start
	docker-compose start

stop: ## docker compose stop
	docker-compose stop

up: ## docker compose up
	export USER_ID='id -u $$USER'
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
