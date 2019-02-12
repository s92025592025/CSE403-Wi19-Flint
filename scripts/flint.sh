#!/bin/sh

CURRENT_DIR="${0%/*}"

java -jar "${CURRENT_DIR}/flint-0.1.3.jar" $@
