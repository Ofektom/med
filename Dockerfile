# First stage: build the project with Maven
FROM maven:3.8.5-openjdk-21-slim AS builder

# Set working directory
WORKDIR /app

# Copy your pom.xml and source code
COPY pom.xml .
COPY src/ src/

# Build the application
RUN mvn clean package -DskipTests

# Second stage: run the app
FROM openjdk:21-jdk-slim

# Set working directory
WORKDIR /app

# Copy the jar from the build stage
COPY --from=builder /app/target/med-0.0.1-SNAPSHOT.jar app.jar

# Expose your app's port
EXPOSE 6063

# Run your application
ENTRYPOINT ["java", "-jar", "app.jar"]