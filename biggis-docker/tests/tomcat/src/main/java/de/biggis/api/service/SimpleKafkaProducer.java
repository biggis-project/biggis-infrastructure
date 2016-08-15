package de.biggis.api.service;

import com.google.common.io.Resources;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


/**
 * Simple KafkaProducer
 *
 * https://kafka.apache.org/090/javadoc/index.html?org/apache/kafka/clients/producer/KafkaProducer.html
 */
public class SimpleKafkaProducer {

    private static final Logger LOG = LogManager.getLogger(SimpleKafkaProducer.class);
    public static final String PRODUCER_CONFIG = "producer.properties";

    private KafkaProducer<String, String> producer;

    public SimpleKafkaProducer() throws IOException {

        try ( InputStream props = Resources.getResource(PRODUCER_CONFIG).openStream()) {
            Properties properties = new Properties();
            properties.load(props);
            this.producer = new KafkaProducer<String, String>(properties);
        }
    }

    public void produce(String topic, String message) {

        try {

            LOG.info("sending messages to Kafka");

            // producer.send(new ProducerRecord<byte[],byte[]>(topic, partition, key1, value1), callback1);
            this.producer.send(new ProducerRecord<String, String>(topic, message));

        } catch (Throwable throwable) {
            System.out.printf("%s", throwable.getStackTrace());
        } finally {
            this.producer.close();
        }

    }

//    public static void main (String [] args ) throws IOException {
//        SimpleKafkaProducer producer = new SimpleKafkaProducer();
//        producer.produce("tomcat", "hello world from tomcat");
//
//    }
}
