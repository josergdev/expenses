## Run application

### Auto: Using Docker
1. [Install Docker](https://www.docker.com/get-started)
2. Run docker-compose `docker-compose up`
3. Application will run on port `http://localhost:8080`
4. If any code changes are made:
   1. Down containers `docker-compose down`
   2. Restart again with building option `docker-compose up --build`

### Manual: Using Maven (3.6.3) and JDK (17)
1. Compile project `mvn package`
2. Run jar `java -jar target/expenses-0.1.jar`
3. Application will run on port `http://localhost:8080`

* Note: This option run by default an in memory H2 database as datasource