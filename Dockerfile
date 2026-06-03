FROM maven:3.9-eclipse-temurin-17 AS build

WORKDIR /usr/src/app
COPY . .
RUN mvn --batch-mode clean package

FROM eclipse-temurin:17-jdk
EXPOSE 8080
COPY --from=build /usr/src/app/target/*.jar /opt/app/app.jar
WORKDIR /opt/app
VOLUME /tmp

CMD ["java", "-jar", "app.jar"]
