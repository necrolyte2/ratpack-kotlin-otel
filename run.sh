#!/bin/bash

# Why does java have to be so hard?

java -javaagent:/otel/otel.jar -classpath /src/app:$(find /src/lib | xargs | tr ' ' ':') sample.MainKt
