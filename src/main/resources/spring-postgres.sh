#!/bin/sh

java_bin=$JAVA_HOME/bin/java
java_min=256M
java_max=512M
APP_NAME=spring-postgres
WORKINGDIR=/app/$APP_NAME
PIDDIR=$WORKINGDIR
f_pid=$PIDDIR/$APP_NAME.pid

JAR_NAME=$APP_NAME.jar
CONFIG_LOCATION=application.properties


export startDir=`pwd`

cd $WORKINGDIR

$java_bin -jar -server -Duser.timezone=GMT -Ddefault.config.dir=$startDir -Dfile.encoding=ISO8859_1 -Xms${java_min} -Xmx${java_max} -XX:MaxPermSize=${java_perm} $JAR_NAME --spring.config.location=$CONFIG_LOCATION  > /dev/null &

PID=$!

if ! [ -z $PID ];then
  echo $PID > $f_pid
fi