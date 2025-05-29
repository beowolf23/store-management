FROM eclipse-temurin:21-jre-jammy

ARG JAR_FILE=/target/*.jar
COPY $JAR_FILE /app/runner.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/runner.jar"]