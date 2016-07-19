# Forked from https://github.com/jplock/docker-zookeeper
# and adjusted to alpine base image
FROM biggis/base:java8-jre-alpine

MAINTAINER wipatrick

ARG VERSION=3.4.6

RUN set -x && \
    apk --update add --virtual build-dependencies curl && \
    curl -s http://apache.mirrors.pair.com/zookeeper/zookeeper-$VERSION/zookeeper-$VERSION.tar.gz | tar -xzf - -C /opt && \
    ln -s /opt/zookeeper-$VERSION /opt/zookeeper && \
    cp /opt/zookeeper/conf/zoo_sample.cfg /opt/zookeeper/conf/zoo.cfg && \
    mkdir -p /tmp/zookeeper && \
    apk del build-dependencies && \
    rm -rf /var/cache/apk/*

WORKDIR /opt/zookeeper

VOLUME ["/opt/zookeeper", "/tmp/zookeper"]

EXPOSE 2181 2888 3888

CMD ["bin/zkServer.sh", "start-foreground"]
