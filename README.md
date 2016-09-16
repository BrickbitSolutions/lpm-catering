#LPM PROJECT

##CATERING MODULE

This module automates the stock management and order cycles of the catering of an event. Specifically produced on the use case of ZanziLan vzw.

###Build Status

[![CircleCI](https://circleci.com/gh/BrickbitSolutions/lpm-catering/tree/develop.svg?style=shield)](https://circleci.com/gh/BrickbitSolutions/lpm-catering/tree/develop) [![Codacy Badge](https://api.codacy.com/project/badge/Grade/9fac2e3088574d16a29f7a18ba381cf1)](https://www.codacy.com/app/soulscammer/lpm-catering?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=BrickbitSolutions/lpm-catering&amp;utm_campaign=Badge_Grade) [![codecov](https://codecov.io/gh/BrickbitSolutions/lpm-catering/branch/develop/graph/badge.svg)](https://codecov.io/gh/BrickbitSolutions/lpm-catering)


###Installation

Compile the code with Gradle 2.14 or with the delivered Gradle wrapper. This will generate a .jar in the folder build/libs

```sh
$ Gradle build
```

To start the module simply type the following in a terminal:

```
$ java -jar lpm-core-1.0-SNAPSHOT.jar --spring.config.location=file:/path/to/application.properties
```


The Catering module depends on the [Core Module](https://github.com/BrickbitSolutions/lpm-core) for handling authentication and authorization. When no configuration is passed on it will expect a core module to be running on localhost:8080. This will also start the service with Spring Boots default auto config on port 8081, using a in-memory database.
We strongly recommend connecting it to a postgreSQL where it was designed for. Feel free to use any other SQL Database, but bare in mind the Database migrations are written for PostgreSQL specifically. The module is completely configurable to your desires using Spring Boot [Common application properties](http://docs.spring.io/spring-boot/docs/current/reference/html/common-application-properties.html). 

####LPM Properties

To configure a few features of LPM, we provide some properties of our own. These you can use to configure them to your desire.

```
lpm.storage.images.products=/tmp/images     #The location where LPM will save the images for the Products. Please make sure LPM has te right access rights to this folder. This is a mandatory setting and will default to /tmp/images.
lpm.core.url=http://localhost:8080          #The url where the Core module is running
```