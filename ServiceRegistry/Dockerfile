# Use a base image with the JDK and required dependencies
FROM openjdk:17-alpine

EXPOSE 8761

# Copy the JAR file containing your microservice into the container
ADD  target/*.jar server-registry.jar

# Specify the command to run your microservice when the container starts
ENTRYPOINT  ["java", "-jar", "/server-registry.jar"]
