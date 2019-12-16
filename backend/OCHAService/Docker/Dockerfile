FROM openjdk:8-jdk-alpine
LABEL maintainer="Chinh Nguyen"
ENV REFRESHED_AT 2019-11-26
EXPOSE 8080
RUN set -ex \
  && apk update \
  && apk upgrade \
  && apk add curl jq
RUN mkdir /OCHA
COPY /build/libs/*.jar /OCHA/backend-0.1.0.jar

WORKDIR /OCHA
CMD ["java", "-jar", "-Djava.security.egd=file:/dev/./urandom","backend-0.1.0.jar"]
