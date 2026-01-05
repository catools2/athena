#!/usr/bin/env bash

helm uninstall athena

docker system prune -a --volumes -f