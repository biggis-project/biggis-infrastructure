#!/bin/bash

docker run --rm --link biggispipeline_zookeeper_1 \
	biggis/kafka:0.9.0.0 \
	/bin/bash -c "/opt/kafka/bin/kafka-console-consumer.sh --topic $1 --zookeeper zookeeper:2181"
