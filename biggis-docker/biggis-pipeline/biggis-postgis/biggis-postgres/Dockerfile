FROM biggis/base:java8-jre-alpine

MAINTAINER wipatrick

ENV PG_MAJOR=9.5.3
ENV PG_VERSION=9.5.3-r1

ENV LANG en_US.utf8
ENV PGDATA /opt/postgresql/data

RUN apk add --no-cache \
            postgresql=${PG_VERSION} \
            postgresql-contrib=${PG_VERSION} \
    && mkdir -p /opt/postgresql/data /opt/postgresql/initdb.d


ADD start.sh /opt/postgresql/

VOLUME ["/opt/postgresql/data"]

EXPOSE 5432

ENTRYPOINT ["/opt/postgresql/start.sh"]
CMD ["postgres"]
