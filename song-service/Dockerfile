# Use an official Maven image to build the application
FROM maven:3.9.4-eclipse-temurin-21 AS build
WORKDIR /app

# Copy the project files
COPY pom.xml pom.xml
WORKDIR ./song-service
COPY /song-service/pom.xml pom.xml
RUN mvn dependency:go-offline
COPY /song-service/src src
RUN mvn clean package -DskipTests

# Use an official OpenJDK image to run the application
FROM eclipse-temurin:21-jdk
WORKDIR /app

# Copy the built JAR file from the build stage
COPY --from=build /app/song-service/target/*.jar app.jar

# Expose the application port
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "app.jar"]