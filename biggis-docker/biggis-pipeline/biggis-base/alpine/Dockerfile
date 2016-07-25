FROM alpine:3.4

MAINTAINER wipatrick

ARG ALPINE_VERSION=3.4

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
      eu.biggis-project.version=$ALPINE_VERSION

ADD repositories /etc/apk/
RUN apk add --no-cache gosu@testing && \
    mkdir /storage /opt

COPY entrypoint.sh /usr/local/bin/entrypoint.sh

WORKDIR /home

ENTRYPOINT ["/usr/local/bin/entrypoint.sh"]
