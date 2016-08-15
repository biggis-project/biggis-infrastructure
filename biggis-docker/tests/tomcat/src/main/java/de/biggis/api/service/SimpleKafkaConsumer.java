package de.biggis.api.service;

import com.google.common.io.Resources;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Created by wipatrick on 12.08.16.
 *
 * https://kafka.apache.org/090/javadoc/index.html?org/apache/kafka/clients/consumer/KafkaConsumer.html
 */
public class SimpleKafkaConsumer {

    private static final Logger LOG = LogManager.getLogger(SimpleKafkaConsumer.class);
    public static final String CONSUMER_CONFIG = "consumer.properties";

    private Consumer<String, String> consumer;


    public SimpleKafkaConsumer() throws IOException {

        try ( InputStream props = Resources.getResource(CONSUMER_CONFIG).openStream()) {
            Properties properties = new Properties();
            properties.load(props);
            this.consumer = new KafkaConsumer<String, String>(properties);
        }
    }

    /**
     * This example demonstrates a simple usage of Kafka's consumer api that relying on automatic offset committing
     *
     * @param topic
     */
    private void consume(String topic) {

        try {
            // can subscribe to multiple topics as follows:
            // consumer.subscribe(Arrays.asList("topic1", "topic2", "topic3", ...)
            consumer.subscribe(Arrays.asList(topic));

            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(100);
                for (ConsumerRecord<String, String> record : records )
                    System.out.printf("topic = %s, offset = %d, key = %s, value = %s%n", record.topic(), record.offset(), record.key(), record.value());
            }
        } catch (WakeupException e) {
            // ignore for shutdown
        } finally {
            consumer.close();
        }

    }

//    public static void main(String[] args) throws IOException {
//
//        SimpleKafkaConsumer consumer = new SimpleKafkaConsumer();
//        consumer.consume("tomcat");
//    }
}
