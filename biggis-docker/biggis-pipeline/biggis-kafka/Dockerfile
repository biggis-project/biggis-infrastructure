# FROM java:8-jre-alpine
FROM biggis/base:java8-jre-alpine
MAINTAINER wipatrick

# The Scala 2.10 build is currently recommended by the project.
ARG KAFKA_VERSION=0.9.0.0
ARG SCALA_VERSION=2.10

ENV KAFKA_RELEASE_ARCHIVE kafka_${SCALA_VERSION}-${KAFKA_VERSION}.tgz

RUN set -x && \
    apk --update add --virtual build-dependencies curl && \
    curl -s http://apache.openmirror.de/kafka/${KAFKA_VERSION}/${KAFKA_RELEASE_ARCHIVE} | tar -xzf - -C /opt && \
    ln -s /opt/kafka_${SCALA_VERSION}-${KAFKA_VERSION} /opt/kafka && \
    mkdir -p /tmp/kafka /tmp/logs && \
    apk del build-dependencies && \
    rm -rf /var/cache/apk/*

ENV KAFKA_HOME /opt/kafka
ENV PATH $KAFKA_HOME/bin:$PATH

ADD config $KAFKA_HOME/config
ADD start.sh $KAFKA_HOME/start.sh

WORKDIR /opt/kafka

EXPOSE 9092 7203

CMD ["/opt/kafka/start.sh"]
