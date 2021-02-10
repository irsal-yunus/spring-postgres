#!/bin/sh

java_bin=$JAVA_HOME
java_min=256M
java_max=512M
java_perm=256m
APP_NAME=builds
WORKINGDIR=/app/$APP_NAME
PIDDIR=$WORKINGDIR
f_pid=$PIDDIR/$APP_NAME.pid

JAR_NAME=$APP_NAME.jar
CONFIG_LOCATION=application.properties
LOGGING_CONFIG=logback.xml

export startDir=`pwd`

cd $WORKINGDIR

$java_bin -jar -server -Duser.timezone=GMT -Ddefault.config.dir=$startDir -Dfile.encoding=ISO8859_1 -Xms${java_min} -Xmx${java_max} -XX:MaxPermSize=${java_perm} $JAR_NAME --spring.config.location=$CONFIG_LOCATION --logging.config=$LOGGING_CONFIG > /dev/null &

PID=$!

if ! [ -z $PID ];then
  echo $PID > $f_pid
fi