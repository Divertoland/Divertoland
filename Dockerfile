# First stage: Build the JAR file using Maven
FROM maven:3.8.6-openjdk-17-slim AS builder

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src
RUN mvn clean install -DskipTests

# Second stage: Create the final image with only the built JAR file
FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=builder /app/target/divertoland.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]