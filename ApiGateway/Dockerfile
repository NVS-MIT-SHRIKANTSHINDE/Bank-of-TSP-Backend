# Use a base image with the JDK and required dependencies
FROM openjdk:17-alpine

EXPOSE 8082

# Copy the JAR file containing your microservice into the container
ADD  target/*.jar api-gateway.jar

# Specify the command to run your microservice when the container starts
ENTRYPOINT  ["java", "-jar", "/api-gateway.jar"]
