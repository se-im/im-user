FROM openjdk:8-jdk-alpine

VOLUME /tmp

COPY ../../web/target/*.jar app.jar

ENV spring.profile.active=docker

ENTRYPOINT ["java","-jar","/app.jar"]