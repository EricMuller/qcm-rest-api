# qcm-designer-api

[![N|Solid](https://cdn.travis-ci.org/images/favicon-076a22660830dc325cc8ed70e7146a59.png)](https://travis-ci.org/) 


[![Build Status](https://travis-ci.com/EricMuller/qcm-designer-api.svg?branch=master)](https://travis-ci.com/EricMuller/qcm-designer-api)

 

qcm-designer-api is a sample QCM Rest API.


You can also:
  
  - Create some Question.
  - Create some Questionaire. 
  - Create some Tag .
  - Upload Question from json files.

### Tech

qcm-designer-api uses a number of open source projects to work properly:


* [SpringBoot] - Create stand-alone backend Spring applications
* [Spring Data JPA]  - provides repository support for the Java Persistence API (JPA)
* [MapStruct] - code generator that greatly simplifies the implementation of mappings between Java bean types  
* [keycloak] - an open source identity and access management solution
* [Lombok] - Never write another getter or equals method again
* [Maven] - the build system
* [Springfox] - Automated JSON API documentation for API's built with Spring
* [Travis] - Test and Deploy with Confidence



### Installation

qcm-designer-api requires [MAVEN](https://maven.apache.org/) v3.3+ to run.

Install the dependencies and devDependencies and start the server.

```sh
$ cd qcm-designer-api
$ maven clean install
$ maven spring-boot:run
```

Verify the deployment by navigating to your server address in your preferred browser.

```sh
http://127.0.0.1:8080/swagger-ui.html#/
```


### Todos

 - Write MORE features
 - Add Night Mode

License
----

MIT
