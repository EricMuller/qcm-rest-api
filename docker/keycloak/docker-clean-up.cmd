@echo off
rem $> docker-compose --file docker-compose-scale.yml up -d --build --scale message-server=3 product-server=2

docker volume rm keycloak_postgres_data
docker compose rm
docker compose build
docker compose up

