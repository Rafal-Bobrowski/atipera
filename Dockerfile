FROM openjdk:17-jdk-slim-buster
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} Atipera-task.jar
ENTRYPOINT ["java","-jar","Atipera-task.jar"]