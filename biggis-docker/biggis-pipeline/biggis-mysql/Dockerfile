FROM biggis/base:alpine-3.4

MAINTAINER wipatrick

ARG MYSQL_MAJOR=10.1.14
ARG MYSQL_VERSION=10.1.14-r3

RUN apk add --no-cache mysql=${MYSQL_VERSION}\
        && mkdir -p /var/lib/mysql \
        && mkdir -p /etc/mysql/conf.d \
        && { \
              echo '[mysqld]'; \
              echo 'user = root'; \
              echo 'datadir = /var/lib/mysql/'; \
              echo 'port = 3306'; \
              echo 'log-bin = /var/lib/mysql/mysql-bin'; \
              echo 'socket = /var/lib/mysql/mysql.sock'; \
              echo '!includedir /etc/mysql/conf.d/'; \
            } > /etc/mysql/my.cnf

VOLUME ["/var/lib/mysql", "/etc/mysql/conf.d/"]

EXPOSE 3306

ENTRYPOINT ["mysqld"]
CMD ["--skip-grant-tables"]
