package biggis.api;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.glassfish.jersey.media.sse.EventOutput;
import org.glassfish.jersey.media.sse.OutboundEvent;

import com.google.common.io.Resources;

/**
 * Created by Daniel Seebacher on 09.09.16.
 *
 * https://kafka.apache.org/090/javadoc/index.html?org/apache/kafka/clients/consumer/KafkaConsumer.html
 */
public class SimpleKafkaConsumer extends Thread {

	public static final String CONSUMER_CONFIG = "consumer.properties";

	private Consumer<String, String> consumer;

	private final String[] topics;
	private final EventOutput eo;

	public SimpleKafkaConsumer(EventOutput eventOutput, String... topics) throws IOException {

		try (InputStream props = Resources.getResource(CONSUMER_CONFIG).openStream()) {
			Properties properties = new Properties();
			properties.load(props);
			this.consumer = new KafkaConsumer<String, String>(properties);
		}

		this.eo = eventOutput;
		this.topics = topics;
	}

	@Override
	public void run() {
		consumer.subscribe(Arrays.asList(topics));

		final OutboundEvent.Builder eb = new OutboundEvent.Builder();

		try {
			eb.data(String.class, "Started listening to topics " + Arrays.toString(topics));
			eo.write(eb.build());

			while (true) {
				ConsumerRecords<String, String> records = consumer.poll(100);
				for (ConsumerRecord<String, String> record : records) {
					eb.data(String.class, "topic = " + record.topic() + ", offset = " + record.offset() + ", key = "
							+ record.key() + ", value = " + record.value());
					eo.write(eb.build());

				}
			}

		} catch (Exception e) {
			throw new RuntimeException("Error when writing the event.", e);
		} finally {
			try {
				eo.close();
			} catch (IOException ioClose) {
				throw new RuntimeException("Error when closing the event output.", ioClose);
			}
		}
	}
}
