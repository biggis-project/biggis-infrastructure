FROM biggis/base:alpine-3.4

MAINTAINER wipatrick

ARG MYSQL_MAJOR=10.1.19
ARG MYSQL_VERSION=10.1.19-r0

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
      eu.biggis-project.version=$MYSQL_MAJOR

RUN apk add --no-cache mysql=${MYSQL_VERSION} mysql-client=${MYSQL_VERSION}

COPY my.cnf /etc/mysql/my.cnf
COPY start.sh /start.sh

EXPOSE 3306
CMD ["/start.sh"]
