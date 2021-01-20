FROM openjdk:8-jdk-alpine
COPY target/*.jar app.jar
ADD entrypoint.sh entrypoint.sh
RUN chmod +x entrypoint.sh
ENTRYPOINT ["/entrypoint.sh"]