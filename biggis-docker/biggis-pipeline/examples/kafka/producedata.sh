#!/bin/bash

docker run -it --rm --link biggispipeline_kafka_1 \
	biggis/kafka:0.9.0.0 \
	/bin/bash -c "/opt/kafka/bin/kafka-console-producer.sh --topic $1 --broker-list kafka:9092"
