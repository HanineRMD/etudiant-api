# Dockerfile.simple
FROM maven:3.9-eclipse-temurin-25-noble AS build
WORKDIR /app
COPY pom.xml .
# Ajouter cette ligne pour ignorer les erreurs DNS
RUN mvn dependency:go-offline -B || true
COPY src ./src
RUN mvn clean package -DskipTests -B

FROM eclipse-temurin:25-jdk-noble
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]