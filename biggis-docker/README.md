# biggis-docker

<!-- [![Build Status](https://travis-ci.org/biggis-project/biggis-infrastructure.svg?branch=master)][Travis]
[![](https://img.shields.io/docker/stars/biggis/biggis-infrastructure.svg)][Dockerhub]
[![](https://img.shields.io/docker/pulls/biggis/biggis-infrastructure.svg)][Dockerhub]
[![](https://badge.imagelayers.io/biggis/biggis-infrastructure:latest.svg)][ImageLayers]

[Dockerhub]: https://hub.docker.com/r/biggis/biggis-infrastructure/
[Travis]: https://travis-ci.org/biggis-project/biggis-infrastructure
[ImageLayers]: https://imagelayers.io/?images=biggis/biggis-infrastructure:latest -->

# Notes

## Dockerized base components of BigGIS infrastructure
| No.   | Framework      | Image                        | Description                                                                            |
|-------|----------------|------------------------------|----------------------------------------------------------------------------------------|
| 0a)   | -              | biggis/base:alpine-3.4       | Official alpine:3.4 image + gosu + UID handling                                        |
| 0b)   | -              | biggis/base:java8-jre-alpine | Official java:8-jre-alpine image + bash, gosu + UID handling                           |
| 1     | -              | biggis/collector:0.9.0.0     | Inherits from biggis/kafka:0.9.0.0 and uses inotifywait to watch for file creation.    |
| 2     | Kafka          | biggis/kafka:0.9.0.0         | Message Queue for data, information propagation.                                       |
| 3     | Zookeeper      | biggis/zookeeper:3.4.6       | Needed by Kafka for storing configurations, leader election, state.                    |
| 4     | Flink          | biggis/flink:1.0.3           | Stream Processor for pre-analytical jobs, normalization, transformation.               |
| 5     | MariaDB        | biggis/mariadb:10.1          | Used for storing tile indices etc.                                                     |
| ~~6~~ | ~~PostgreSQL~~ | ~~biggis/postgres:9.5~~      | ~~Simple PostgreSQL db.~~                                                              |
| ~~6~~ | ~~PostGIS~~    | ~~biggis/postgis:9.5-2.2~~   | ~~Inherits from biggis/postgres:9.5.~~                                                 |
| ~~7~~ | ~~Hadoop~~     | -                            | ~~HDFS for storing raster data such as satellite images, thermal flight images, etc.~~ |

## Database schema for indexing the tiles
We are using dockerized **mysql** or **postgres/postgis** for M3.
Later, we will replace the database with dockerized Exasolution database.

We can use a readily available docker image containing **mysql** as follows:
``` sh
# the -P option maps database server port to some high port in the host
# this is useful for development e.g. using MySQL Workbench
docker run --name biggis-mysql -P -e MYSQL_ROOT_PASSWORD=[secretpasswd] -d mysql:5.7.13
```

We can use a readily available docker image containing **postgres+postgis** as follows:
``` sh
# the -P option maps database server port to some high port in the host
# this is useful for development e.g. using pgAdmin
docker run --name biggis-postgres -P -e POSTGRES_PASSWORD=[secretpasswd] -d mdillon/postgis
```

To see which port is mapped to the host:
``` sh
docker ps
```

- see also [biggis-project/biggis-tiledb](https://github.com/biggis-project/biggis-tiledb/)


<!-- ## Tagging scheme
- Tagging scheme makes use of immutable infrastructure pattern:
  - `<travis-build-#> - <github-branch> - <committer> . <first-8-chars-github-commit-hash>`

## Building docker images

When building docker images for a service it's usually quite common to start out from a base image like ubuntu (~188MB) or centos (~172MB).

However these base images are considered to be 'fat' as they contain various things your application/service might not need but increases your image size significantly.

So like in development when stripping down your code in order to be more efficient, start off from a minimal base image (e.g. Busybox ~2MB, Alpine ~5MB, Debian ~125MB) in order to make the deployment of your application/service more efficient.

see:
- https://www.brianchristner.io/docker-image-base-os-size-comparison/
- http://www.iron.io/microcontainers-tiny-portable-containers/
- https://github.com/iron-io/dockers

Additionally, there are some other important things one has to consider when building a docker image as pointed out below:

see:
- http://phusion.github.io/baseimage-docker/

So the ```phusion/baseimage:<VERSION>``` is a perfect example of a good docker base image. -->
