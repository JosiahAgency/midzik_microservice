# FROM eclipse-temurin:21-jre-alpine
FROM eclipse-temurin:21
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} analytics.jar
ENTRYPOINT ["java","-jar","/analytics.jar"]