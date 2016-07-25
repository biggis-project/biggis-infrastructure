FROM java:8-jre-alpine

MAINTAINER wipatrick

ARG JRE_VERSION=8-jre-alpine

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
      eu.biggis-project.version=$JRE_VERSION

ADD repositories /etc/apk
RUN apk add --no-cache bash gosu@testing

COPY entrypoint.sh /usr/local/bin/entrypoint.sh

RUN mkdir /storage /opt

WORKDIR /home

ENTRYPOINT ["/usr/local/bin/entrypoint.sh"]
