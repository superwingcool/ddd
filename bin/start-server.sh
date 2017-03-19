#!/usr/bin/env bash

PROJECT_DIR="$(cd "$(dirname "$0")"/..; pwd)"
cd $PROJECT_DIR
mvn clean spring-boot:run > $PROJECT_DIR/start.log 2>&1 &
