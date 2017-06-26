#!/bin/sh

java -Xmx64M -Xms64M -Djava.util.logging.config.file=logging.properties -jar ../target/homework-task-04.jar -o=100000 -t=4000
