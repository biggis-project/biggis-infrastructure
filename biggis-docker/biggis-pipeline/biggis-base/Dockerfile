FROM java:8-jre-alpine

MAINTAINER wipatrick

RUN echo 'http://dl-4.alpinelinux.org/alpine/v3.4/main' >> /etc/apk/repositories && \
    echo '@testing http://dl-4.alpinelinux.org/alpine/edge/testing' >> /etc/apk/repositories
RUN apk add --no-cache bash curl snappy supervisor gosu@testing

# Configure supervisor
ADD supervisor.conf /etc/supervisor/

COPY entrypoint.sh /usr/local/bin/entrypoint.sh

RUN mkdir /storage

WORKDIR /home

ENTRYPOINT ["/usr/local/bin/entrypoint.sh"]
