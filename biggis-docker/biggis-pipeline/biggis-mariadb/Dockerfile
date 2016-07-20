FROM biggis/base:alpine-3.4

MAINTAINER wipatrick

ARG MYSQL_MAJOR=10.1.14
ARG MYSQL_VERSION=10.1.14-r3

RUN apk add --no-cache mysql=${MYSQL_VERSION} mysql-client=${MYSQL_VERSION}

COPY my.cnf /etc/mysql/my.cnf
COPY start.sh /start.sh

EXPOSE 3306
CMD ["/start.sh"]
