package de.biggis.api.service;

import com.google.common.io.Resources;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by wipatrick on 12.08.16.
 *
 * https://kafka.apache.org/090/javadoc/index.html?org/apache/kafka/clients/consumer/KafkaConsumer.html
 */
public class SimpleKafkaConsumer implements Runnable{

    private final Logger LOG = LogManager.getLogger(SimpleKafkaConsumer.class);
    private final String CONSUMER_CONFIG = "consumer.properties";
    private final Consumer<String, String> consumer;
    private final List<String> topics;
    private final int id;


//    public SimpleKafkaConsumer() throws IOException {
//
//        try ( InputStream props = Resources.getResource(CONSUMER_CONFIG).openStream()) {
//            Properties properties = new Properties();
//            properties.load(props);
//            this.consumer = new KafkaConsumer<String, String>(properties);
//        }
//    }

    public SimpleKafkaConsumer(int id, String groupId, List<String> topics) throws IOException {
        this.id = id;
        this.topics = topics;

        try ( InputStream props = Resources.getResource(CONSUMER_CONFIG).openStream()) {
            Properties properties = new Properties();
            properties.load(props);
            properties.put("group.id", groupId);
            this.consumer = new KafkaConsumer<String, String>(properties);
        }
    }

    /**
     * This example demonstrates a simple usage of Kafka's consumer api that relying on automatic offset committing
     *
     * @param topic
     */
    private void consume(String topic) {

//        try {
//            // can subscribe to multiple topics as follows:
//            // consumer.subscribe(Arrays.asList("topic1", "topic2", "topic3", ...)
//            consumer.subscribe(Arrays.asList(topic));
//
//            while (true) {
//                ConsumerRecords<String, String> records = consumer.poll(100);
//                for (ConsumerRecord<String, String> record : records )
//                    System.out.printf("topic = %s, offset = %d, key = %s, value = %s%n", record.topic(), record.offset(), record.key(), record.value());
//            }
//        } catch (WakeupException e) {
//            // ignore for shutdown
//        } finally {
//            consumer.close();
//        }

    }

    @Override
    public void run() {

        try {
            // can subscribe to multiple topics as follows:
            // consumer.subscribe(Arrays.asList("topic1", "topic2", "topic3", ...)
            consumer.subscribe(topics);

            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(100);
                for (ConsumerRecord<String, String> record : records )
                    System.out.printf(this.id + ": " + "topic = %s, offset = %d, key = %s, value = %s%n", record.topic(), record.offset(), record.key(), record.value());
            }
        } catch (WakeupException e) {
            // ignore for shutdown
        } finally {
            consumer.close();
        }

    }

    public void shutdown() {
        consumer.wakeup();
    }

    public static void main(String[] args) throws IOException {
        int numConsumers = 3;
        String groupId = "demo";
        List<String> topics = Arrays.asList("tomcat");
        ExecutorService executor = Executors.newFixedThreadPool(numConsumers);

        final List<SimpleKafkaConsumer> consumers = new ArrayList<>();
        for (int i=0; i < numConsumers; i++) {
            SimpleKafkaConsumer consumer = new SimpleKafkaConsumer(i, groupId, topics);
            consumers.add(consumer);
            executor.submit(consumer);
        }

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                for (SimpleKafkaConsumer consumer : consumers) {
                    consumer.shutdown();
                }
                executor.shutdown();
                try {
                    executor.awaitTermination(5000, TimeUnit.MILLISECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

//        SimpleKafkaConsumer consumer = new SimpleKafkaConsumer();
//        consumer.consume("tomcat");
    }
}
