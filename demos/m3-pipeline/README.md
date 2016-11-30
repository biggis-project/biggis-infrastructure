## M3 BigGIS pipeline
The M3 BigGIS pipeline is a simple, docker based big data pipeline.

## Prerequisites
- Tested on Ubuntu/CentOS and Mac (with Docker Machine 0.7.0, VirtualBox 5+)
- Docker Engine 1.10.0+
- Docker Compose 1.9.0+

For detailed description please refer to [https://docs.docker.com/](https://docs.docker.com/). As of now, native Docker support for Windows and Mac users is only in beta version. I suggest you to use [Docker Toolbox](https://docs.docker.com/toolbox/overview/) to get you up and running.

**Tip**: It is recommended to read the Docker documentation to get a basic understanding of Docker containers, Docker container networking, Docker data volumes.

## Environment
| Component                                             | Image                                                                                                                                                                                                         |
|-------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| [Kafka 0.9.0.0](http://kafka.apache.org/)             | [![Build Status](https://api.travis-ci.org/biggis-project/biggis-kafka.svg)](https://travis-ci.org/biggis-project/biggis-kafka) [kafka 0.9.0.0](https://github.com/biggis-project/biggis-kafka)               |
| [Zookeeper 3.4.6](https://zookeeper.apache.org/)      | [![Build Status](https://api.travis-ci.org/biggis-project/biggis-zookeeper.svg)](https://travis-ci.org/biggis-project/biggis-zookeeper) [zookeeper 3.4.6](https://github.com/biggis-project/biggis-zookeeper) |
| [Flink 1.1.3 (Scala 2.11)](https://flink.apache.org/) | [![Build Status](https://api.travis-ci.org/biggis-project/biggis-base.svg)](https://travis-ci.org/biggis-project/biggis-flink) [flink 1.1.3](https://github.com/biggis-project/biggis-flink)                  |
| [Tomcat 8.0.36](http://tomcat.apache.org/)            | [![Build Status](https://api.travis-ci.org/biggis-project/biggis-tomcat.svg)](https://travis-ci.org/biggis-project/biggis-tomcat) [tomcat 8.0.36](https://github.com/biggis-project/biggis-tomcat)            |
| [MariaDB 10.1.19](https://mariadb.org/)               |  only used for PoC, not relevant within BigGIS                                                                                                                                                                |

## Usage

### General
We are using Docker Compose to automatically spin up a multi-container ecosystem forming the BigGIS analytical pipeline of the aforementioned components.

**Note**: If you are not running native Linux as a Docker Host make sure that you set the env variables correctly to communicate with the Docker daemon by running the following:
```sh
$ eval $(docker-machine env)
```
Besides, if you want to interact with the Kafka container from external sources, e.g. for testing from within your IDE (see the example down later), you will need to specify the following entry in the ```docker-compose.yml``` according to your Docker Host IP address in order to make it work:
```yaml
# Using Docker Machine:
# docker-machine ip <name-of-machine>
#
# On native Linux:
# ip route get 1 | awk '{print $NF;exit}'
KAFKA_ADVERTISED_HOST_NAME: <DOCKER_HOST_IP>
```

To get going you simply need to run the following commands:
```sh
# Pull images locally
$ docker-compose pull

# Deploy containers
$ docker-compose up -d

# Stop running containers
$ docker-compose stop

# Remove created volumes and network
$ sh clean.sh
```

Once everything is up and running, you are provided with a full stack of integrated big data frameworks. E.g., you can visit the Flink UI on ```http://<DOCKER_HOST_IP>:8081```. The _&lt;DOCKER_HOST_IP&gt;_ is the IP where your Docker Engine is running on, which can be (1) _localhost_ or _127.0.0.1_ if you are running under Linux or (2) the IP of your virtual machine, e.g. _192.168.99.100_ if you used Docker Machine to provision a VirtualBox instance. Use ```docker-compose ps``` to list all running container instances.
```sh
$ docker-compose ps
Name                          Command               State                       Ports
----------------------------------------------------------------------------------------------------------------------
m3pipeline_collector_1     /usr/local/bin/entrypoint. ...   Up      7203/tcp, 9092/tcp
m3pipeline_db_1            /usr/local/bin/entrypoint. ...   Up      0.0.0.0:3306->3306/tcp
m3pipeline_jobmanager_1    /usr/local/bin/entrypoint. ...   Up      0.0.0.0:6123->6123/tcp, 0.0.0.0:8081->8081/tcp
m3pipeline_kafka_1         /usr/local/bin/entrypoint. ...   Up      7203/tcp, 0.0.0.0:9092->9092/tcp
m3pipeline_taskmanager_1   /usr/local/bin/entrypoint. ...   Up
m3pipeline_tomcat_1        /usr/local/bin/entrypoint. ...   Up      0.0.0.0:8080->8080/tcp
m3pipeline_zookeeper_1     /usr/local/bin/entrypoint. ...   Up      0.0.0.0:2181->2181/tcp, 2888/tcp, 3888/tcp
```
![image](https://cloud.githubusercontent.com/assets/15153294/17624492/23d198b6-60a5-11e6-8d50-b37dd1039cca.png)

**Tip**: Once you stopped the running containers and you want to remove them as well as the created shared volumes and the project specific Docker network bridge on your Docker Host, you should run a ```sh clean.sh```. This way all dangling volumes under _/var/lib/docker/volumes_ are removed.

### Specific Services Only
Even though launching the full BigGIS analytical pipeline only takes a couple of seconds, sometimes you only want to do some quick testings with only a couple of services running, e.g. consuming and producing data from/to Kafka while your developping a Flink Job inside your IDE. You are able to pass a ```service``` argument to ```docker-compose up -d <service1> <service2>```, specifying the services you want to launch.

**Note**: The frameworks running inside these Docker containers do have certain dependencies to other frameworks, e.g. Kafka needs a running Zookeeper instance. Thus, ```docker-compose up -d kafka``` will also start up Zookeeper. The name of a service is directly derived from the wording inside the ```docker-compose.yml```.

## M3 Raster-Pipeline: A High-Level Infrastructure Overview
After pulling the Docker images and starting the BigGIS analytical Pipeline you are provided with the following components:
![highlevel-infrastructure_v3](https://cloud.githubusercontent.com/assets/15153294/17623805/32163eca-60a2-11e6-9657-1e16ed9f0ad5.png)


As a first PoC, we want to demonstrate an integrated, end-to-end, analytical BigGIS pipeline for a specific use case. Therefore, we are considering performing a [hot spot analysis](https://pro.arcgis.com/de/pro-app/tool-reference/spatial-statistics/h-how-hot-spot-analysis-getis-ord-gi-spatial-stati.htm) on a thermal flight dataset. The workflow of our M3 Raster-Pipeline is as follows:

1. Pre-chopped tiles of thermal flight dataset are dropped sequentially to specific tile directory _/tiles/&lt;id&gt;ingest.tif_ on the Docker Host, which the ```Collector``` container is monitoring via a simple _inotify_ script. Detected changes are fetched, such that when a new tile arrives, a new _collect-event_ is pushed to ```Kafka```. This event contains some metadata information about the file, e.g. _path/to/tile_.
2. ```Flink``` consists of several individual jobs: the first job consumes the _collect-event_, loads in the raster file and normalizes. Normalized tiles are then stored under _/tiles/&lt;id&gt;norm.tif_ volume on the host and a new event _norm-event_ is pushed out to ```Kafka```. The second job consumes the _norm-event_, takes the normalized tiles and indexes them. These indices are stored in ```DB``` and a new event _idx-event_ is pushed out to ```Kafka```. The third job consumes the _idx-event_, loads the normalized tiles and the corresponding indices and performs a hot spot analysis, using the neighborhood information. The results (z-scores) are pushed in ```DB```, new hot spot raster tiles are stored under _/tiles/&lt;id&gt;hotspot.tif_ and a new event _hs-event_ is pushed to ```Kafka```. The last job consumes the _hs-event_, uses the hot spot raster tiles as well as the z-scores and ranks them accordingly. The resulting dataset contains TopK hot spot (cold spot) locations in the given area, which is again stored in the ```DB```. Eventually, a _topk-event_ is committed to ```Kafka```.
3. The creation of a new (part) of raster image is initialized by receiving the _topk-event_, loading the geolocations from ```DB``` and the hot spot tiles from _/tiles/&lt;id&gt;hotspot.tif_.
4. The newly updated tiles are then mapped together and a hollistic image of a specific area is generated and stored on disk. A new event _genimg-event_ is pushed to ```Kafka```.
5. Lastly, a Tomcat based web app registers the _genimg-event_, extracts the filesystem path where the newly generated image is stored off the Kafka message and updated the displays this image in a browser. Old images are thus periodically updated.

![pipeline-workflow_v3](https://cloud.githubusercontent.com/assets/15153294/17624309/523f2534-60a4-11e6-82e6-da6d923d98f1.png)

## Work in progess (ToDo)
