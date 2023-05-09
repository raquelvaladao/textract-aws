FROM fabric8/java-alpine-openjdk11-jre

RUN apk --no-cache upgrade

WORKDIR /app

ENV APP_HOME app
ENV APP_NAME api.jar

COPY target/*.jar /${APP_HOME}/${APP_NAME}

ENTRYPOINT java -jar /${APP_HOME}/${APP_NAME}
EXPOSE 8080
