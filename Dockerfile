FROM openjdk:12-jdk-alpine

LABEL maintainer="Rui Wang (urumqi.wang@gmail.com)"

RUN apk update && apk add bash

ADD target/gohenry-coding-challenge.jar /home/gohenry-coding-challenge.jar

WORKDIR /home

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "gohenry-coding-challenge.jar"]
