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
| No. | Framework | Description                                                                        |
|-----|-----------|------------------------------------------------------------------------------------|
| 1   | Kafka     | Message Queue for data, information propagation.                                   |
| 2   | Zookeeper | Needed by Kafka for storing configurations.                                        |
| 3   | Flink     | Stream Processor for pre-analytical jobs, normalization, transformation.           |
| 4   | Hadoop    | HDFS for storing raster data such as satellite images, thermal flight images, etc. |

## Database schema for indexing the tiles
We are using dockerized postgres + postgis for M3.
Later, we will replace the database with dockerized Exasolution database.

We can use a readily available docker image containing postgres+postgis as follows:
``` sh
# the -P option maps postgress port to some high port in the host
# this is useful for development e.g. using pgAdmin
docker run --name biggis-db -P -e POSTGRES_PASSWORD=[secretpasswd] -d mdillon/postgis
```

To see which port is mapped to the host:
``` sh
docker ps
```

Here is the database schema:
``` sql
CREATE EXTENSION postgis; -- activates postgis
CREATE DATABASE tiledb;
drop table if exists tiles; -- cleanup
create table tiles (
  tileid serial primary key,
  fname varchar(100) unique not null, -- image location
  
  -- the map extend of this tile
  extent geometry,
  
  -- the tile has to be regenerated if something within update_area
  -- and in a time frame between now and update_past
  update_area geometry,
  update_past timestamp,
  
  ts timestamp, -- time dimension of the tile
  ts_idx timestamp, -- when the tile was indexed
  
  -- to distinguish amongst different data sources
  source int
);
```



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
