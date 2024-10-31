FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
ARG JAR_FILE=target/Proyecto_Integrador_Backend_CTD_C1_G6-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app_backend.jar
EXPOSE 8080
CMD ["java", "-jar", "app_backend.jar"]