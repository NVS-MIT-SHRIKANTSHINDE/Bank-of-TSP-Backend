# Use a base image with the JDK and required dependencies
FROM openjdk:17-alpine

EXPOSE 8081

# Copy the JAR file containing your microservice into the container
ADD  target/*.jar transactionservice.jar

# Specify the command to run your microservice when the container starts
ENTRYPOINT  ["java", "-jar", "/transactionservice.jar"]
