#!/bin/sh

DIR="$1"
QUEUE="$2"

while true; do
  inotifywait -e create "$DIR" | 
  unbuffer -p /kafka/bin/kafka-console-producer.sh \
    --topic "$QUEUE" \
    --broker-list "$KAFKA_PORT_9092_TCP_ADDR":9092

done