FROM biggis/base:java8-jre-alpine

# Install Flink
ARG FLINK_VERSION=1.1.1
ARG HADOOP_VERSION=27
ARG SCALA_VERSION=2.10

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
      eu.biggis-project.version=$FLINK_VERSION

RUN set -x && \
    apk --update add --virtual build-dependencies curl && \
    curl -s $(curl -s https://www.apache.org/dyn/closer.cgi\?as_json\=1 | awk '/preferred/ {gsub(/"/,""); print $2}')flink/flink-${FLINK_VERSION}/flink-${FLINK_VERSION}-bin-hadoop${HADOOP_VERSION}-scala_${SCALA_VERSION}.tgz | tar -xzf - -C /opt && \
    ln -s /opt/flink-$FLINK_VERSION /opt/flink && \
    sed -i -e "s/echo \$mypid >> \$pid/echo \$mypid >> \$pid \&\& wait/g" /opt/flink/bin/flink-daemon.sh && \
    apk del build-dependencies && \
    rm -rf /var/cache/apk/*

ENV FLINK_HOME /opt/flink
ENV PATH $PATH:$FLINK_HOME/bin

ADD docker-entrypoint.sh $FLINK_HOME/bin/

WORKDIR /opt/flink

EXPOSE 6123

CMD ["docker-entrypoint.sh", "sh", "-c"]
