@echo off
rem $> docker-compose --file docker-compose-scale.yml up -d --build --scale message-server=3 product-server=2

docker compose up --scale qcm-server=1 -d
