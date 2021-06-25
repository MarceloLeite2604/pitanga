FROM openjdk:16-jdk-alpine3.13
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT java -Dspring.profiles.active=${SPRING_PROFILES} -jar /app.jar
EXPOSE 8080