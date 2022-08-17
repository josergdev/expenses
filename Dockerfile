FROM maven:3.6.3-openjdk-17-slim AS build

ENV HOME=/usr/app
RUN mkdir -p $HOME
WORKDIR $HOME
ADD pom.xml $HOME
RUN mvn -f $HOME/pom.xml verify --fail-never

COPY src $HOME/src
RUN mvn -f $HOME/pom.xml clean package

FROM eclipse-temurin:17-jre-alpine
COPY --from=build "/usr/app/target/expenses-0.1.jar" "/usr/local/lib/expenses-0.1.jar"
EXPOSE 8080
CMD ["java", "-jar", "/usr/local/lib/expenses-0.1.jar"]