package org.apache.flink.quickstart;

import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer09;
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
public class ReadFromKafka {

    public static void main (String [] args ) throws Exception {

        // parse user parameters
        ParameterTool parameterTool = ParameterTool.fromArgs(args);

        // create execution environment
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStream<String> kafkaStream = env
                .addSource(new FlinkKafkaConsumer09<>(
                        parameterTool.getRequired("topic"),
                        new SimpleStringSchema(),
                        parameterTool.getProperties()
                ));

        // print() will write the contents of the stream to the TaskManager's standard out stream
        kafkaStream.print();

        env.execute();
    }
}
