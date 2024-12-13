FROM openjdk:23-jdk AS compiler

ARG COMPILE_DIR=/code_folder

WORKDIR ${COMPILE_DIR}

COPY .mvn .mvn
COPY src src
COPY pom.xml .
COPY mvnw .
COPY mvnw.cmd .

RUN chmod a+x ./mvnw

RUN ./mvnw clean package -Dmaven.tests.skip=true

ENV SERVER_PORT 3000

EXPOSE ${SERVER_PORT}

# run application using ENTRYPOINT or CMD
ENTRYPOINT java -jar target/noticebaordapp-0.0.1-SNAPSHOT.jar

FROM openjdk:23-jdk

ARG DEPLOY_DIR=/app

WORKDIR ${DEPLOY_DIR}

COPY --from=compiler /code_folder/target/noticeboardapp-0.0.1-SNAPSHOT.jar noticeboardapp.jar

ENV SERVER_PORT=3000

EXPOSE ${SERVER_PORT}

HEALTHCHECK --interval=60s --timeout=120s --start-period=5s --retries=3 \
    CMD curl http://localhost:${PORT}/health || exit 1

ENTRYPOINT [ "java", "-jar", "noticeboardapp.jar" ]
