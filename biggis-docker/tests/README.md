## Some tests for the BigGIS-Pipeline

1. Kafka-Flink
2. Kafka-Tomcat

### 1. Kafka-Flink Integration

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

### 2. Kafka-Tomcat Integration
Start Kafka and Tomcat.
```sh
$ export USER_ID=`id -u $USER`
$ docker-compose up -d kafka tomcat
```
Kafka, Zookeeper and Tomcat should be running in a container.
```sh
$ docker-compose ps
           Name                         Command               State                     Ports
----------------------------------------------------------------------------------------------------------------
biggispipeline_kafka_1       /usr/local/bin/entrypoint. ...   Up      7203/tcp, 0.0.0.0:9092->9092/tcp
biggispipeline_tomcat_1      /usr/local/bin/entrypoint. ...   Up      0.0.0.0:8080->8080/tcp
biggispipeline_zookeeper_1   /usr/local/bin/entrypoint. ...   Up      0.0.0.0:2181->2181/tcp, 2888/tcp, 3888/tcp
```
Test, that Kafka is running correctly by (1) creating a topic, e.g. _api_.
```sh
$ ./biggis-docker/tests/kafka/kafkaCreateTopic.sh api
Starting with UID : 9001
Created topic "api".
```
You'll find a simple Java Maven project under _tests/tomcat_, which provides a simple RESTful api that implements Kafka's Java Producer Client API. The java app takes HTTP POST requests and sends them to Kafka via topic _api_. To build the project simply type the following Maven command.
```sh
cd biggis-docker/tests/tomcat
mvn clean package
```
To deploy the built war-file to the running Tomcat instance use ```deploy.sh```.
***Note***: you will need to pass the IP of your Docker Host as the second argument.
```sh
$ ./deploy.sh target/tomcat-api.war 192.168.99.100
OK - Deployed application at context path /api
```
Start a simple Kafka consumer in a new terminal window subscribing the topic _api_.
```sh
$ ./biggis-docker/tests/kafkaConsumer.sh api
```
Start simulating a sensor stream by using ```simulate.sh```. The following command sends 60 messages every second to our RESTful service running in the Tomcat Docker container, which parses the input and propagates it further to Kafka.
***Note***: You will need to pass three arguments, (1) number of messages to be sent (2) message interval in seconds (3) Docker Host IP
```sh
./simulate.sh 60 1 192.168.99.100
The counter is 0
{
  "aid": "aggr-001",
  "sid": "sens-001",
  "lat": null,
  "lon": null,
  "alt": null,
  "epoch": 1471262595,
  "temp": 16.0,
  "humid": 0.82
}The counter is 1
...
```
If everything is running smoothly, you should see some messages starting to appear in the Kafka conumser terminal window.
```sh
aggr-001,sens-001,null,null,null,1471262595,16.0,0.82
...
```
Stop the the simple consumer (```ctrl+c```) and stop the whole pipeline.
```sh
$ docker-compose stop
$ make clean
```
Additionally, the Java based Maven project provides a ```SimpleKafkaConsumer``` class, that one can use to consume messages from a Kafka queue.
