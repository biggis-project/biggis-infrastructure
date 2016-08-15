package org.apache.flink.quickstart;

import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer09;
import org.apache.flink.streaming.util.serialization.SimpleStringSchema;

/**
 * Created by wipatrick on 23.07.16.
 *
 * Note that the Kafka source is expecting the following parameters to be set
 *  - "bootstrap.servers" (comma separated list of kafka brokers)
 *  - "zookeeper.connect" (comma separated list of zookeeper servers)
 *  - "group.id" the id of the consumer group
 *  - "topic" the name of the topic to read data from.
 *
 * You can pass these required parameters using "--bootstrap.servers host:port,host1:port1 --zookeeper.connect host:port --topic testTopic"
 *
 * This is a valid input example:
 * 		--topic test --bootstrap.servers 192.168.99.100:9092 --zookeeper.connect 192.168.99.100:2181 --group.id myGroup
 *
 */
public class WriteToKafka {

    public static void main (String [] args) throws Exception {

        ParameterTool parameterTool = ParameterTool.fromArgs(args);
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // create sample data source
        DataStream<String> kafkaStream = env.addSource(new StringGenerator());

        // write data stream to Kafka
        kafkaStream.addSink(new FlinkKafkaProducer09<String>(
                parameterTool.getRequired("bootstrap.servers"),
                parameterTool.getRequired("topic"),
                new SimpleStringSchema()
        ));

        env.execute();
    }

    public static class StringGenerator implements SourceFunction<String> {

        private static final long serialVersionUID = 1L;
        boolean running = true;
        long i = 0;

        @Override
        public void run(SourceContext<String> sourceContext) throws Exception {
            while(running) {
                sourceContext.collect("element-" + (i++));
                Thread.sleep(1000);
            }
        }

        @Override
        public void cancel() {
            running = false;
        }
    }
}
