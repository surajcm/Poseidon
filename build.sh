#!/bin/bash
export JAVA_HOME="/Library/Java/JavaVirtualMachines/jdk-19.jdk/Contents/Home"
export JAVA_OPTS="-Xms512m -Xmx512m"
# java -Dserver.port=8181 $JAVA_OPTS -jar build/libs/*.jar
exec ./gradlew "$@"
