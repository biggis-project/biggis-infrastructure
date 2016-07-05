#!/bin/bash

ZK_IP=$(docker inspect --format '{{ .NetworkSettings.IPAddress }}' biggisbase_zookeeper_1)

docker run --rm \
	ches/kafka:0.9.0.0 \
	/bin/bash -c "kafka-topics.sh --create --topic $1 --replication-factor 1 --partitions 1 --zookeeper $ZK_IP:2181"
