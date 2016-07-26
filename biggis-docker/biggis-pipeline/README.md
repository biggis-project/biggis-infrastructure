## biggis-pipeline
The BigGIS analytical pipeline is a simple, docker based big data pipeline for PoC's, demos and milestone meetings.

## Prerequisites
- Tested on Ubuntu/CentOS and Mac (with Docker Machine 0.7.0, VirtualBox 5+)
- Docker Engine 1.10.0+
- Docker Compose 1.6.0+

For detailed description please refer to [https://docs.docker.com/](https://docs.docker.com/). As of now, native Docker support for Windows and Mac users is only in beta version. I suggest you to use [Docker Toolbox](https://docs.docker.com/toolbox/overview/) to get you up and running.

**Tip**: It is recommended to read the Docker documenation to get a basic understanding of Docker containers, Docker container networking, Docker data volumes.

## Components
| No.   | Framework                                  | Image                        | Description                                                                                                               |
|-------|--------------------------------------------|------------------------------|---------------------------------------------------------------------------------------------------------------------------|
| 0a)   | -                                          | biggis/base:alpine-3.4       | Official alpine:3.4 image + gosu + UID handling                                                                           |
| 0b)   | -                                          | biggis/base:java8-jre-alpine | Official java:8-jre-alpine image + bash, gosu + UID handling                                                              |
| 1     | -                                          | biggis/collector:0.9.0.0     | Inherits from biggis/kafka:0.9.0.0 and uses inotifywait to watch for file creation. This event is pushed to Kafka.        |
| 2     | [Kafka](http://kafka.apache.org/)          | biggis/kafka:0.9.0.0         | Message Queue for data, information propagation.                                                                          |
| 3     | [Zookeeper](https://zookeeper.apache.org/) | biggis/zookeeper:3.4.6       | Needed by Kafka for storing configurations, leader election, state.                                                       |
| 4     | [Flink](https://flink.apache.org/)         | biggis/flink:1.0.3           | Stream processor for pre-analytical jobs, which consumes event streams from Kafka for normalizations and transformations. |
| 5     | [MariaDB](https://mariadb.org/)            | biggis/mariadb:10.1          | Used for storing tile indices for M3. To be replaced with Exasolution database.                                           |

## Usage
We are using Docker Compose to automatically spin up a multi-container ecosystem forming the BigGIS analytical pipeline of the aforementioned components. To get you going more easily in the beginning, the commands specifieds in the ```Makefile``` will help you to perform the very basic life cycle steps of docker containers seamlessly.

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

Once everything is up and running, you are provided with a full stack of integrated big data frameworks. E.g., you can visit the Flink UI on ```http://<DOCKER_HOST_IP>:8081```. The _&lt;DOCKER_HOST_IP&gt;_ is the IP where your Docker Engine is running on, which can be (1) _localhost_ or _127.0.0.1_ if you are running under Linux or (2) the IP of your virtual machine, e.g. _192.168.99.100_ if you used Docker Machine to provision a VirtualBox instance. Use ```make list``` to list all running container instances.
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

**Tip**: Once you stopped the running containers via ```make stop``` and you want to remove them as well as the created shared volumes and the project specific Docker network bridge on your Docker Host, you should run a ```make clean```. This way all dangling volumes under _/var/lib/docker/volumes_ are removed.

Use ```make help``` for information about the commands.

## M3 Raster-Pipeline: A High-Level Infrastructure Overview
[highlevel-infrastructure.pdf](https://github.com/biggis-project/biggis-infrastructure/files/383315/highlevel-infrastructure.pdf)


## Work in progess (ToDo)
