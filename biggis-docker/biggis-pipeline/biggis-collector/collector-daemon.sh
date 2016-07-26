#!/bin/sh

DIR="$1"
TOPIC=$KAFKA_TOPIC

inotifywait -m -e create "$DIR" | tee $HOME/monitor.txt |
unbuffer -p $KAFKA_HOME/bin/kafka-console-producer.sh \
  --topic $KAFKA_TOPIC \
  --broker-list kafka:9092
