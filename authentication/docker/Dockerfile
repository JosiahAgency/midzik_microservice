# FROM eclipse-temurin:21-jre-alpine
FROM eclipse-temurin:21
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} authentication.jar
ENTRYPOINT ["java","-jar","/authentication.jar"]