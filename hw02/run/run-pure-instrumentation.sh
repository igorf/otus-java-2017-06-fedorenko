#!/bin/sh

java -XX:-UseTLAB -Xmx256M -Xms256M -javaagent:agents/local-agent.jar -jar ../task/target/homework-task-02.jar -uselocalagent
