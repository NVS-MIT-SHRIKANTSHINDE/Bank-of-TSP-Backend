# Use a base image with the JDK and required dependencies
FROM openjdk:17-alpine

EXPOSE 8082

# Copy the JAR file containing your microservice into the container
ADD  target/*.jar customer-service.jar

# Specify the command to run your microservice when the container starts
ENTRYPOINT  ["java", "-jar", "/customer-service.jar"]
