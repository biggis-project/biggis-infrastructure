#!/bin/bash

PACKAGE=$1
USER=$2

JAVA_DIR='export JAVA_HOME="/usr/lib/jvm/java-'$PACKAGE'-oracle"'
JAVA_HOME_SET_CHECK=`grep -F "export JAVA_HOME" /etc/environment`
OLD_PATH_WITH=`cat /etc/environment | grep PATH=`
OLD_PATH=`cat /etc/environment | grep PATH= | tr -d '"' | awk -F'PATH=' '{print $2}'`
NEW_PATH='PATH="'$OLD_PATH':$JAVA_HOME/bin"'

if [ -z "$JAVA_HOME_SET_CHECK" ]; then
  # no JAVA_HOME set, insert before PATH
  sed -i "/PATH=/i$JAVA_DIR" /etc/environment
  # add JAVA_HOME to PATH
  # escape sed due to "/" getting interprated as sed delimiter
  sed -i "s/${OLD_PATH_WITH//\//\\/}/${NEW_PATH//\//\\/}/g" /etc/environment
else
  sed -i "/^export JAVA_HOME/c$JAVA_DIR" /etc/environment
  # JAVA_HOME is probably included in PATH
fi
