# Build stage with Maven and Eclipse Temurin JDK 17
FROM maven:3.9-eclipse-temurin-17 AS builder

WORKDIR /app

# Copy pom.xml and src directory
COPY pom.xml .
COPY src/ src/

# Build the project with Java 17
RUN mvn clean package -DskipTests

# Final stage with Eclipse Temurin JRE 17 for runtime (matches build version, smaller image)
FROM eclipse-temurin:17-jre-jammy

WORKDIR /app

# Copy the jar from the build stage
COPY --from=builder /app/target/med-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 6063

ENTRYPOINT ["java", "-jar", "app.jar"]