FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app


COPY target/client-suite-0.0.1-SNAPSHOT.jar client-suite-0.0.1-SNAPSHOT.jar


EXPOSE 8080


ENTRYPOINT ["java", "-jar", "client-suite-0.0.1-SNAPSHOT.jar"]
