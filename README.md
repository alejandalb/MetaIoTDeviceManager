# MetaIoTDeviceManager

build command

docker build -t alalca3/metaiot -f docker/Dockerfile .

docker network create metaIoT-network

docker run -it --name device01 -p 8080:8080 --net metaIoT-network --env-file ./docker/env.list -d alalca3/metaiot


## RUN THINGSBOARD

mkdir -p ~/.mytb-data && sudo chown -R 799:799 ~/.mytb-data
mkdir -p ~/.mytb-logs && sudo chown -R 799:799 ~/.mytb-logs

docker run -p 9090:9090 -p 1883:1883 -p 7070:7070 -p 5683-5688:5683-5688/udp --net metaIoT-network -v ~/.mytb-data:/data -v ~/.mytb-logs:/var/log/thingsboard --name mytb -d thingsboard/tb-postgres