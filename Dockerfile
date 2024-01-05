FROM openjdk:17-jdk-slim
WORKDIR /usr/src/app/api
ADD /target/*.jar backend-0.0.1-SNAPSHOT.jar
EXPOSE 3000
ENTRYPOINT ["java","-jar","backend-0.0.1-SNAPSHOT.jar"]

