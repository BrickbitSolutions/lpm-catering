#LPM PROJECT

##CATERING MODULE

This module automates the stock management and order cycles of the catering of an event. Specifically produced on the use case of ZanziLan vzw.

###Build Status

[![CircleCI](https://circleci.com/gh/BrickbitSolutions/lpm-catering/tree/develop.svg?style=svg)](https://circleci.com/gh/BrickbitSolutions/lpm-catering/tree/develop)

###Installation

Compile the code with Gradle 2.14 or with the delivered Gradle wrapper. This will generate a .jar in the folder build/libs

```sh
$ Gradle build
```

In order to start the module you will have to do some configuration according to your own environment using a spring boot application.properties file. Without this file, the module will not start.

```
datasource.primary.url=jdbc:postgresql://localhost:32775/lpmc
datasource.primary.username=lpmc_user
datasource.primary.password=lpmc_pwd

datasource.auth.url=jdbc:postgresql://localhost:32776/lpm
datasource.auth.username=lpm_user
datasource.auth.password=lpm_pwd

lpm.storage.images.products=/tmp/images

spring.jpa.database=postgresql
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.generate-ddl=false
spring.jpa.properties.hibernate.format_sql=false
spring.jpa.show-sql=true

logging.level.org.springframework.web=DEBUG
logging.level.org.springframework.messaging=TRACE

spring.jackson.serialization.WRITE_DATES_AS_TIMESTAMPS=false
spring.jackson.serialization.indent_output=true
spring.application.name=LPM Catering Module

server.port=8082
```

You may configure the module to your liking using the known [spring boot application properties](http://docs.spring.io/spring-boot/docs/current/reference/html/common-application-properties.html) . To start the module use the following command:

```
$ java -jar lpm-core-1.0-SNAPSHOT.jar --spring.config.location=file:/path/to/application.properties
```

Spring Boot will start a container and serve the API default on localhost:8080.