# First stage: Build the JAR file using Maven
FROM maven:3.9.9-eclipse-temurin-17 AS builder

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src
RUN mvn clean install -DskipTests

# Second stage: Create the final image with only the built JAR file
FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY --from=builder /app/target/divertoland-${VERSION}.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]