# Start with a base image containing Java runtime
FROM openjdk:8-jdk-alpine

# Make port 8080 available to the world outside this container
EXPOSE 8080

# Set the workdir to 
WORKDIR /usr/local/bin

# The application's jar file
ARG JAR_FILE

# Copy the application's jar to the container
COPY ${JAR_FILE} webapp.jar

# Run the jar file
CMD ["java","-jar","webapp.jar"]
