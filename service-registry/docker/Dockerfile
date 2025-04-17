# FROM eclipse-temurin:21-jre-alpine
FROM eclipse-temurin:21
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} registry.jar
ENTRYPOINT ["java","-jar","/registry.jar"]