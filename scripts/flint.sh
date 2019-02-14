#!/bin/bash
  
CURRENT_DIR="${0%/*}"


if [ 0 -lt $(ls -l "${CURRENT_DIR}" | grep flint-.*.jar | wc -l) ];
then
        NEWEST_FLINT="$(ls "${CURRENT_DIR}" | grep /flint-*.jar | sort -t- -k2 -V -r | head -1)"
        echo "$NEWEST_FLINT"
        java -jar "${CURRENT_DIR}/flint-0.1.3.jar" "$@"
else
        echo "flint-<version>.jar should be in the same directory as flint.sh"
fi
