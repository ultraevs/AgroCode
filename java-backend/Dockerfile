FROM openjdk:17-jdk-slim

WORKDIR /app

COPY . .

RUN apt-get install -y maven
RUN mvn clean install

COPY target/java-backend.jar app.jar

RUN apt-get update && apt-get install -y dos2unix
COPY .env .
RUN dos2unix .env
RUN export $(cat .env | xargs)

ENTRYPOINT ["java", "-jar", "app.jar"]
