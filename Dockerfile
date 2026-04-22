# Use official Java runtime
FROM eclipse-temurin:17-jdk

# Set working directory
WORKDIR /app

# Copy project files
COPY . .

# Build the project
RUN ./mvnw clean package || mvn clean package

# Expose port
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "target/nexthire-0.0.1-SNAPSHOT.jar"]