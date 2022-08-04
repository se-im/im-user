FROM openjdk:8-jdk-alpine

VOLUME /tmp

COPY  user-service-app-8001/target/user-service-app-8001-2.0.jar app.jar

ENV SPRING_PROFILES_ACTIVE=docker \
    DUBBO_IP_TO_REGISTRY=124.221.242.204

CMD ["java","-jar","/app.jar"]
