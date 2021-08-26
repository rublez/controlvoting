FROM openjdk:11
RUN mkdir app
ARG JAR_FILE
ADD /target/${JAR_FILE} /app/votingcontrol-0.0.1-SNAPSHOT.jar
WORKDIR /app
ENTRYPOINT java -jar votingcontrol-0.0.1-SNAPSHOT.jar
