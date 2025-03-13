# Use the official OpenJDK 21 image as a base
FROM eclipse-temurin:21
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]