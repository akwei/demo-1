#!/bin/bash

set -e
SCRIPTPATH=$(dirname $realpath ${BASH_SOURCE[0]})
DOCKER_FILE_PATH=./${SCRIPTPATH}/Dockerfile
DOCKER_CONTEXT_PATH=./${SCRIPTPATH}/..
echo "${SCRIPTPATH}, ${DOCKER_FILE_PATH}, ${DOCKER_CONTEXT_PATH}"

docker build -t akwei/demo-1:v1 -f ${DOCKER_FILE_PATH} ${DOCKER_CONTEXT_PATH}