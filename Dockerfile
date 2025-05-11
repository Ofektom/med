# Build stage with a known, existing Maven OpenJDK image
FROM maven:3.8.5-openjdk-17-slim AS builder

WORKDIR /app

# Copy pom.xml and src directory
COPY pom.xml .
COPY src/ src/

# Build the project
RUN mvn clean package -DskipTests

# Final stage
FROM openjdk:21-jdk-slim

WORKDIR /app

# Copy the jar from the build stage
COPY --from=builder /app/target/med-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 6063

ENTRYPOINT ["java", "-jar", "app.jar"]