# Use Java 21 image
FROM eclipse-temurin:17-jdk

# App jar copy
COPY target/*.jar app.jar

# Expose port
EXPOSE 8080

# Run app
ENTRYPOINT ["java", "-jar", "/app.jar"]