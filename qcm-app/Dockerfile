FROM adoptopenjdk:11-jre-hotspot
MAINTAINER eric.pierre.muller@gmail.com
ARG JARFILE=./target/*.jar
COPY ${JARFILE} qcm-app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/qcm-app.jar"]
