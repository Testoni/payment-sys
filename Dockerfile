FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Copy your application JAR file
COPY target/payment-system-0.0.1-SNAPSHOT.jar /app/payment-system.jar

# Download WireMock standalone JAR
RUN wget https://repo1.maven.org/maven2/com/github/tomakehurst/wiremock-standalone/2.14.0/wiremock-standalone-2.14.0.jar -O /app/wiremock-standalone.jar

# Expose the ports for your application and WireMock
EXPOSE 8080 8081

# Start both your application and WireMock
CMD ["sh", "-c", "java -jar /app/wiremock-standalone.jar --port 8081 & java -jar /app/payment-system.jar"]