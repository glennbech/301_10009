#!/bin/bash
set -ex;
docker build --tag "${IMAGE}:${TRAVIS_COMMIT}" . && \
docker push "${IMAGE}:${TRAVIS_COMMIT}" && \
set +x