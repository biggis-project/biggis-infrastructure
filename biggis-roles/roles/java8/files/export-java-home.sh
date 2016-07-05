#!/bin/bash

PACKAGE=$1
USER=$2
HOME=/home/$USER

JAVA_DIR="export JAVA_HOME=/usr/java/jdk$PACKAGE"
JAVA_HOME_SET_CHECK=`grep -F "export JAVA_HOME" $HOME/.bash_profile`
OLD_PATH=`cat $HOME/.bash_profile | grep PATH=`
NEW_PATH=$OLD_PATH:'$JAVA_HOME/bin'

if [ -z "$JAVA_HOME_SET_CHECK" ]; then
  # no JAVA_HOME set, insert before PATH
  sed -i "/PATH=/i$JAVA_DIR" $HOME/.bash_profile
  # add JAVA_HOME to PATH
  # escape sed due to "/" getting interprated as sed delimiter
  sed -i "s/${OLD_PATH//\//\\/}/${NEW_PATH//\//\\/}/g" $HOME/.bash_profile
else
  sed -i "/^export JAVA_HOME/c$JAVA_DIR" $HOME/.bash_profile
  # JAVA_HOME is probably included in PATH
fi

. $HOME/.bash_profile
