# Use a base image with eclipse-temurin
FROM eclipse-temurin:17-jdk-focal

# Set the working directory inside the container
WORKDIR /app

ARG JAR_FILE=./target/*.jar

# Copy the packaged JAR file into the container
COPY ${JAR_FILE} app.jar

# Expose the port that the application will be running on
EXPOSE 8080

# Set the entry point command to run the application when the container starts
ENTRYPOINT ["java","-jar","app.jar"]