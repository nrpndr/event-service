FROM openjdk:17-alpine
MAINTAINER nrpndr
WORKDIR /app/event-service
EXPOSE 9013
ARG JAR_FILE=target/event-service.jar
COPY ${JAR_FILE} event-service.jar
ENTRYPOINT ["java","-jar","event-service.jar"]