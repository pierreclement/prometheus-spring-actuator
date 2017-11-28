#!/bin/sh

if [ "$DEBUG" = true ]; then
  printf "Running the application in debug mode\n"
  JAVA_OPTS="$JAVA_OPTS -Xdebug -Xrunjdwp:server=y,transport=dt_socket,address=$DEBUG_PORT,suspend=n"
fi

if [ "$JMX" = true ]; then
  printf "Running the application in JMX mode\n"
  JAVA_OPTS="$JAVA_OPTS -Dcom.sun.management.jmxremote.port=$JMX_PORT -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false"
fi
printf "Running APP_HOME/JAR_FILE $APP_HOME/$JAR_FILE\n"
# Enables application to take PID 1 and receive SIGTERM sent by Docker stop command.
# See here https://docs.docker.com/engine/reference/builder/#/entrypoint
exec java $JAVA_OPTS \
       -Djava.security.egd=file:/dev/./urandom -jar $APP_HOME/$JAR_FILE
