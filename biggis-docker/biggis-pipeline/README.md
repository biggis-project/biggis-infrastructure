## biggis-pipeline
The BigGIS analytical pipeline is a simple, docker based big data pipeline for PoC's, demos and milestone meetings.

## What is BigGIS?
BigGIS is a new generation of GIS that supports decision making in multiple scenarios which require processing of large and heterogeneous data sets. The novelty lies in an integrated analytical approach to spatio-temporal data, that are unstructured and from unreliable sources. The system provides predictive, prescriptive and visual tool integrated in a common analytical pipeline. The prototype will be evaluated on three scenarios - “Smart city”, “Environmental management” and “Disaster control”.

## Prerequisites
- Tested on Ubuntu/CentOS and Mac (with Docker Machine 0.7.0, VirtualBox 5+)
- Docker Engine 1.10.0+
- Docker Compose 1.6.0+

For detailed description please refer to [https://docs.docker.com/](https://docs.docker.com/). As of now, native Docker support for Windows and Mac users is only in beta version. I suggest you to use [Docker Toolbox](https://docs.docker.com/toolbox/overview/) to get you up and running.

## Components
| No.   | Framework      | Image                        | Description                                                                                                               |
|-------|----------------|------------------------------|---------------------------------------------------------------------------------------------------------------------------|
| 0a)   | -              | biggis/base:alpine-3.4       | Official alpine:3.4 image + gosu + UID handling                                                                           |
| 0b)   | -              | biggis/base:java8-jre-alpine | Official java:8-jre-alpine image + bash, gosu + UID handling                                                              |
| 1     | -              | biggis/collector:0.9.0.0     | Inherits from biggis/kafka:0.9.0.0 and uses inotifywait to watch for file creation. This event is pushed to Kafka.        |
| 2     | Kafka          | biggis/kafka:0.9.0.0         | Message Queue for data, information propagation.                                                                          |
| 3     | Zookeeper      | biggis/zookeeper:3.4.6       | Needed by Kafka for storing configurations, leader election, state.                                                       |
| 4     | Flink          | biggis/flink:1.0.3           | Stream processor for pre-analytical jobs, which consumes event streams from Kafka for normalizations and transformations. |
| 5     | MariaDB        | biggis/mariadb:10.1          | Used for storing tile indices.                                                                                            |

## Usage
Seamlessly execute commands using ```make``` and ```docker```.
```sh
# Build images locally
$ make build

# Deploy containers
$ make up

# Stop running containers
$ make stop

# Start stopped containers
$ make start

# Remove created volumes and network
$ make clean
```
Use ```make list``` to list all running container instances.
```sh
$ make list
Name                          Command               State                       Ports
----------------------------------------------------------------------------------------------------------------------
biggispipeline_collector_1     /usr/local/bin/entrypoint. ...   Up      7203/tcp, 9092/tcp
biggispipeline_db_1            /usr/local/bin/entrypoint. ...   Up      0.0.0.0:3306->3306/tcp
biggispipeline_jobmanager_1    /usr/local/bin/entrypoint. ...   Up      0.0.0.0:6123->6123/tcp, 0.0.0.0:8081->8081/tcp
biggispipeline_kafka_1         /usr/local/bin/entrypoint. ...   Up      7203/tcp, 9092/tcp
biggispipeline_taskmanager_1   /usr/local/bin/entrypoint. ...   Up
biggispipeline_zookeeper_1     /usr/local/bin/entrypoint. ...   Up      2181/tcp, 2888/tcp, 3888/tcp
```
Use ```make help``` for information about the commands.


## Work in progess (ToDo)
2. Provide sample Flink job (_testing/flink_) on how to communicate with Kafka.
