FROM maven:3.9-eclipse-temurin-17 AS build

COPY . /usr/src/app
RUN mvn --batch-mode -f /usr/src/app/pom.xml clean package

FROM eclipse-temurin:17-jdk
ENV PORT=8080
ENV SPRING_DATASOURCE_URL=
ENV SPRING_DATASOURCE_DRIVER_CLASS_NAME=com.mysql.cj.jdbc.Driver
ENV SPRING_DATASOURCE_USERNAME=
ENV SPRING_DATASOURCE_PASSWORD=
EXPOSE 8080
COPY --from=build /usr/src/app/target /opt/target
WORKDIR /opt/target
VOLUME /tmp
ENV JAVA_OPTS=""

CMD ["/bin/bash", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar java-proj-latest.jar"]
