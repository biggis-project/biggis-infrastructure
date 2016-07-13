# inherits from biggis/base:java8-jre-alpine
FROM biggis/kafka:0.9.0.0

# requirements
# inotify-tools: watches dirs
# expect: contains unbuffer
# perl-io-tee: contains tee
RUN apk add --no-cache inotify-tools expect perl-io-tee@testing

RUN mkdir /opt/collector
ADD collector-daemon.sh /opt/collector
RUN mkdir /storage/rasteringest

WORKDIR /home/collector

VOLUME ["/storage/rasteringest"]

CMD ["/opt/collector/collector-daemon.sh", "/storage/rasteringest", "rasterqueue"]
