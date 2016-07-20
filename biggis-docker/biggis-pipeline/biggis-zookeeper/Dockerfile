FROM biggis/base:java8-jre-alpine

MAINTAINER wipatrick

ARG VERSION=3.4.6

RUN set -x && \
    apk --update add --virtual build-dependencies curl && \
    curl -s http://apache.mirrors.pair.com/zookeeper/zookeeper-$VERSION/zookeeper-$VERSION.tar.gz | tar -xzf - -C /opt && \
    ln -s /opt/zookeeper-$VERSION /opt/zookeeper && \
    cp /opt/zookeeper/conf/zoo_sample.cfg /opt/zookeeper/conf/zoo.cfg && \
    sed -i -e "s/dataDir=\/tmp\/zookeeper/dataDir=\/tmp\/zookeeper\/data/g" /opt/zookeeper/conf/zoo.cfg && \
    mkdir -p /tmp/zookeeper && \
    apk del build-dependencies && \
    rm -rf /var/cache/apk/*

WORKDIR /opt/zookeeper

EXPOSE 2181 2888 3888

CMD ["bin/zkServer.sh", "start-foreground"]
