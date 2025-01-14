FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17.0.1-jdk-slim
COPY --from=build /target/STAR_WARS_PROJECT-0.0.1-SNAPSHOT.jar STAR_WARS_PROJECT.jar
EXPOSE 8080
ENTRYPOINT["java","-jar","STAR_WARS_PROJECT.jar"]
