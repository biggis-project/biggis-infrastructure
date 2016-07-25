# inherits from biggis/base:java8-jre-alpine
FROM biggis/kafka:0.9.0.0

ARG KAFKA_VERSION=0.9.0.0

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
      eu.biggis-project.version=$KAFKA_VERSION

RUN apk add --no-cache inotify-tools expect perl-io-tee@testing

RUN mkdir /opt/collector
ADD collector-daemon.sh /opt/collector
RUN mkdir /storage/rasteringest

WORKDIR /home/collector

CMD ["/opt/collector/collector-daemon.sh", "/storage/rasteringest", "rasterqueue"]
