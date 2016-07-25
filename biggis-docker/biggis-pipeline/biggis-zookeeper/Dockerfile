FROM biggis/base:java8-jre-alpine

MAINTAINER wipatrick

ARG ZK_VERSION=3.4.6

ARG BUILD_DATE
ARG VCS_REF

LABEL eu.biggis-project.build-date=$BUILD_DATE \
      eu.biggis-project.license="MIT" \
      eu.biggis-project.name="BigGIS" \
      eu.biggis-project.url="http://biggis-project.eu/" \
      eu.biggis-project.vcs-ref=$VCS_REF \
      eu.biggis-project.vcs-type="Git" \
      eu.biggis-project.vcs-url="https://github.com/biggis-project/biggis-infrastructure" \
      eu.biggis-project.environment="dev" \
      eu.biggis-project.version=$ZK_VERSION

RUN set -x && \
    apk --update add --virtual build-dependencies curl && \
    curl -s http://apache.mirrors.pair.com/zookeeper/zookeeper-$ZK_VERSION/zookeeper-$ZK_VERSION.tar.gz | tar -xzf - -C /opt && \
    ln -s /opt/zookeeper-$ZK_VERSION /opt/zookeeper && \
    cp /opt/zookeeper/conf/zoo_sample.cfg /opt/zookeeper/conf/zoo.cfg && \
    sed -i -e "s/dataDir=\/tmp\/zookeeper/dataDir=\/tmp\/zookeeper\/data/g" /opt/zookeeper/conf/zoo.cfg && \
    mkdir -p /tmp/zookeeper && \
    apk del build-dependencies && \
    rm -rf /var/cache/apk/*

WORKDIR /opt/zookeeper

EXPOSE 2181 2888 3888

CMD ["bin/zkServer.sh", "start-foreground"]
