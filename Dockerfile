FROM openjdk:21-jdk-slim
RUN mkdir -p /app/db
WORKDIR /app
COPY . .
RUN ./mvnw clean install
CMD ["java", "-jar", "/app/target/DockerAttempt1411-0.0.1-SNAPSHOT.jar"]