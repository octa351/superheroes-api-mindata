FROM openjdk:11-jre-slim

COPY target/superhero.api-0.0.1-SNAPSHOT.jar superhero.api-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/message-server-1.0.0.jar"]