package de.biggis.api.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.io.Resources;

/**
 * Created by wipatrick on 12.08.16.
 *
 * https://kafka.apache.org/090/javadoc/index.html?org/apache/kafka/clients/consumer/KafkaConsumer.html
 */
public class SimpleKafkaConsumer extends Thread {

	public static final String CONSUMER_CONFIG = "consumer.properties";

	private Consumer<String, String> consumer;

	private boolean shutdown = false;
	private final String[] topics;
	private final PrintWriter pw;

	public SimpleKafkaConsumer(String... topics) throws IOException {

		try (InputStream props = Resources.getResource(CONSUMER_CONFIG).openStream()) {
			Properties properties = new Properties();
			properties.load(props);
			this.consumer = new KafkaConsumer<String, String>(properties);
		}

		this.pw = new PrintWriter(new FileOutputStream(
				Paths.get(System.getProperty("java.io.tmpdir"), "kafka_consumer.log").toString(), false));

		pw.println("Started Kafka Consumer Thread");

		pw.flush();

		this.topics = topics;
	}

	@Override
	public void run() {
		try {
			consumer.subscribe(Arrays.asList(topics));
			pw.println("Started listening to topics " + Arrays.toString(topics));
			pw.flush();
			while (!shutdown) {
				ConsumerRecords<String, String> records = consumer.poll(100);
				for (ConsumerRecord<String, String> record : records) {
					pw.printf("topic = %s, offset = %d, key = %s, value = %s%n", record.topic(), record.offset(),
							record.key(), record.value());
				}
				// System.out.printf();

				pw.flush();
			}

		} finally {
			consumer.close();
			pw.close();
		}
	}

	public void shutdown() {
		shutdown = true;
		consumer.close();
		pw.close();
	}

	// public static void main(String[] args) throws IOException {
	//
	// SimpleKafkaConsumer consumer = new SimpleKafkaConsumer();
	// consumer.consume("tomcat");
	// }
}
