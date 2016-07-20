FROM alpine:3.4

MAINTAINER wipatrick

ADD repositories /etc/apk/
RUN apk add --no-cache gosu@testing && \
    mkdir /storage /opt

COPY entrypoint.sh /usr/local/bin/entrypoint.sh

WORKDIR /home

ENTRYPOINT ["/usr/local/bin/entrypoint.sh"]
