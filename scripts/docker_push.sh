#!/bin/bash
set -ex;
docker build --tag "${IMAGE}:latest" . && \
docker push "${IMAGE}:latest" && \
set +x