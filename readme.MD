# Freelancer Marketplace API

**The app is deployed on ec2 and available to test directly using Postman**
## Build instructions for local testing

- You need docker-compose with docker daemon running
- git

1. Clone the repository:
   ```bash
   git clone https://github.com/niyazi-eren/free.git
2. Start project
    ```bash
    cd free
    docker-compose -f compose.backend.yaml up

3. Run tests (you need Java)
    ```bash
    ./mvnw test
   
## Test using Postman
- Two collections were added to this repo
  - one to test locally 
  - and one to test in integration (container deployed on ec2 machine)

## Documentation
Available here on swagger-ui http://13.38.93.138:8080/swagger-ui/index.html#/
or locally http://localhost:8080/swagger-ui/index.html#/


## Key Features
- All endpoints are implemented, tested and documented (Freelancer creation is fully documented)
- The asynchronous workload is handled with a queue using RabbitMQ
- The app container is built using Paketo buildpacks.
- The API is deployed using Docker-compose on ec2
- Used TestContainers to start a Postgresql container for tests
- Flyway for schema versioning


## Technologies Used
- Java 17
- Spring Boot 3
- RabbitMQ
- OpenAPI / Swagger
- Hibernate, JPA
- PostgreSQL
- Flyway
- TestContainers, RestAssured, JUnit
- Docker-compose