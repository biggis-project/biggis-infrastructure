FROM java:8-jre-alpine

MAINTAINER wipatrick

ADD repositories /etc/apk
RUN apk add --no-cache bash curl snappy supervisor gosu@testing

# Configure supervisor
ADD supervisor.conf /etc/supervisor/

COPY entrypoint.sh /usr/local/bin/entrypoint.sh

RUN mkdir /storage /opt

WORKDIR /home

ENTRYPOINT ["/usr/local/bin/entrypoint.sh"]
