FROM amazoncorretto:17-alpine3.16
WORKDIR .
VOLUME /tmp

ENV WAIT_VERSION 2.9.0
ADD https://github.com/ufoscout/docker-compose-wait/releases/download/$WAIT_VERSION/wait /wait
RUN chmod +x /wait

ARG JAR_SOURCE_PATH=""
COPY $JAR_SOURCE_PATH app.jar