#!/usr/bin/env bash

nohup mvn clean spring-boot:run > start.log 2 >&1 &
