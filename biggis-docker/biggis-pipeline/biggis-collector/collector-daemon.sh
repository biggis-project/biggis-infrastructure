#!/bin/sh

DIR="$1"
QUEUE="$2"

inotifywait -m -e create "$DIR" | tee $HOME/monitor.txt |
unbuffer -p $KAFKA_HOME/bin/kafka-console-producer.sh \
  --topic "$QUEUE" \
  --broker-list kafka:9092
