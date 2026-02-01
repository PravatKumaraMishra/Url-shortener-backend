FROM eclipse-temurin:25-jdk AS build
WORKDIR /app

# Copy wrapper and project definition first
COPY mvnw pom.xml ./
COPY .mvn/ .mvn/

# Fix permissions and download dependencies
RUN chmod +x mvnw && ./mvnw dependency:go-offline

# Copy source and build
COPY src ./src
RUN ./mvnw clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:25-jre-alpine
WORKDIR /app

# Copy the JAR (Assumes standard Spring Boot naming)
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]