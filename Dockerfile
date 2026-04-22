# Use Maven with Java 17
FROM maven:3.9.9-eclipse-temurin-17

# Set working directory
WORKDIR /app

# Copy project files
COPY . .

# Build the application
RUN mvn clean package -DskipTests

# Expose port used by Spring Boot
EXPOSE 8080

# Start the application
CMD ["java", "-jar", "target/nexthire-1.0.0.jar"]