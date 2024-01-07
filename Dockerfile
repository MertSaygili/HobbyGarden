# Stage 1: Build the Spring Boot application
# FROM maven:3.9-amazoncorretto-17 AS builder
# 
# WORKDIR /app
# 
# # Copy the necessary build files
# COPY . .
# 
# #COPY pom.xml .
# 
# # Build the Spring Boot project
# RUN mvn clean package

# Stage 2: Create the final image with only the built JAR
FROM eclipse-temurin:17-jre

WORKDIR /app

# Copy the built JAR from the builder stage
# COPY --from=builder /app/target/hobby_garden_server-0.0.1-SNAPSHOT.jar .

COPY ./target/hobby_garden-latest.jar .

# Expose the port the application runs on
EXPOSE 8080

# Specify the command to run on container start
CMD ["java", "-jar", "hobby_garden-latest.jar"]
