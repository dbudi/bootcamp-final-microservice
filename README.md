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

## Test Schenario

1. Inventory INSTOCK -> Payment APPROVED -> Order COMPLETED

    `curl -X 'POST' \
    'http://localhost:8081/api/order/create' \
    -H 'accept: */*' \
    -H 'Content-Type: application/json' \
    -d '{
    "orderId": 1,
    "productId": 1,
    "quantity": 10,
    "amount": 5000,
    "customerId": 1,
    "status": "CREATED"
    }'`
    
2. Inventory OUTOFSTOCK -> Order FAILED

    `curl -X 'POST' \
    'http://localhost:8081/api/order/create' \
    -H 'accept: */*' \
    -H 'Content-Type: application/json' \
    -d '{
    "orderId": 1,
    "productId": 15,
    "quantity": 10,
    "amount": 5000,
    "customerId": 1,
    "status": "CREATED"
    }'`
    
3. Inventory INSTOCK -> Payment REJECTED -> Inventory ROLLBACK -> Order FAILED

    `curl -X 'POST' \
    'http://localhost:8081/api/order/create' \
    -H 'accept: */*' \
    -H 'Content-Type: application/json' \
    -d '{
    "orderId": 1,
    "productId": 1,
    "quantity": 10,
    "amount": 5000,
    "customerId": 15,
    "status": "CREATED"
    }'`