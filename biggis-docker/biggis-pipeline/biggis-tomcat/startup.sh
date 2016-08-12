#!/bin/bash

if [ -n $TOMCAT_ADMIN ] && [ -n $TOMCAT_ADMIN_PASSWORD ]; then
  sed -i -e "s/%%TOMCAT_ADMIN%%/$TOMCAT_ADMIN/g" $CATALINA_HOME/conf/tomcat-users.xml
  sed -i -e "s/%%TOMCAT_ADMIN_PASSWORD%%/$TOMCAT_ADMIN_PASSWORD/g" $CATALINA_HOME/conf/tomcat-users.xml
else
  sed -i -e "s/%%TOMCAT_ADMIN%%/tomcat/g" $CATALINA_HOME/conf/tomcat-users.xml
  sed -i -e "s/%%TOMCAT_ADMIN_PASSWORD%%/password/g" $CATALINA_HOME/conf/tomcat-users.xml
fi

echo "Start Tomcat Server"
$CATALINA_HOME/bin/catalina.sh run
