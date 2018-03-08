FROM openjdk:8-jre-alpine

ENV SPRING_PROFILES_ACTIVE=redis
ENV RESTKV_REDIS_URL="redis://redis:6379/0"

VOLUME /tmp
ADD build/libs/restkv.jar app.jar
RUN sh -c 'touch /app.jar'

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
