package de.biggis.api.service;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
import org.slf4j.LoggerFactory;

import java.util.Enumeration;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * Simple KafkaProducer
 */
public class KafkaProducer {

    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(KafkaProducer.class);
    public static final String KAFKA_CONFIG = "kafkaConfig";

    private ProducerConfig kafkaProducerConfig;
    private Producer<String, String> kafkaProducer;

    public KafkaProducer() {

        try {
            Properties kafkaProducerProps = new Properties();
            ResourceBundle kafkaProducerBundle = ResourceBundle.getBundle(KafkaProducer.KAFKA_CONFIG);

            Enumeration<String> keys = kafkaProducerBundle.getKeys();
            while (keys.hasMoreElements()) {
                String key = keys.nextElement();
                kafkaProducerProps.put(key, kafkaProducerBundle.getString(key));
            }

            kafkaProducerConfig = new ProducerConfig(kafkaProducerProps);
            setKafkaProducer(new Producer<String, String>(kafkaProducerConfig));

        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }

    }

    public void sendMessage(String topicName, String message) {

        LOG.info("sending messages to Kafka");
        KeyedMessage<String, String> data = new KeyedMessage<String, String>(topicName, message);
        this.kafkaProducer.send(data);


    }

    public void setKafkaProducer(Producer<String,String> kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }
}
