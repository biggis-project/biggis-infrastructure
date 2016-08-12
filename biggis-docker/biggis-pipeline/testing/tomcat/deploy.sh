#!/bin/bash

# http://<TOMCAT_USER>:<TOMCAT_PASSWORD>@<DOCKER_HOST_IP>:5000/manager/text/deploy?path=/api&update=true
#
# <TOMCAT_USER>: biggis (see conf/tomcat-users.xml)
# <TOMCAT_PASSWORD>: biggis (see conf/tomcat-users.xml)
curl --upload-file $1 "http://biggis:biggis@192.168.99.100:8080/manager/text/deploy?path=/api&update=true"
