FROM openjdk:8-jdk-alpine

VOLUME /tmp

COPY  ../../user-service-app-8001/target/user-service-app-8001-2.0.jar app.jar

ENV spring.profile.active=docker

ENTRYPOINT ["java","-jar","/app.jar"]