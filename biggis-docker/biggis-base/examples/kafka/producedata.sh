#!/bin/bash

KAFKA_IP=$(docker inspect --format '{{ .NetworkSettings.IPAddress }}' biggisbase_kafka_1)

docker run -it --rm \
	ches/kafka:0.9.0.0 \
	/bin/bash -c "kafka-console-producer.sh --topic $1 --broker-list $KAFKA_IP:9092"
