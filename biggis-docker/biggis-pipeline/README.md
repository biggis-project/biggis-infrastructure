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
![screenshot 2016-07-26 11 12 15](https://cloud.githubusercontent.com/assets/15153294/17132419/f116c4f4-5321-11e6-8790-43a7ffb50fab.png)

**Tip**: Once you stopped the running containers via ```make stop``` and you want to remove them as well as the created shared volumes and the project specific Docker network bridge on your Docker Host, you should run a ```make clean```. This way all dangling volumes under _/var/lib/docker/volumes_ are removed.

Use ```make help``` for information about the commands.

## M3 Raster-Pipeline: A High-Level Infrastructure Overview
After building the Docker images with ```make build``` and starting the BigGIS analytical Pipeline ```make up``` you are provided with the following components:
![highlevel-infrastructure](https://cloud.githubusercontent.com/assets/15153294/17130376/81b4f544-5318-11e6-83a2-0d860acf79c1.png)


As a first PoC, we want to demonstrate an integrated, end-to-end, analytical BigGIS pipeline for a specific use case. Therefore, we are considering performing a [hot spot analysis](https://pro.arcgis.com/de/pro-app/tool-reference/spatial-statistics/h-how-hot-spot-analysis-getis-ord-gi-spatial-stati.htm) on a thermal flight dataset.
<!-- The workflow of our M3 Raster-Pipeline is as follows:
1. Pre-chopped tiles of thermal flight dataset are dropped sequentially to specific data directory on the Docker Host, which the ```Collector``` container is monitoring via a simple _inotify_ script. Detected changes are fetched, such that when a new tile arrives, a new _collect-event_ is pushed to ```Kafka```. This event contains some metadata information about the file, e.g. _path/to/file_.
2. ```Flink``` consists of several individual jobs: the first job consumes the _collect-event_, loads in the raster file and normalizes. Normalized tiles are then stored under _/normtiles_ volume on the host and a new event _norm-event_ is pushed out to ```Kafka```. The second job consumes the _norm-event_, takes the normalized tiles and indexes them. These indices are stored in ```DB``` and a new event _idx-event_ is pushed out to ```Kafka```. The third job consumes the _idx-event_, loads the normalized tiles and the corresponding indices and performs a hot spot analysis, using the neighborhood information. The results are stored in ```DB```, raster tiles are  -->
![pipeline-workflow](https://cloud.githubusercontent.com/assets/15153294/17138347/1da64562-5340-11e6-9c50-9cd13d532b57.png)

## Work in progess (ToDo)
