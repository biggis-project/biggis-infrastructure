#!/usr/bin/env bash

RANCHER_ACCESS_KEY=${RANCHER_ACCESS_KEY:-$1}
RANCHER_SECRET_KEY=${RANCHER_SECRET_KEY:-$2}
RANCHER_PROJECT_ID=${RANCHER_PROJECT_ID:-$3}

VOLUMES=( "kafka" "zookeeper" "exasol" "geoserver" "hdfs" "couchdb" "sesame")

function createVol {
  VOLUME_NAME=$1
  sed -i -e "s/##VOLUME_NAME##/$VOLUME_NAME/g" create-volume.json
  curl -u "${RANCHER_ACCESS_KEY}:${RANCHER_SECRET_KEY}" -X POST -H "Content-Type: application/json" -d @create-volume.json "http://server.biggis.project.de:8080/v1/projects/${RANCHER_PROJECT_ID}/volumes" | jq '.'
  sed -i -e "s/$VOLUME_NAME/##VOLUME_NAME##/g" create-volume.json
}

function usage {
  printf "source env-file\n"
  printf "./create-volume.sh\n"
}

if [ -z "$RANCHER_ACCESS_KEY" ] || [ -z "$RANCHER_SECRET_KEY" ] || [ -z "$RANCHER_PROJECT_ID" ]; then
  usage
  exit 1
fi

printf "Enter a name for a volume to be created.\n"
printf "Create all volumes:\n"
printf "[ all ]\n\n"
printf "Create specific volume:\n"
printf "[ "
for key in ${VOLUMES[@]}
  do
    :
    printf "${key} "
done
printf "]\n\n"
read VOL

if [ $VOL = "all" ]; then
  for key in ${VOLUMES[@]}
    do
      :
      createVol ${key}
  done
else
  createVol $VOL
fi
