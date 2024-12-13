FROM openjdk:23-jdk-oracle AS builder

ARG COMPILE_DIR=/compiledir

WORKDIR ${COMPILE_DIR}

COPY mvnw .
COPY mvnw.cmd .
COPY pom.xml .
COPY .mvn .mvn
COPY src src

RUN ./mvnw clean package -Dmaven.test.skip=true

ENV SERVER_PORT=8080

EXPOSE ${SERVER_PORT}



FROM openjdk:23-jdk-oracle

ARG WORKDIR=/app

WORKDIR ${WORKDIR}

COPY --from=builder /compiledir/target/ssf-0.0.1-SNAPSHOT.jar app.jar
# COPY customers.csv

ENV SERVER_PORT=8080
ENV API_KEY=abc123
ENV FILEPATH=/app/customers.csv

EXPOSE ${SERVER_PORT}

HEALTHCHECK --interval=30s --timeout=5s --start-period=5s --retries=3 \
    CMD curl http://localhost:${PORT}/health || exit 1

ENTRYPOINT ["java", "-jar", "app.jar"]