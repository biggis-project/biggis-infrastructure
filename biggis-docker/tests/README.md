## Some tests for the BigGIS-Pipeline

### Starting Kafka and do some local Flink development

Start Kafka.
```sh
$ make up service=kafka
```
Test, that Kafka is running correctly by (1) creating a topic, e.g. _test_.
```sh
$ ./tests/kafka/kafkaCreateTopic.sh test
Starting with UID : 9001
Created topic "test".
```
(2) starting a simple consumer in a new terminal window.
```sh
$ ./tests/kafka/kafkaConsumer.sh test
```
(3) starting a simple producer in a new terminal window and type _hello world_.
```sh
$ ./tests/kafka/kafkaProducer.sh test
hello world
```
You will see _hello world_ pop up in your consumer window.

Import the Maven project under _tests/flink_ in your IDE. You will find three Flink Jobs written in the Java API: ```ReadFromKafka```, ```WriteToKafka``` and ```KafkaWordCount```. For the purpose of this demo, we use ```ReadFromKafka```. In order to run the Job, you need to provide the some arguments as shown in the code (IntelliJ: Run > Edit configurations > Program arguments). Then run the ```ReadFromKafka``` Flink Job.
```Java
/**
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
        kafkaStream.map(new MapFunction<String, String>() {

            private static final long serialVersionUID = -2522168019108009712L;

            @Override
            public String map(String msg) throws Exception {
                return "Kafka and Flink says: " + msg;
            }
        }).print();

        env.execute();
    }
}
```
Use the simple producer in the open terminal window to send a new message.
```sh
hello biggis
```
See the message in your IDE,
```sh
1> Kafka and Flink says: hello biggis
```
and in the open terminal window of the simple consumer.
```sh
hello world
hello biggis
```
Stop the Job, stop the the simple consumer and producer (```ctrl+c```) and stop Kafka.
```sh
$ make stop
$ make clean
```
