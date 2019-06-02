# qcm-designer-api
 

[![Build Status](https://travis-ci.com/EricMuller/qcm-designer-api.svg?branch=master)](https://travis-ci.com/EricMuller/qcm-designer-api) [![Known Vulnerabilities](https://snyk.io/test/github/EricMuller/qcm-designer-api/badge.svg)](https://snyk.io/test/github/EricMuller/qcm-designer-api)



qcm-designer-api is a sample QCM Rest API.


You can also:
  
  - Create some Questions.
  - Create some Questionnaires. 
  - Create some Tags.
  - Upload Questions from json files.

### Tech

qcm-designer-api uses a number of open source projects to work properly:


* [SpringBoot] - Create stand-alone backend Spring applications
* [Spring Data JPA]  - provides repository support for the Java Persistence API (JPA)
* [MapStruct] - code generator that greatly simplifies the implementation of mappings between Java bean types  
* [keycloak] - an open source identity and access management solution
* [Lombok] - Never write another getter or equals method again
* [Maven] - the build system
* [Springfox] - Automated JSON API documentation for API's built with Spring
* [Travis] - Test and Deploy with Confidence [![N|Solid](https://cdn.travis-ci.org/images/favicon-076a22660830dc325cc8ed70e7146a59.png)](https://travis-ci.org/)
* [Snyk] - Finding & fixing vulnerabilities in your dependencies
* [Heroku] - Fully managed container-based cloud platform, with integrated data services. 


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

### Deploy

[![Deploy to Heroku](https://www.herokucdn.com/deploy/button.png)](https://qcm-designer-api.herokuapp.com/swagger-ui.html#)


```sh
$ curl https://qcm-designer-api.herokuapp.com/ ...

```

### Todos

 - Write MORE features
 - Add Night Mode

License
----
[![License](http://img.shields.io/:license-apache-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)

Code is under the [Apache Licence v2](https://www.apache.org/licenses/LICENSE-2.0.txt).
