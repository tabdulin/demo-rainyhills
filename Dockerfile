FROM maven:3.6-jdk-11-slim as builder

RUN mkdir demo
WORKDIR /demo
COPY pom.xml .
RUN mvn clean verify --fail-never
COPY src ./src
RUN mvn clean verify

FROM openjdk:11-jre-slim
RUN mkdir demo
WORKDIR /demo
COPY --from=builder /demo/target/demo-rainyhills.jar .
ENTRYPOINT ["java", "-jar", "demo-rainyhills.jar"]