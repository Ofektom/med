# Use an openjdk 21 base image
FROM openjdk:21-jdk-slim

# Set working directory
WORKDIR /app

# Copy your jar file into the container
COPY target/med-0.0.1-SNAPSHOT.jar app.jar

# Expose your app's port
EXPOSE 6063

# Run your application
ENTRYPOINT ["java", "-jar", "app.jar"]