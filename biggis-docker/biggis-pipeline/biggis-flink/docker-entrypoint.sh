#!/bin/sh

if [ "$1" = "jobmanager" ]; then
    echo "Starting Job Manager"
    # making use of docker to resolve container name
    sed -i -e "s/jobmanager.rpc.address: .*/jobmanager.rpc.address: jobmanager/g" $FLINK_HOME/conf/flink-conf.yaml
    echo "config file: " && grep '^[^\n#]' $FLINK_HOME/conf/flink-conf.yaml
    $FLINK_HOME/bin/jobmanager.sh start cluster

# used for running flink on docker swarm 1.12
# only tested with 1 jobmanager setup
elif [ "$1" = "jobmanager-swarm" ]; then
    echo "Starting Job Manager"
    # making use of docker swarms internal dns service
    sed -i -e "s/jobmanager.rpc.address: .*/jobmanager.rpc.address: tasks.jobmanager/g" $FLINK_HOME/conf/flink-conf.yaml
    echo "config file: " && grep '^[^\n#]' $FLINK_HOME/conf/flink-conf.yaml
    $FLINK_HOME/bin/jobmanager.sh start cluster

# used for running flink on docker swarm 1.12
# only tested with 1 jobmanager setup
elif [ "$1" = "jobmanager-mesos" ]; then
    echo "Starting Job Manager"
    # making use of docker swarms internal dns service
    sed -i -e "s/jobmanager.rpc.address: .*/jobmanager.rpc.address: jobmanager.marathon.mesos/g" $FLINK_HOME/conf/flink-conf.yaml
    echo "config file: " && grep '^[^\n#]' $FLINK_HOME/conf/flink-conf.yaml
    $FLINK_HOME/bin/jobmanager.sh start cluster

elif [ "$1" = "taskmanager" ]; then
    echo "Starting Task Manager"
    # making use of docker to resolve container name
    sed -i -e "s/jobmanager.rpc.address: .*/jobmanager.rpc.address: jobmanager/g" $FLINK_HOME/conf/flink-conf.yaml
    sed -i -e "s/taskmanager.numberOfTaskSlots: 1/taskmanager.numberOfTaskSlots: `grep -c ^processor /proc/cpuinfo`/g" $FLINK_HOME/conf/flink-conf.yaml
    echo "config file: " && grep '^[^\n#]' $FLINK_HOME/conf/flink-conf.yaml
    $FLINK_HOME/bin/taskmanager.sh start

# used for running flink on docker swarm 1.12
# only tested with 1 jobmanager setup
elif [ "$1" = "taskmanager-swarm" ]; then
    echo "Starting Task Manager"
    # making use of docker swarms internal dns service
    sed -i -e "s/jobmanager.rpc.address: .*/jobmanager.rpc.address: tasks.jobmanager/g" $FLINK_HOME/conf/flink-conf.yaml
    sed -i -e "s/taskmanager.numberOfTaskSlots: 1/taskmanager.numberOfTaskSlots: `grep -c ^processor /proc/cpuinfo`/g" $FLINK_HOME/conf/flink-conf.yaml
    echo "config file: " && grep '^[^\n#]' $FLINK_HOME/conf/flink-conf.yaml
    $FLINK_HOME/bin/taskmanager.sh start

# this was used for a small test to try running Flink via Marathon on Mesos
# only tested with 1 jobmanager setup
elif [ "$1" = "taskmanager-mesos" ]; then
    echo "Starting Task Manager"
    # using mesos-dns to resolve jobmanager
    # make sure to name the jobmanager in marathon
    sed -i -e "s/jobmanager.rpc.address: localhost/jobmanager.rpc.address: jobmanager.marathon.mesos/g" $FLINK_HOME/conf/flink-conf.yaml
    sed -i -e "s/taskmanager.numberOfTaskSlots: 1/taskmanager.numberOfTaskSlots: `grep -c ^processor /proc/cpuinfo`/g" $FLINK_HOME/conf/flink-conf.yaml
    echo "config file: " && grep '^[^\n#]' $FLINK_HOME/conf/flink-conf.yaml
    $FLINK_HOME/bin/taskmanager.sh start

else
    $@
fi
