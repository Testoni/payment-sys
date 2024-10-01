FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY target/payment-system-0.0.1-SNAPSHOT.jar /app/payment-system.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/payment-system.jar"]
