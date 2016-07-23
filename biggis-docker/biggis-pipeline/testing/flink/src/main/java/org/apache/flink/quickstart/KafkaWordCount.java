package org.apache.flink.quickstart;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer09;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer09;
import org.apache.flink.streaming.util.serialization.SerializationSchema;
import org.apache.flink.streaming.util.serialization.SimpleStringSchema;
import org.apache.flink.util.Collector;

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
 * 		--topicIn testIn --topicOut testOut --bootstrap.servers 192.168.99.100:9092 --zookeeper.connect 192.168.99.100:2181 --group.id myGroup
 *
 */
public class KafkaWordCount {

    public static void main(String [] args) throws Exception {

        ParameterTool parameterTool = ParameterTool.fromArgs(args);
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // read input data from Kafka
        DataStream<Tuple2<String, Integer>> counts = env
                .addSource(new FlinkKafkaConsumer09<>(
                        parameterTool.getRequired("topicIn"),
                        new SimpleStringSchema(),
                        parameterTool.getProperties()))
                .flatMap(new LineSplitter())
                .keyBy(0)
                .sum(1);

        // execute and print result
        //counts.print();

        // write analyzed results backs to Kafka
        counts.addSink(new FlinkKafkaProducer09<Tuple2<String, Integer>>(
                parameterTool.getRequired("bootstrap.servers"),
                parameterTool.getRequired("topicOut"),
                new UDFSchema()
        ));

        env.execute("Simple KafkaWordCount");
    }

    // user-defined function
    /**
     * Implements the string tokenizer that splits sentences into words as a user-defined
     * FlatMapFunction. The function takes a line (String) and splits it into
     * multiple pairs in the form of "(word,1)" (Tuple2<String, Integer>).
     */
    public static final class LineSplitter implements FlatMapFunction<String, Tuple2<String, Integer>> {

        @Override
        public void flatMap(String value, Collector<Tuple2<String, Integer>> out) {
            // normalize and split the line
            String[] tokens = value.toLowerCase().split("\\W+");

            // emit the pairs
            for (String token : tokens) {
                if (token.length() > 0) {
                    out.collect(new Tuple2<String, Integer>(token, 1));
                }
            }
        }
    }

    public static class UDFSchema implements SerializationSchema<Tuple2<String, Integer>> {

        @Override
        public byte[] serialize(Tuple2<String, Integer> wordcount) {
            return (wordcount.toString()).getBytes();
        }
    }
}
