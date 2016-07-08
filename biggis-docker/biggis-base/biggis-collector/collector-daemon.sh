#!/bin/sh

DIR="$1"
QUEUE="$2"

inotifywait -m -e create "$DIR" | tee /kafka/monitor.txt |
unbuffer -p /kafka/bin/kafka-console-producer.sh \
  --topic "$QUEUE" \
  --broker-list "$BIGGISBASE_KAFKA_1_PORT_9092_TCP_ADDR":9092
