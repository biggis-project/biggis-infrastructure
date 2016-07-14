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
| No.   | Framework      | Image                        | Description                                                                                                                                                            |
|-------|----------------|------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| 0a)   | -              | biggis/base:alpine-3.4       | Same as official alpine:3.4 image only tagged in biggis-schema.                                                                                                        |
| 0     | -              | biggis/base:java8-jre-alpine | Minimal base image. Inherits from java:8-jre-alpine image and adds bash, curl, snappy, supervisor, gosu.  Additionally, it handles user permissions in shared volumes. |
| 1     | -              | biggis/collector:0.9.0.0     | Inherits from biggis/kafka:0.9.0.0 and uses inotifywait to watch for file creation.                                                                                    |
| 2     | Kafka          | biggis/kafka:0.9.0.0         | Message Queue for data, information propagation.                                                                                                                       |
| 3     | Zookeeper      | biggis/zookeeper:3.4.6       | Needed by Kafka for storing configurations, leader election, state.                                                                                                    |
| 4     | Flink          | biggis/flink:1.0.3           | Stream Processor for pre-analytical jobs, normalization, transformation.                                                                                               |
| 5     | MySQL          | biggis/mysql:10.1            | Used for storing indices etc.                                                                                                                                          |
| ~~6~~ | ~~PostgreSQL~~ | ~~biggis/postgres:9.5~~      | ~~Simple PostgreSQL db.~~                                                                                                                                              |
| ~~6~~ | ~~PostGIS~~    | ~~biggis/postgis:9.5-2.2~~   | ~~Inherits from biggis/postgres:9.5.~~                                                                                                                                 |
| ~~7~~ | ~~Hadoop~~     | -                            | ~~HDFS for storing raster data such as satellite images, thermal flight images, etc.~~                                                                                 |

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

## Useful queries
``` sql
-- find all tiles "a" that are affected
-- by change in tile "b" (here with tileid=3)
SELECT
    a.*
FROM
    tiles AS a,
    tiles AS b
WHERE
    ST_INTERSECTS(a.update_area, b.extent)
        AND a.tileid <> b.tileid
        AND b.tileid = 3
```

## Sample data
``` sql
insert into tiles(tileid, fname, extent, update_area, ts, ts_idx) values
(2, "test2",
ST_PolygonFromText('polygon((0 0, 1 0, 1 1, 0 1, 0 0))'),
ST_PolygonFromText('polygon((-1 -1, 2 -1, 2 2, -1 2, -1 -1))'),
"2016-07-10 00:00:00", "2016-07-10 00:00:01"),
(3, "test3",
ST_PolygonFromText('polygon((1 0, 2 0, 2 1, 1 1, 1 0))'),
ST_PolygonFromText('polygon((0 -1, 3 -1, 3 2, 0 2, 0 -1))'),
"2016-07-10 00:00:00", "2016-07-10 00:00:01");
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
