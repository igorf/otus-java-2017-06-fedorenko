#!/bin/sh

java -XX:-UseTLAB -Xmx256M -Xms256M -javaagent:agents/jamm.jar -jar ../task/target/homework-task-02.jar -usejamm
