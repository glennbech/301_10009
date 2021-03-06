FROM maven:3.6-jdk-11 as builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn -T 1C package

FROM adoptopenjdk/openjdk11:alpine-slim
COPY --from=builder /app/target/*.jar /app/pgr301-m99.jar
ENTRYPOINT ["java","-jar","/app/pgr301-m99.jar"]