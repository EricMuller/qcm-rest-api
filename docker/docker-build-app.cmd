

cd ..

call mvn clean package -Dmaven.test.skip=true

call docker build -t ericmuller/qcm-rest-api ./qcm-app

rem call docker push  ericmuller/qcm-rest-api

rem call docker pull ericmuller/qcm-rest-api

rem docker build -t qcm-rest-api:0.0.1 ./qcm-app

rem docker run --name qcm-postgres -p 5433:5432 -e POSTGRES_PASSWORD=postgres -d postgres:10.12

rem docker run --name qcm-rest-api -p 8080:8081 -d qcm-rest-api

rem docker exec -it qcm-rest-api bash

rem docker history --format "{{.ID}} {{.CreatedBy}} {{.Size}}" qcm-rest-api
