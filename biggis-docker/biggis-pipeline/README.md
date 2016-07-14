## Getting started
```
➜  biggis-pipeline git:(master) export USER_ID=`id -u $USER`
➜  biggis-pipeline git:(master) docker-compose up -d
Creating volume "biggispipeline_mysql-data" with default driver
Creating volume "biggispipeline_flink-conf" with default driver
Creating volume "biggispipeline_storage-normtiles" with default driver
Creating volume "biggispipeline_storage-hstiles" with default driver
Creating volume "biggispipeline_storage-rasteringest" with default driver
Creating biggispipeline_zookeeper_1
Creating biggispipeline_db_1
Creating biggispipeline_kafka_1
Creating biggispipeline_jobmanager_1
Creating biggispipeline_collector_1
Creating biggispipeline_taskmanager_1
➜  biggis-pipeline git:(master) docker ps
CONTAINER ID        IMAGE                      COMMAND                  CREATED             STATUS              PORTS                              NAMES
af9e9eb7af63        biggis/flink:1.0.3         "/usr/local/bin/entry"   5 seconds ago       Up 5 seconds                                           biggispipeline_taskmanager_1
6228f7e6c7a4        biggis/collector:0.9.0.0   "/usr/local/bin/entry"   6 seconds ago       Up 5 seconds        7203/tcp, 9092/tcp                 biggispipeline_collector_1
187f11c605c9        biggis/flink:1.0.3         "/usr/local/bin/entry"   6 seconds ago       Up 5 seconds        0.0.0.0:8081->8081/tcp             biggispipeline_jobmanager_1
7f7591c5543d        biggis/kafka:0.9.0.0       "/usr/local/bin/entry"   6 seconds ago       Up 6 seconds        7203/tcp, 0.0.0.0:9092->9092/tcp   biggispipeline_kafka_1
6f436fd89c46        biggis/mysql:10.1          "mysqld --skip-grant-"   7 seconds ago       Up 6 seconds        0.0.0.0:3306->3306/tcp             biggispipeline_db_1
0581fd88b2a6        biggis/zookeeper:3.4.6     "/usr/local/bin/entry"   7 seconds ago       Up 6 seconds        2181/tcp, 2888/tcp, 3888/tcp       biggispipeline_zookeeper_1
```

## Work in progess (ToDo)
1. Write ```startup.sh``` for exposing neccessary env variables (${USER_ID}).
