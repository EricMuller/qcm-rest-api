# Qcm Api

[![Build Status](https://travis-ci.com/EricMuller/qcm-rest-api.svg?branch=master)](https://travis-ci.com/EricMuller/qcm-rest-api)[![License](http://img.shields.io/:license-mit-blue.svg)](https://opensource.org/licenses/mit-license.php)[![Quality Gate](https://sonarcloud.io/api/project_badges/measure?project=com.emu.apps.qcm%3Aqcm-rest-api&metric=alert_status)](https://sonarcloud.io/dashboard/index/com.emu.apps.qcm:qcm-rest-api) [![Known Vulnerabilities](https://snyk.io/test/github/EricMuller/qcm-rest-api/badge.svg)](https://snyk.io/test/github/EricMuller/qcm-rest-api)[![CII Best Practices](https://bestpractices.coreinfrastructure.org/projects/2888/badge)](https://bestpractices.coreinfrastructure.org/projects/2888) 


qcm-rest-api is a sample QCM Rest API.


You can also:
  
  - Create some Questions.
  - Create some Questionnaires. 
  - Create some Tags.
  - Upload Questions from json files.

### Quality Gate


[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=com.emu.apps.qcm%3Aqcm-rest-api&metric=bugs)](https://sonarcloud.io/component_measures?id=com.emu.apps.qcm%3Aqcm-rest-api&metric=bugs)[![Code smells](https://sonarcloud.io/api/project_badges/measure?project=com.emu.apps.qcm%3Aqcm-rest-api&metric=code_smells)](https://sonarcloud.io/component_measures?id=com.emu.apps.qcm%3Aqcm-rest-api&metric=code_smells)[![Duplicated lines](https://sonarcloud.io/api/project_badges/measure?project=com.emu.apps.qcm%3Aqcm-rest-api&metric=duplicated_lines_density)](https://sonarcloud.io/component_measures?id=com.emu.apps.qcm%3Aqcm-rest-api&metric=duplicated_lines_density)

[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=com.emu.apps.qcm%3Aqcm-rest-api&metric=coverage)](https://sonarcloud.io/component_measures?id=com.emu.apps.qcm%3Aqcm-rest-api&metric=coverage)

[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=com.emu.apps.qcm%3Aqcm-rest-api&metric=vulnerabilities)](https://sonarcloud.io/component_measures?id=com.emu.apps.qcm%3Aqcm-rest-api&metric=vulnerabilities)


### Tech

qcm-rest-api uses a number of open source projects to work properly:

* [SpringBoot] - Create stand-alone backend Spring applications
* [Spring WebFlux] - Reactive-stack web framework 
* [Spring Data JPA]  - Provides repository support for the Java Persistence API (JPA)
* [Apache Kafka] - Distributed streaming platform
* [MapStruct] - code generator that greatly simplifies the implementation of mappings between Java bean types  
* [keycloak] - an open source identity and access management solution
* [Lombok] - Never write another getter or equals method again
* [JaCoCo] - JaCoCo is a free code coverage library for Java,.

### Continuous integration

* [Maven] - the build system
* [Travis] - Test and Deploy with Confidence [travis-ci](https://travis-ci.com/)
* [SonarQube] - SonarQube on [sonarcloud.io](https://sonarcloud.io/about) is an open-source platform for continuous inspection of code quality.
* [Heroku] - Fully managed container-based cloud platform, with integrated data services. [heroku.com](https://www.heroku.com)
* [Snyk] - Finding & fixing vulnerabilities in your dependencies. [snyk.io](https://snyk.io)

### Documentations

* [SwaggerHub](https://app.swaggerhub.com/apis-docs/ericmuller/qcm-rest-api/2-oas3) - Collaborative platform where you can define your APIs using the OpenAPI Specification

### Installation

qcm-rest-api requires [MAVEN](https://maven.apache.org/) v3.3+ to run.

Install the dependencies and devDependencies and start the server.

```sh
$ cd qcm-rest-api
$ maven clean install
$ maven spring-boot:run
```

Verify the deployment by navigating to your server address in your preferred browser.

```sh
http://127.0.0.1:8080/swagger-ui.html#/
```

### Deploy

<a href="https://qcm-rest-api.herokuapp.com/swagger-ui.html#" target="_blank">![Deploy to Heroku](https://www.herokucdn.com/deploy/button.png)</a>


```sh
$ curl https://qcm-rest-api.herokuapp.com/ ...

```

### Todos

 - Write more features
 - Add reactive mode with webflux
 - Batch import with kafka
 
### License



Code is under the [MIT Licence ](https://opensource.org/licenses/mit-license.php).


### Some Technical Notes 


[Date Format in REST API](http://apiux.com/2013/03/20/5-laws-api-dates-and-times/) 

    The 5 laws of API dates and times in rest api:

    - Law #1: Use ISO-8601 for your dates 
    - Law #2: Accept any timezone
    - Law #3: Store it in UTC
    - Law #4: Return it in UTC
    - Law #5: Don’t use time if you don’t need it



[Google JSON Style Guide](https://google.github.io/styleguide/jsoncstyleguide.xml?showone=Property_Name_Format#Property_Name_Format) (recommendations for building JSON APIs at Google),

     It recommends that:

     - Property names must be camelCased, ASCII strings.

     - The first character must be a letter, an underscore (_) or a dollar sign ($).
