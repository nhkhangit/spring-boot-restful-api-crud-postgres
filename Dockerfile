FROM maven:3.9.8 as builder

WORKDIR /app

COPY ./global/pom.xml ./pom.xml
COPY ./global/.mvn ./.mvn


COPY ./global/src ./src

RUN mvn package

FROM openjdk:17

WORKDIR /app

COPY --from=builder /app/target/*.jar ./app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
