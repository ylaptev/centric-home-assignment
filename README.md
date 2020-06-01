# Home Assignment

This is an implementation of CentricSoftware Home assignment.  

## Technologies

* Java 11 (java src compatible with Java 8, but will require pom changes)
* Spring Boot 2.2
* String Web, String Data, JPA
* H2: in memory (embedded) database
* Lombok (requires plugin in IDE: https://projectlombok.org/setup/overview)
* Maven 3

## Build and Test

This is Maven 3 based project using JDK 11. 

Build project with standard maven command:

```bash
mvn clean install
```
This will also run all tests including integration tests.

To skip tests in build process run:

```bash
mvn clean install -DskipTests
```

Assumptions: your local path variables set correctly to use JDK 11 and Maven 3.

## Usage

Start Spring Boot application:
```bash
java -jar ./target/centric-demo-0.0.1-SNAPSHOT.jar
```

Application will start on port 8088. 

Sample get URL:
http://localhost:8088/v1/products?category=apparel&page=0&pageSize=5
