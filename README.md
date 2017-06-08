# BigGIS infrastructure

### Current Container environment

* [![Build Status](https://api.travis-ci.org/biggis-project/biggis-base.svg)](https://travis-ci.org/biggis-project/biggis-base) [biggis/base:alpine-3.4](https://github.com/biggis-project/biggis-base),[biggis/base:java8-jre-alpine](https://github.com/biggis-project/biggis-base), [biggis/base:oraclejava8-jre](https://github.com/biggis-project/biggis-base)
* [![Build Status](https://api.travis-ci.org/biggis-project/biggis-hdfs.svg)](https://travis-ci.org/biggis-project/biggis-hdfs) [biggis/hdfs:2.7.1](https://github.com/biggis-project/biggis-hdfs)
* [![Build Status](https://api.travis-ci.org/biggis-project/biggis-kafka.svg)](https://travis-ci.org/biggis-project/biggis-kafka) [biggis/kafka:0.10.1.1](https://github.com/biggis-project/biggis-kafka)
* [![Build Status](https://api.travis-ci.org/biggis-project/biggis-zookeeper.svg)](https://travis-ci.org/biggis-project/biggis-zookeeper) [biggis/zookeeper:3.4.9](https://github.com/biggis-project/biggis-zookeeper)
* [![Build Status](https://api.travis-ci.org/biggis-project/biggis-base.svg)](https://travis-ci.org/biggis-project/biggis-flink) [biggis/flink:1.3.0](https://github.com/biggis-project/biggis-flink)
* [![Build Status](https://api.travis-ci.org/biggis-project/biggis-spark.svg)](https://travis-ci.org/biggis-project/biggis-spark) [biggis/spark:2.1.0](https://github.com/biggis-project/biggis-spark)
* [![Build Status](https://api.travis-ci.org/biggis-project/biggis-tomcat.svg)](https://travis-ci.org/biggis-project/biggis-tomcat) [biggis/tomcat:8.0.36](https://github.com/biggis-project/biggis-tomcat)
* [EXASOL/docker-db](https://github.com/EXASOL/docker-db)

### Additional Container

**Note**: These container were only relevant for small testing purposes in the early phases of BigGIS or for small demos
* [![Build Status](https://api.travis-ci.org/biggis-project/biggis-postgres.svg)](https://travis-ci.org/biggis-project/biggis-postgres) [biggis/postgres:9.6](https://github.com/biggis-project/biggis-postgres)

## BigGIS demos
Docker based analytical BigGIS pipeline for demos, milestones, PoC's.

## BigGIS playbooks
Various Ansible playbooks to setup and provision CentOS 7 and Ubuntu 14.04 cluster with BigGIS container management and deployment applications.

## BigGIS templates
BigGIS infrastructure template for running a BigGIS cluster on [Rancher](http://docs.rancher.com/rancher/) Cattle.
