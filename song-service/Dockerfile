# Use an official Maven image to build the application
FROM maven:3.9.4-eclipse-temurin-17 AS build
WORKDIR /app

# Copy the project files
COPY pom.xml .
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Use an official OpenJDK image to run the application
FROM eclipse-temurin:17-jdk
WORKDIR /app

# Copy the built JAR file from the build stage
COPY --from=build /app/target/song-service-*.jar app.jar

# Expose the application port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]