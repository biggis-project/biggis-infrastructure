FROM biggis/base:java8-jre-alpine

# Install Flink
ARG FLINK_VERSION=1.0.3
ARG HADOOP_VERSION=27
ARG SCALA_VERSION=2.10

RUN mkdir /opt \
    && curl -s $(curl -s https://www.apache.org/dyn/closer.cgi\?as_json\=1 | awk '/preferred/ {gsub(/"/,""); print $2}')flink/flink-${FLINK_VERSION}/flink-${FLINK_VERSION}-bin-hadoop${HADOOP_VERSION}-scala_${SCALA_VERSION}.tgz | tar -xzf - -C /opt \
    && mv /opt/flink-$FLINK_VERSION /opt/flink

ENV FLINK_HOME /opt/flink
ENV PATH $PATH:$FLINK_HOME/bin

# Configure container
ADD docker-entrypoint.sh $FLINK_HOME/bin/

WORKDIR /opt/flink

VOLUME ["/storage"]

# ENTRYPOINT ["docker-entrypoint.sh"]
CMD ["docker-entrypoint.sh", "sh", "-c"]
