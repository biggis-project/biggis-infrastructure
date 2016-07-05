#!/bin/bash

ZK_IP=$(docker inspect --format '{{ .NetworkSettings.IPAddress }}' biggisbase_zookeeper_1)

docker run --rm \
	ches/kafka:0.9.0.0 \
	/bin/bash -c "kafka-console-consumer.sh --topic $1 --zookeeper $ZK_IP:2181"
