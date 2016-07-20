# inherits from biggis/base:java8-jre-alpine
FROM biggis/kafka:0.9.0.0

RUN apk add --no-cache inotify-tools expect perl-io-tee@testing

RUN mkdir /opt/collector
ADD collector-daemon.sh /opt/collector
RUN mkdir /storage/rasteringest

WORKDIR /home/collector

CMD ["/opt/collector/collector-daemon.sh", "/storage/rasteringest", "rasterqueue"]
