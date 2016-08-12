FROM biggis/base:java8-jre-alpine

MAINTAINER wipatrick

# Tomcat Version
ARG TOMCAT_MAJOR=8
ARG TOMCAT_VERSION=8.0.36

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
      eu.biggis-project.version=$TOMCAT_VERSION

# Download and install
RUN set -x && \
    apk --update add --virtual build-dependencies curl && \
    curl -LO https://archive.apache.org/dist/tomcat/tomcat-${TOMCAT_MAJOR}/v${TOMCAT_VERSION}/bin/apache-tomcat-${TOMCAT_VERSION}.tar.gz && \
    curl -LO https://archive.apache.org/dist/tomcat/tomcat-${TOMCAT_MAJOR}/v${TOMCAT_VERSION}/bin/apache-tomcat-${TOMCAT_VERSION}.tar.gz.md5 && \
    md5sum -c apache-tomcat-${TOMCAT_VERSION}.tar.gz.md5 && \
    gunzip -c apache-tomcat-${TOMCAT_VERSION}.tar.gz | tar -xf - -C /opt && \
    rm -f apache-tomcat-${TOMCAT_VERSION}.tar.gz apache-tomcat-${TOMCAT_VERSION}.tar.gz.md5 && \
    ln -s /opt/apache-tomcat-${TOMCAT_VERSION} /opt/tomcat && \
    rm -rf /opt/tomcat/webapps/examples /opt/tomcat/webapps/docs && \
    apk del build-dependencies && \
    rm -rf /var/cache/apk/*

# Configuration
COPY conf/tomcat-users.xml /opt/tomcat/conf/tomcat-users.xml
COPY startup.sh /opt/tomcat/startup.sh

# Set max file size
# RUN sed -i 's/52428800/5242880000/g' /opt/tomcat/webapps/manager/WEB-INF/web.xml

# Set environment
ENV CATALINA_HOME /opt/tomcat
ENV PATH $CATALINA_HOME/bin:$PATH

# Expose web port
EXPOSE 8080

WORKDIR $CATALINA_HOME
# Launch Tomcat on startup
CMD ["/opt/tomcat/startup.sh"]
