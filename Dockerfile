FROM eclipse-temurin:17-alpine
COPY target/expenses-0.1.jar expenses.jar
EXPOSE 8080
CMD ["java", "-jar", "expenses.jar"]