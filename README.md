# bootcamp-final-microservice
simple microservices using kafka for orchestration

## Technology stack
* Java 17
* Maven
* Spring-boot 3.3.1
* Docker
* Apache Kafka

## Setup

1. Clean and build from parent project:

    `mvn clean install`

2. Run Order microservice on port 8081:

    `cd order-ms`

    `mvn spring-boot:run -Dspring-boot.run.jvmArguments='-Dserver.port=8081'`

3. Run Inventory microservice on port 8082:

    `cd inventory-ms`

    `mvn spring-boot:run -Dspring-boot.run.jvmArguments='-Dserver.port=8082'`

4. Run Payment microservice on port 8083:

    `cd payment-ms`

    `mvn spring-boot:run -Dspring-boot.run.jvmArguments='-Dserver.port=8083'`

5. Run Order Orchestrator on port 8080:

    `cd inventory-ms`

    `mvn spring-boot:run'`

6. Post order request to endpoint `/api/order/create`